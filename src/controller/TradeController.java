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
import view.TradeView;

public class TradeController {
	private TradeView view;
	private String spelId;
	private Stage popUp;
	private PlayerModel[] players;
	private Integer usrPlayer; // 0..3
	private GameController superController;
	
	
	/** Shows trade request screen
	 * @param player
	 * @param spelID
	 */
	public TradeController(String spelID, PlayerModel[] players, Integer usrPlayer, GameController superController) {
		view = new TradeView(this);
		this.spelId = spelID;
		this.players = players;
		this.usrPlayer = usrPlayer;
		this.superController = superController;
		
		popUp = new Stage();
		Scene scene = new Scene(view);
		scene.getStylesheets().add(getClass().getResource("/view/style/application.css").toExternalForm());
		popUp.setScene(scene);
		popUp.setResizable(false);
		
		popUp.setOnHidden((e) -> superController.closeTrade());
	}
	
	@SuppressWarnings("unchecked")
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
					while (r.next() && popUp.isShowing()) {
						String playerName = r.getString("username");
						System.out.println(playerName);
						if (!names.contains(playerName)) {
							names.add(playerName);
							HashMap<TileType, Integer> offer = new HashMap<>();
							HashMap<TileType, Integer> request = new HashMap<>();

							offer.put(TileType.B, r.getInt("geeft_baksteen"));
							offer.put(TileType.E, r.getInt("geeft_erts"));
							offer.put(TileType.G, r.getInt("geeft_graan"));
							offer.put(TileType.H, r.getInt("geeft_hout"));
							offer.put(TileType.W, r.getInt("geeft_wol"));

							request.put(TileType.B, r.getInt("vraagt_baksteen"));
							request.put(TileType.E, r.getInt("vraagt_erts"));
							request.put(TileType.G, r.getInt("vraagt_graan"));
							request.put(TileType.H, r.getInt("vraagt_hout"));
							request.put(TileType.W, r.getInt("vraagt_wol"));

							@SuppressWarnings("rawtypes")
							HashMap[] bloob = {offer, request};
							Platform.runLater(() -> view.addPlayerOffer(bloob, playerName));
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
	
	public void show() {
		popUp.show();
	}

	public void close() {
		popUp.close();
		superController.closeTrade();
	}

}
