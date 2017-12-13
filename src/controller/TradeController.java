package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
		
		//preparing counter tradesceen
		popUpCounterTrade = new Stage();
		viewCounter = new CounterTradeView(this);
		Scene sceneCounterTrade = new Scene(viewCounter);
		sceneCounterTrade.getStylesheets().add(getClass().getResource("/view/style/application.css").toExternalForm());
		popUpCounterTrade.setScene(sceneCounterTrade);
		popUpCounterTrade.setResizable(false);
//		popUpCounterTrade.setOnHidden((e) -> superController.closeTrade());
		popUpTrade.initStyle(StageStyle.UNDECORATED);
		
		
		//preparing tradescreen
		popUpTrade = new Stage();
		viewTrade = new TradeView(this);
		Scene sceneTrade = new Scene(viewTrade);
		sceneTrade.getStylesheets().add(getClass().getResource("/view/style/application.css").toExternalForm());
		popUpTrade.setScene(sceneTrade);
		popUpTrade.setResizable(false);
		popUpTrade.initStyle(StageStyle.UNDECORATED);
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
			while (i < 3) {
				System.out.println("waiting for trade response");
				try {
					Thread.sleep(CatanController.refreshTime);
					ResultSet r = DatabaseManager.createStatement().executeQuery(
							"SELECT username, geeft_baksteen, geeft_wol, geeft_erts, geeft_graan , geeft_hout, "
							+ "vraagt_baksteen, vraagt_wol, vraagt_erts, vraagt_graan, vraagt_hout, geaccepteerd FROM catan.ruilaanbod "
									+ "WHERE idspel = " + spelId + " AND username != '" + players[usrPlayer].getUsername() + "';");
					while (r.first() && popUpTrade.isShowing()) {
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
		}).start();
	}

	public boolean checkSufficient(TileType type, Integer amount) {
		return ((PlayerUser) players[usrPlayer]).hasResource(type, amount);
	}
	
	public boolean checkAllSufficient(HashMap<TileType, Integer>[] bloob) {
		return false;
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
			if(players[i].getUsername().equals(offerPlayer)) {
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
	
	public void showTrade() {
		popUpTrade.show();
	}
	
	public void showTradeCounter() {
		try {
			ResultSet r = DatabaseManager.createStatement().executeQuery("SELECT * FROM ruilaanbod WHERE idspel = " + spelId + " AND geaccepteerd IS NULL");
			popUpCounterTrade.show();
			viewCounter.show(TradeHelper.retrieveOffer(r));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	public void close() {
		popUpTrade.close();
		superController.closeTrade();
	}

}
