package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import view.*;

public class GameController {

	PlayerModel[] players;

	String spelId;
	Integer usrPlayer; // 0..3
	Integer currentTurn;

	// views
	PlayBoardView playboardview;
	GameControlerView buttons;
	DiceView dice;

	private EventHandler<? super MouseEvent> pieceEvent;
	private EventHandler<? super MouseEvent> firstRndStreet;
	private EventHandler<MouseEvent> buyEvent;

	private EventHandler<MouseEvent> endTurn;

	private EventHandler<MouseEvent> firstRndPiece;

	public GameController(String spelId, PlayerModel[] players, int usrPlayer, Stage stage) throws Exception {
		this.players = new PlayerModel[4];
		this.usrPlayer = usrPlayer - 1;
		this.spelId = spelId;
		this.players = players;

		buyEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				refresh();
				Node caller = (Node) arg0.getSource();
				switch (caller.getId()) {
				case "townBtn":
					showTownPlacable();
					break;
				case "streetBtn":
					showStreetPlacable();
					break;
				case "cityBtn":
					showCityPlacable();
					break;
				}
			}
		};

		this.endTurn = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					BoardHelper.nextTurnForward(spelId);
					disableButtons();
					usrTurn();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};

		this.pieceEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				piecePlacement(arg0);
				refresh();
			}
		};

		// event handler for first round piece placement
		this.firstRndPiece = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				piecePlacement(arg0);
				refresh();
				showFrstRndStreet();
			}
		};
		// event handler for first round street placement
		this.firstRndStreet = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				piecePlacement(arg0);

				ResultSet result;
				try {
					result = DatabaseManager.createStatement().executeQuery(
							"select (select count(*) from spelerstuk where spelerstuk.idspel = spel.idspel and username = '"
									+ players[usrPlayer].getUsername()
									+ "' and x_van is not null) from spel where idspel =" + spelId);
					result.next();
					if (result.getInt(1) == 2 && players[usrPlayer].getPlayerNumber() == 4) {
						frstRnd();
					} else if (result.getInt(1) == 4 && players[usrPlayer].getPlayerNumber() == 1) {
						DatabaseManager.createStatement()
								.executeUpdate("UPDATE spel SET eersteronde=0 WHERE idspel = " + spelId);
						enableButtons();
					} else if (result.getInt(1) > 2) {
						BoardHelper.nextTurnBackward(spelId);
					} else {
						BoardHelper.nextTurnForward(spelId);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		buttons = new GameControlerView(buyEvent, endTurn);
		playboardview = new PlayBoardView();
		dice = new DiceView();
		GameMergeView mergeView = new GameMergeView(playboardview, buttons, stage);
		refresh();

		mergeView.show();
	}

	/**
	 * Starts the gamecontroller
	 * 
	 */
	public void start() {
		refresh();
		try {
			ResultSet result = DatabaseManager.createStatement()
					.executeQuery("select eersteronde from spel where idspel =" + spelId);
			result.next();
			if (result.getInt(1) == 1) {
				frstRnd();
			} else {
				usrTurn();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void frstRnd() {
		new Thread() {
			@Override
			public void run() {
				await();
				showFrstRndPieces();
			}
		}.start();
	}

	private void usrTurn() {
		new Thread() {
			@Override
			public void run() {
				await();
				enableButtons();
			}
		}.start();
	}

	/**
	 * Awaits a "shouldrefresh" flag up for playerUser
	 * 
	 */
	private void await() {
		boolean check = false;
		while (!check) {
			try {
				ResultSet result = DatabaseManager.createStatement()
						.executeQuery("select shouldrefresh from speler where username = '"
								+ players[usrPlayer].getUsername() + "' AND idspel = " + spelId);
				result.next();
				check = result.getInt(1) == 1;
				if (!check)
					System.out.println("waiting");
				Thread.sleep(2000);
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			refresh();
			DatabaseManager.createStatement().executeUpdate("UPDATE speler SET shouldrefresh=0 where username = '"
					+ players[usrPlayer].getUsername() + "' AND idspel = " + spelId);
			ResultSet result = DatabaseManager.createStatement()
					.executeQuery("SELECT beurt_username FROM spel WHERE idspel = " + spelId);
			result.next();
			if (!result.getString(1).equals(players[usrPlayer].getUsername()))
				await();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void piecePlacement(MouseEvent event) {
		try {
			if (event.getSource() instanceof PieceView) {
				PieceView caller = (PieceView) event.getSource();
				BoardHelper.registerPlacement(caller.getPieceModel(), spelId);
			} else if (event.getSource() instanceof StreetView) {
				StreetView caller = (StreetView) event.getSource();
				BoardHelper.registerPlacement(caller.getStreetModel(), spelId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	};

	public void showTownPlacable() {

		ArrayList<Piece> listOfPiece;
		try {
			listOfPiece = BoardHelper.getPlacebleTownPos(players[usrPlayer], spelId);
			Platform.runLater(() -> {
				for (Piece piece : listOfPiece) {
					playboardview.addPiece(piece, pieceEvent);
				}
			});
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void showStreetPlacable() {
		ArrayList<Street> listOfStreet;
		try {
			listOfStreet = BoardHelper.getPlacableStreePos(players[usrPlayer], spelId);
			Platform.runLater(() -> {
				for (Street piece : listOfStreet) {
					playboardview.addStreet(piece, pieceEvent);
				}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showFrstRndPieces() {
		ArrayList<Piece> listOfTowns;
		try {
			listOfTowns = BoardHelper.getValidFirstRoundTownPos(players[usrPlayer], spelId);
			Platform.runLater(() -> {
				for (Piece piece : listOfTowns) {
					playboardview.addPiece(piece, firstRndPiece);
				}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showFrstRndStreet() {
		ArrayList<Street> listOfTowns;
		try {
			listOfTowns = BoardHelper.getValidFirstRoundStreetPos(players[usrPlayer], spelId);
			Platform.runLater(() -> {
				for (Street piece : listOfTowns) {
					playboardview.addStreet(piece, firstRndStreet);
				}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showCityPlacable() {
		ArrayList<Piece> listOfTowns;
		try {
			listOfTowns = BoardHelper.getPlacableCity(players[usrPlayer], spelId);
			Platform.runLater(() -> {
				for (Piece piece : listOfTowns) {
					playboardview.addPiece(piece, pieceEvent);
				}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void enableButtons() {
		Platform.runLater(() -> {
			buttons.setEnabled();
		});
	}

	private void disableButtons() {
		Platform.runLater(() -> {
			buttons.setDisabled();
		});
	}

	// update ing field
	public void refresh() {
		System.out.println("board is refreshing");

		Platform.runLater(() -> {
			playboardview.getChildren().clear();
			// updates all hexes and attached values
			try {
				for (Tile t : BoardHelper.getAllHexes(spelId)) {
					playboardview.addHex(t);
				}
				for (PlayerModel player : players) {
					// places all streets for player
					for (Street street : BoardHelper.getStreetsPlayer(player, spelId)) {
						playboardview.addStreet(street);
					}
					// places all cities and towns for player
					for (Piece street : BoardHelper.getPiecesPlayer(player, spelId)) {
						playboardview.addPiece(street);
					}
				}
				buttons.setLongestRoad(BoardHelper.getLongestRoad(players[usrPlayer], spelId));
			} catch (Exception e) {
				// TODO show error to user??? //
				e.printStackTrace();
			}
		});
	}

}
