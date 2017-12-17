package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BoardHelper;
import model.PlayerModel;
import model.PlayerUser;
import model.TileType;
import model.TradeHelper;
import view.CounterTradeView;
import view.TradeView;

public class TradeController {
	private TradeView viewTrade;
	private String spelId;
	private Stage popUpTrade;
	private PlayerModel[] players;
	private Integer usrPlayer; // 0..3
	private GameController superController;
	private CounterTradeView viewCounter;
	private Stage popUpCounterTrade;

	/**
	 * @param spelID
	 * @param players
	 * @param usrPlayer
	 * @param superController
	 */
	public TradeController(String spelID, PlayerModel[] players, Integer usrPlayer, GameController superController) {
		this.spelId = spelID;
		this.players = players;
		this.usrPlayer = usrPlayer;
		this.superController = superController;

		// preparing counter tradesceen
		popUpCounterTrade = new Stage();
		viewCounter = new CounterTradeView(this);
		Scene sceneCounterTrade = new Scene(viewCounter);
		sceneCounterTrade.getStylesheets().add(getClass().getResource("/view/style/application.css").toExternalForm());
		popUpCounterTrade.setScene(sceneCounterTrade);
		popUpCounterTrade.setResizable(false);
		// popUpCounterTrade.setOnHidden((e) -> superController.closeTrade());
		popUpCounterTrade.initStyle(StageStyle.UNDECORATED);

		// preparing tradescreen
		popUpTrade = new Stage();
		viewTrade = new TradeView(this);
		Scene sceneTrade = new Scene(viewTrade);
		sceneTrade.getStylesheets().add(getClass().getResource("/view/style/application.css").toExternalForm());
		popUpTrade.setScene(sceneTrade);
		popUpTrade.setResizable(false);
		popUpTrade.initStyle(StageStyle.UNDECORATED);
		popUpTrade.setOnHidden((e) -> superController.closeTrade());
	}

	public void submitTradeRequest(HashMap<TileType, Integer>[] tradeSuggestion) {
		try {
			TradeHelper.registerTrade(spelId, players[usrPlayer], tradeSuggestion, "NULL");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		new Thread(() -> {
			int i = 0;
			ArrayList<String> names = new ArrayList<>();
			while (i < 3 && popUpTrade.isShowing()) {
				System.out.println("waiting for trade response");
				try {
					Thread.sleep(CatanController.refreshTime);
					ResultSet r = DatabaseManager.createStatement().executeQuery(
							"SELECT username, geeft_baksteen, geeft_wol, geeft_erts, geeft_graan , geeft_hout, "
									+ "vraagt_baksteen, vraagt_wol, vraagt_erts, vraagt_graan, vraagt_hout, geaccepteerd FROM catan.ruilaanbod "
									+ "WHERE idspel = " + spelId + " AND NOT username = '"
									+ players[usrPlayer].getUsername() + "';");
					while (r.next()) {
						String playerName = r.getString("username");
						System.out.println(playerName);
						if (!names.contains(playerName)) {
							names.add(playerName);
							HashMap<TileType, Integer>[] offer = TradeHelper.retrieveOffer(r);
							Platform.runLater(() -> viewTrade.addPlayerOffer(offer, playerName));
							i++;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			Platform.runLater(() -> viewTrade.addRejectBtn());
		}).start();
	}

	public boolean checkSufficient(TileType type, Integer amount) {
		return ((PlayerUser) players[usrPlayer]).hasResource(type, amount);
	}

	public boolean checkAllSufficient(HashMap<TileType, Integer>[] bloob) {
		if (!((PlayerUser) players[usrPlayer]).hasResource(TileType.B, bloob[0].get(TileType.B)))
			return false;
		if (!((PlayerUser) players[usrPlayer]).hasResource(TileType.E, bloob[0].get(TileType.E)))
			return false;
		if (!((PlayerUser) players[usrPlayer]).hasResource(TileType.G, bloob[0].get(TileType.G)))
			return false;
		if (!((PlayerUser) players[usrPlayer]).hasResource(TileType.H, bloob[0].get(TileType.H)))
			return false;
		if (!((PlayerUser) players[usrPlayer]).hasResource(TileType.W, bloob[0].get(TileType.W)))
			return false;
		return true;
	}

	public void submitCounterTradeRequest(HashMap<TileType, Integer>[] data) {
		try {
			TradeHelper.registerTrade(spelId, players[usrPlayer], data, "TRUE");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void registerReject() {
		try {
			TradeHelper.registerReject(spelId, players[usrPlayer]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean acceptOffer(String offerPlayer) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].getUsername().equals(offerPlayer)) {
				PlayerModel offerer = players[i];
				try {
					TradeHelper.acceptOffer(spelId, offerer, players[usrPlayer]);
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return false;
	}

	public void purchaseBank(MouseEvent e) {
		Node source = (Node) e.getSource();
		try {
			HashMap<TileType, Integer> map = BoardHelper.getTradeRatio(players[usrPlayer], spelId);
			PlayerUser user= (PlayerUser)players[usrPlayer]; 
			
			switch (source.getId()) {
			case "houtBnkLbl":
				
				break;
			case "wolBnkLbl":
				break;
			case "graanBnkLbl":
				break;
			case "baksteenBnkLbl":
				break;
			case "ertsBnkLbl":
				break;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void showTrade() {
		try {
			TradeHelper.clearOffer(spelId);
			viewTrade.reset(BoardHelper.getTradeRatio(players[usrPlayer], spelId));
			popUpTrade.show();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showTradeCounter() {
		try {
			String offerer = null;
			ResultSet r = DatabaseManager.createStatement()
					.executeQuery("SELECT * FROM ruilaanbod WHERE idspel = " + spelId + " AND geaccepteerd IS NULL");
			if (r.first()) {
				offerer = r.getString("username");
			}
			popUpCounterTrade.show();
			viewCounter.show(TradeHelper.retrieveOffer(r), offerer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isShown() {
		if (popUpCounterTrade.isShowing() || popUpTrade.isShowing())
			return true;
		return false;
	}

	public void closeCounter() {
		popUpCounterTrade.close();
		superController.closeTrade();
	}

	public void closeTrade() {
		popUpTrade.close();
		superController.closeTrade();
	}

	public void registerCounterReject() {
		try {
			TradeHelper.clearOffer(spelId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
