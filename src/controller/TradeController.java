package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
				try {
					Thread.sleep(CatanController.refreshTime);
					ResultSet r = DatabaseManager.createStatement().executeQuery(
							"SELECT username, geeft_baksteen, geeft_wol, geeft_erts, geeft_graan , geeft_hout, "
									+ "vraagt_baksteen, vraagt_wol, vraagt_erts, vraagt_graan, vraagt_hout, geaccepteerd FROM ruilaanbod "
									+ "WHERE idspel = " + spelId + " AND NOT username = '"
									+ players[usrPlayer].getUsername() + "';");
					while (r.next()) {
						String playerName = r.getString("username");
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

	public boolean checkAllSufficient(HashMap<TileType, Integer> offer) {
		if (!((PlayerUser) players[usrPlayer]).hasResource(TileType.B, offer.get(TileType.B)))
			return false;
		if (!((PlayerUser) players[usrPlayer]).hasResource(TileType.E, offer.get(TileType.E)))
			return false;
		if (!((PlayerUser) players[usrPlayer]).hasResource(TileType.G, offer.get(TileType.G)))
			return false;
		if (!((PlayerUser) players[usrPlayer]).hasResource(TileType.H, offer.get(TileType.H)))
			return false;
		if (!((PlayerUser) players[usrPlayer]).hasResource(TileType.W, offer.get(TileType.W)))
			return false;
		return true;
	}

	public void submitCounterTradeRequest(HashMap<TileType, Integer>[] hashMap) {
		try {
			TradeHelper.registerTrade(spelId, players[usrPlayer], hashMap, "TRUE");
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

	public void purchaseBank(TileType targetResource, HashMap<TileType, Integer> hashMap) {
		try {

			PlayerUser user = (PlayerUser) players[usrPlayer];
			
			
			user.addResource(TileType.H, hashMap.get(TileType.H));
			user.addResource(TileType.W, hashMap.get(TileType.W));
			user.addResource(TileType.G, hashMap.get(TileType.G));
			user.addResource(TileType.B, hashMap.get(TileType.B));
			user.addResource(TileType.E, hashMap.get(TileType.E));
			
			HashMap<TileType, Integer> value = BoardHelper.getTradeRatio(user, spelId);
			
			int result = 0;
			java.util.Iterator<Entry<TileType, Integer>> it = hashMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<TileType, Integer> pair = (Map.Entry<TileType, Integer>) it.next();
				result += pair.getValue() * value.get(targetResource);
				it.remove();
			}
			
			user.removeResource(targetResource, result);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void showTrade() {
		try {
			TradeHelper.clearOffer(spelId);
			viewTrade = new TradeView(this);
			popUpTrade.setScene(new Scene(viewTrade));
			viewTrade.setBankLabels(BoardHelper.getTradeRatio(players[usrPlayer], spelId));
			popUpTrade.show();
		} catch (SQLException e) {
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
			closeTrade();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void resetTrade() {
		showTrade();
	}

	public void showBank(MouseEvent e) {
		try {
			Node source = (Node) e.getSource();
			HashMap<TileType, Integer> map = BoardHelper.getTradeRatio(players[usrPlayer], spelId);
			switch (source.getId()) {
			case "houtBnkLbl":
				viewTrade.showBankTradeWindow(this, map.get(TileType.H), TileType.H);
				break;
			case "wolBnkLbl":
				viewTrade.showBankTradeWindow(this, map.get(TileType.W), TileType.W);
				break;
			case "graanBnkLbl":
				viewTrade.showBankTradeWindow(this, map.get(TileType.G), TileType.G);
				break;
			case "baksteenBnkLbl":
				viewTrade.showBankTradeWindow(this, map.get(TileType.B), TileType.B);
				break;
			case "ertsBnkLbl":
				viewTrade.showBankTradeWindow(this, map.get(TileType.E), TileType.E);
				break;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
