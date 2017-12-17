package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
	private StealResourceController steal;
	private EventHandler<MouseEvent> pieceEvent;
	private EventHandler<MouseEvent> firstRndStreet;
	private EventHandler<MouseEvent> buyEvent;
	private EventHandler<MouseEvent> endTurn;
	private EventHandler<MouseEvent> firstRndPiece;
	private EventHandler<MouseEvent> doubleStreetEvent;
	private EventHandler<MouseEvent> robber;
	private DevelopCardController devCon;
	private TradeController tradeController;

	private CardView cardView;
	private boolean isFirstRound = false;

	public GameController(String spelId, PlayerModel[] players, int usrPlayer, Stage stage) {
		this.players = new PlayerModel[4];
		this.usrPlayer = usrPlayer;
		this.spelId = spelId;
		this.players = players;
		this.diceO = new Dice(spelId);
				//functionality for cardView
		cardView = new CardView(this);
		this.devCon = new DevelopCardController((PlayerUser) players[usrPlayer], this);
		this.steal =  new StealResourceController(devCon);	
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
			if(isFirstRound) {
				PieceView piece = (PieceView)e.getSource();
				try {
					BoardHelper.giveResourcesFrstRound(spelId, piece.getPieceModel(), players[usrPlayer]);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			refresh();
			showFrstRndStreet();
		});
		// event handler for first round street placement
		firstRndStreet = ((e) -> {
			piecePlacement(e);
			try {
				ResultSet result = DatabaseManager.createStatement().executeQuery(
						"select (select count(*) from spelerstuk where spelerstuk.idspel = spel.idspel and username = '"
								+ players[usrPlayer].getUsername() + "' and x_van is not null) from spel where idspel ="
								+ spelId);
				result.next();
				if (result.getInt(1) == 2 && players[usrPlayer].getPlayerNumber() == 4) {
					frstRnd();
				} else if (result.getInt(1) == 4 && players[usrPlayer].getPlayerNumber() == 1) {
					DatabaseManager.createStatement()
							.executeUpdate("UPDATE spel SET eersteronde=0 WHERE idspel = " + spelId);
					usrTurn();
				} else if (result.getInt(1) > 2) {
					BoardHelper.nextTurnBackward(spelId);
					frstRnd();
				} else {
					BoardHelper.nextTurnForward(spelId);
					frstRnd();
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

		// functionality for cardView
		cardView = new CardView(this);

		// merging all individual components into 1 view
		buttons = new GameControlerView(buyEvent, endTurn, trade);
		playboardview = new PlayBoardView();
		dice = new DiceView();
		GameMergeView mergeView = new GameMergeView(playboardview, buttons, stage, playerViews, resourceView, dice,
				chat.getView(), cardView);

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
			BoardHelper.refreshAll(spelId);

			if (isFirstRound) {
				frstRnd();
			} else {
				usrTurn();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void frstRnd() {
		new Thread(() -> {
			await();
			showFrstRndPieces();
		}).start();
	}

	private void usrTurn() {
		new Thread() {
			@Override
			public void run() {
				await();
				int nThrow;
				try {
					boolean newThrow = diceO.throwDiceIfNotThrown();
					nThrow = diceO.getTotalthrow();
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

	public void stealResource(String spelId, GridLocation loc, PlayerModel selectedPlayer) {
		try {
			ArrayList<Piece> pieceLocs;
			ArrayList<PlayerModel> surroundingPlayers = new ArrayList<>();
			pieceLocs = BoardHelper.getSurroundingPieces(spelId, loc.x, loc.y);
			for(int x = 0; x < pieceLocs.size(); x++)
			{
				if(pieceLocs.get(x).getPlayer() != null || pieceLocs.get(x).getPlayer() != players[usrPlayer] )
				surroundingPlayers.add(pieceLocs.get(x).getPlayer());
			}
			DatabaseManager.createStatement().executeUpdate(""
					+ "UPDATE spelergrondstofkaart a SET username = '" + players[usrPlayer].getUsername() + "' WHERE idgrondstofkaart = "
					+ "(SELECT idgrondstofkaart FROM "
					+ "( SELECT idgrondstofkaart FROM spelergrondstofkaart WHERE username = '" + selectedPlayer.getUsername() + "'  ORDER BY RAND() LIMIT 1) as Doge) LIMIT 1");
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
				BoardHelper.setLongestRoad(players, spelId);
			}
			BoardHelper.refreshAll(spelId);
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

	public void disableButtons() {
		Platform.runLater(() -> {
			buttons.setDisabled();
		});
	}

	private void refreshButtons() {
		try {
			if (players[usrPlayer].getPlayerTurn() && !isFirstRound) {
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
			//check if is firstRound
			
			ResultSet r = DatabaseManager.createStatement()
					.executeQuery("select eersteronde from spel where idspel =" + spelId);
			if(r.first() && r.getInt(1) == 1) {isFirstRound = true;} else {isFirstRound = false;};
			
			
			ArrayList<Tile> hexes = BoardHelper.getAllHexes(spelId);
			ArrayList<ArrayList<Street>> allStreets = new ArrayList<>();
			ArrayList<ArrayList<Piece>> allPieces = new ArrayList<>();
			ArrayList<ArrayList<String>> havenList = BoardHelper.getAllHavens();
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
				// havens plaatsen
				for (ArrayList<String> haven : havenList) {
					int x = Integer.parseInt(haven.get(0));
					int y = Integer.parseInt(haven.get(1));
					String havenType = haven.get(2);
					int xEnd = calcEndPointX(x);
					int yEnd = calcEndPointY(y);
					Image im = new Image("view/images/Vraagteken.png");
					if (havenType != null) {
						switch (havenType) {
						case "G":
							im = new Image("view/images/Hooi.png");
							break;
						case "B":
							im = new Image("view/images/Steen.png");
							break;
						case "H":
							im = new Image("view/images/Hout.png");
							break;
						case "E":
							im = new Image("view/images/Erts.png");
							break;
						case "W":
							im = new Image("view/images/Schaap.png");
						}
					}
					playboardview.drawHaven(new GridLocation(x, y), new GridLocation(xEnd, yEnd), im);
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

				//refresh for players
				for (int i = 0; i < players.length; i++) {
					if(i != usrPlayer) {
						players[i].refresh();
					} else {
						PlayerUser usr = (PlayerUser)players[i];
						usr.refresh();
					}
				}

				
				refreshButtons();
				if (trade)
					startCounterTrade();
				devCon.refreshDevCards();
				
			});
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean isTrade() throws SQLException {
		ResultSet r = DatabaseManager.createStatement()
				.executeQuery("SELECT COUNT(*) FROM ruilaanbod WHERE idspel = " + spelId + " AND geaccepteerd IS NULL");
		ResultSet r2 = DatabaseManager.createStatement().executeQuery("SELECT COUNT(*) FROM ruilaanbod WHERE idspel = "
				+ spelId + " AND username = '" + players[usrPlayer].getUsername() + "'");
		if (r.first() && r2.first() && r.getInt(1) == 1 && r2.getInt(1) == 0)
			return true;
		return false;
	}

	private void startCounterTrade() {

		tradeController.showTradeCounter();
	}

	public void closeTrade() {
		try {
			TradeHelper.clearOffer(spelId);
			refresh();
			refreshButtons();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int calcEndPointX(int point) {
		if (point == 1 || point == 2 || point == 4) {
			point--;
		} else if (point == 6 || point == 9 || point == 11) {
			point++;
		}
		return point;
	}

	private int calcEndPointY(int point) {
		if (point == 1 || point == 3 || point == 4 || point == 6 || point == 7) {
			point--;
		} else if (point == 8 || point == 10) {
			point++;
		}
		return point;
	}
		public void playDevCard(int i)
	{
		if(devCon.checkForResource(i))
		{
			steal.showResources(i,players[usrPlayer]);
		}
		else
		{
			devCon.playCard(i);
		}
			
	}
	
	public void setDevCards(ArrayList<String> cards){
		cardView.addCards(cards);
	}
}
