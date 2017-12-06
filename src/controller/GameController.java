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
	Dice diceO;
	private EventHandler<MouseEvent> pieceEvent;
	private EventHandler<MouseEvent> firstRndStreet;
	private EventHandler<MouseEvent> buyEvent;
	private EventHandler<MouseEvent> endTurn;
	private EventHandler<MouseEvent> firstRndPiece;
	private EventHandler<MouseEvent> doubleStreetEvent;
	private DevelopCardController devCon;
	private EventHandler<MouseEvent> robber;

	public GameController(String spelId, PlayerModel[] players, int usrPlayer, Stage stage) throws Exception {
		this.players = new PlayerModel[4];
		this.usrPlayer = usrPlayer - 1;
		this.spelId = spelId;
		this.players = players;
		this.diceO = new Dice(spelId);
		this.devCon  = new DevelopCardController(players[usrPlayer].getSpelId());
		
		buyEvent = ((e) -> {
			refresh();
			Node caller = (Node) e.getSource();
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
		});

		endTurn = ((e) -> {
			try {
				BoardHelper.nextTurnForward(spelId);
				disableButtons();
				usrTurn();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}

		});

		pieceEvent = ((e) -> {
			piecePlacement(e);
			refresh();
		});

		
		

		doubleStreetEvent = ((e) -> {
			piecePlacement(e);
			refresh();
			showStreetPlacable();
		});
		
		
		robber = ((e) -> {	
			robberPlacement(e);
			refresh();
		});
		// event handler for first round piece placement
		firstRndPiece = ((e) -> {
			piecePlacement(e);
			refresh();
			showFrstRndStreet();
		});
		// event handler for first round street placement
		firstRndStreet = ((e) -> {
			piecePlacement(e);
			ResultSet result;
			try {
				result = DatabaseManager.createStatement().executeQuery(
						"select (select count(*) from spelerstuk where spelerstuk.idspel = spel.idspel and username = '"
								+ players[usrPlayer].getUsername() + "' and x_van is not null) from spel where idspel ="
								+ spelId);
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
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		});

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
				int nThrow;
				try {
					boolean newThrow = diceO.throwDiceIfNotThrown();
					nThrow = diceO.getTotalthrow();
					System.out.println("dice: " + nThrow + " " + newThrow);
					if(newThrow)
						BoardHelper.giveResources(Catan.getGameId(), nThrow);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
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

	public void robberPlacement(MouseEvent event) {
		try {
			RobberView view = (RobberView)event.getSource();
			BoardHelper.placeRobber(spelId, view.getLoc());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

	public void showRobberPlacable() {
		ArrayList<GridLocation> locations;
		locations = BoardHelper.getValidRobberLocations(spelId);
		Platform.runLater(() -> {
			for (GridLocation location : locations) {
				playboardview.addRobber(location, robber);
			}
		});
	}
	

	
	public void showTownPlacable() {
		try {
			ArrayList<Piece> listOfPiece = BoardHelper.getPlacebleTownPos(players[usrPlayer], spelId);
			Platform.runLater(() -> {
				for (Piece piece : listOfPiece) {
					playboardview.addPiece(piece, pieceEvent);
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
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
	public void checkEnoughForDevCard()
	{
		PlayerUser player = (PlayerUser)players[usrPlayer];
		if(player.hasResource(TileType.W, 1) && player.hasResource(TileType.E, 1) && player.hasResource(TileType.G, 1))
		{
			player.removeResource(TileType.W);
			player.removeResource(TileType.E);
			player.removeResource(TileType.G);
			devCon.givePlayerCard(player.getUsername());
		}
	}

	// update ing field
	public void refresh() {
		// fetching all data in seperate thread
		System.out.println("board is refreshing");
		try {
			ArrayList<Tile> hexes = BoardHelper.getAllHexes(spelId);
			ArrayList<ArrayList<Street>> allStreets = new ArrayList<>();
			ArrayList<ArrayList<Piece>> allPieces = new ArrayList<>();
			for (PlayerModel player : players) {
				allStreets.add(BoardHelper.getStreetsPlayer(player, spelId));
				allPieces.add(BoardHelper.getPiecesPlayer(player, spelId));
			}
			GridLocation robberPos = BoardHelper.getRobberPos(spelId);
			int longestRoad = BoardHelper.getLongestRoad(players[usrPlayer], spelId);
			Platform.runLater(() -> {
				playboardview.getChildren().clear();
				for (Tile t : hexes) {
					playboardview.addHex(t);
				}
				for (ArrayList<Street> streets : allStreets) {
					// places all streets for player
					for (Street street : streets) {
						playboardview.addStreet(street);
					}
				}
				for (ArrayList<Piece> pieces : allPieces) {
					// places all cities and towns for player
					for (Piece street : pieces) {
						playboardview.addPiece(street);
					}
				}
				buttons.setLongestRoad(longestRoad);
				playboardview.addRobber(robberPos);
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
