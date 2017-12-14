package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
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
	ResourceView resourceView;
	private EventHandler<MouseEvent> pieceEvent;
	private EventHandler<MouseEvent> firstRndStreet;
	private EventHandler<MouseEvent> buyEvent;
	private EventHandler<MouseEvent> endTurn;
	private EventHandler<MouseEvent> firstRndPiece;
	private EventHandler<MouseEvent> doubleStreetEvent;
	private EventHandler<MouseEvent> robber;
	private DevelopCardController devCon;
	private TradeController tradeController;

	public GameController(String spelId, PlayerModel[] players, int usrPlayer, Stage stage) {
		this.players = new PlayerModel[4];
		this.usrPlayer = usrPlayer;
		this.spelId = spelId;
		this.players = players;
		this.diceO = new Dice(spelId);
		this.devCon = new DevelopCardController(players[usrPlayer].getUsername(), spelId, this);

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

		EventHandler<MouseEvent> trade = ((e) -> {
			refresh();
			disableButtons();
			tradeController.showTrade();
		});

		pieceEvent = ((e) -> {
			piecePlacement(e);
			refresh();
		});

		doubleStreetEvent = ((e) -> {
			piecePlacement(e);
			refresh();
			disableButtons();
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

		// binding and creating playerDataview and model via observer
		PlayerView[] playerViews = new PlayerView[4];
		for (int i = 0; i < players.length; i++) {
			playerViews[i] = new PlayerView();
			players[i].addObserver(playerViews[i]);
			players[i].refresh();
		}

		// controller for all trade functionality
		tradeController = new TradeController(spelId, players, this.usrPlayer, this);

		// binding resource view and model via observer
		resourceView = new ResourceView();
		players[this.usrPlayer].addObserver(resourceView);

		// functionality for chat
		ChatController chat = new ChatController(players[this.usrPlayer], spelId);
		new Thread(chat).start();

		// merging all individual components into 1 view
		buttons = new GameControlerView(buyEvent, endTurn, trade);
		playboardview = new PlayBoardView();
		dice = new DiceView();
		GameMergeView mergeView = new GameMergeView(playboardview, buttons, stage, playerViews, resourceView, dice,
				chat.getView());

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
				refreshButtons();
				int nThrow;
				try {
					boolean newThrow = diceO.throwDiceIfNotThrown();
					nThrow = diceO.getTotalthrow();
					System.out.println("dice: " + nThrow + " " + newThrow);
					if (newThrow)
						BoardHelper.giveResources(Catan.getGameId(), nThrow);
					dice.showDice(diceO.getTotalthrow());

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
				Thread.sleep(CatanController.refreshTime);
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
	
	public void showDoubleStreetPlacable() {
		ArrayList<Street> listOfStreet;
		try {
			listOfStreet = BoardHelper.getPlacableStreePos(players[usrPlayer], spelId);
			Platform.runLater(() -> {
				for (Street piece : listOfStreet) {
					playboardview.addStreet(piece, doubleStreetEvent);
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void robberPlacement(MouseEvent event) {
		try {
			RobberView view = (RobberView) event.getSource();
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

	public void disableButtons() {
		Platform.runLater(() -> {
			buttons.setDisabled();
		});
	}

	private void refreshButtons() {
		try {
			if (players[usrPlayer].getPlayerTurn()) {
				PlayerUser p = (PlayerUser) players[usrPlayer];
				HashMap<String, Boolean> buyable = p.getBuyableThings();
				buttons.setButtons(buyable.get("town"), buyable.get("city"), buyable.get("street"), true, true);
			} else {
				buttons.setButtons(false, false, false, false, false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkEnoughForDevCard() {
		try {
			players[usrPlayer].removeResource(TileType.W, 1);
			players[usrPlayer].removeResource(TileType.E, 1);
			players[usrPlayer].removeResource(TileType.G, 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		devCon.givePlayerCard();
	}

	// update ing field
	public void refresh() {
		// fetching all data in seperate thread
		System.out.println("board is refreshing");
		try {
			ArrayList<Tile> hexes = BoardHelper.getAllHexes(spelId);
			ArrayList<ArrayList<Street>> allStreets = new ArrayList<>();
			ArrayList<ArrayList<Piece>> allPieces = new ArrayList<>();
			for (int i = 0; i < players.length; i++) {
				allStreets.add(BoardHelper.getStreetsPlayer(players[i], spelId));
				allPieces.add(BoardHelper.getPiecesPlayer(players[i], spelId));
			}
			GridLocation robberPos = BoardHelper.getRobberPos(spelId);
			int longestRoad = BoardHelper.getLongestRoad(players[usrPlayer], spelId);
			diceO.getDBThrow();
			Boolean trade = isTrade();
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
				dice.showDice(diceO.getTotalthrow());
				refreshButtons();
				if(trade) startCounterTrade();
				//havens plaatsen
				playboardview.drawHaven(new GridLocation(3,1), new GridLocation(3,0));
				playboardview.drawHaven(new GridLocation(4,1), new GridLocation(3,0));

			});
			players[this.usrPlayer].refresh();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean isTrade() throws SQLException {
		ResultSet r = DatabaseManager.createStatement().executeQuery("SELECT COUNT(*) FROM ruilaanbod WHERE idspel = " + spelId +  " AND geaccepteerd IS NULL");
		ResultSet r2 = DatabaseManager.createStatement().executeQuery("SELECT COUNT(*) FROM ruilaanbod WHERE idspel = " + spelId +  " AND username = '" + players[usrPlayer].getUsername() + "'");
		if(r.first() && r2.first() && r.getInt(1) ==1 && r2.getInt(1)== 0) return true;
		return false;
	}

	private void startCounterTrade() {
		
		tradeController.showTradeCounter();
	}
	
	public void closeTrade() {
		refresh();
		refreshButtons();
	}
}
