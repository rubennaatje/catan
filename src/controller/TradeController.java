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
	TradeView view;
	String spelId;
	PlayerUser player;
	Stage popUp;
	HashMap<TileType, Integer>[][] allOffers;
	String tradeRequester = null;
	private PlayerModel[] players;

	/** Shows trade request screen
	 * @param player
	 * @param spelID
	 */
	@SuppressWarnings("unchecked")
	public TradeController(PlayerUser player, String spelID, PlayerModel[] players) {
		view = new TradeView(this);
		this.spelId = spelID;
		this.player = player;
		allOffers = new HashMap[2][3];
		this.players = players;
		
		popUp = new Stage();
		
		Scene scene = new Scene(view);
		scene.getStylesheets().add(getClass().getResource("/view/style/application.css").toExternalForm());
		popUp.setScene(scene);
		popUp.setResizable(false);
		popUp.initStyle(StageStyle.UNDECORATED);
		popUp.show();
	}
	
	/** Shows the counter offer screen 
	 * @param player
	 * @param spelID
	 * @param tradeRequester
	 */
	@SuppressWarnings("unchecked")
	public TradeController(PlayerUser player, String spelID, String tradeRequester) {
		view = new TradeView(this);
		this.spelId = spelID;
		this.player = player;
		this.tradeRequester = tradeRequester;
		allOffers = new HashMap[2][3];
		popUp = new Stage();
		popUp.setScene(new Scene(view));
		popUp.show();
	}
	

	@SuppressWarnings("unchecked")
	public void submitTradeRequest(HashMap<TileType, Integer>[] tradeSuggestion) {
		try {
			TradeHelper.registerTrade(spelId, player, tradeSuggestion, "NULL");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		new Thread(() -> {
			int i = 0;
			ArrayList<String> names = new ArrayList<>();
			while (i <= 3) {
				System.out.println("waiting for trade response");
				try {
					Thread.sleep(CatanController.refreshTime);
					ResultSet r = DatabaseManager.createStatement().executeQuery(
							"SELECT username, geeft_baksteen, geeft_wol, geeft_erts, geeft_graan , geeft_hout, "
							+ "vraagt_baksteen, vraagt_wol, vraagt_erts, vraagt_graan, vraagt_hout, geaccepteerd FROM catan.ruilaanbod "
									+ "WHERE idspel = " + spelId + " AND username != '" + player.getUsername() + "';");
					while (r.next()) {
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
		return player.hasResource(type, amount);
	}
	
	public boolean checkAllSufficient(HashMap<TileType, Integer>[] bloob) {
		//TODO check all values in hashmap for ok
		return false;
	}

	public void submitCounterTradeRequest(HashMap<TileType, Integer>[] data) {
		try {
			TradeHelper.registerTrade(spelId, player, data, "TRUE");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void registerReject() {
		try {
			TradeHelper.registerReject(spelId, player);
			// TODO something to remove
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void acceptOffer(String offerPlayer) {
		try {
			
			TradeHelper.acceptOffer(spelId, offerPlayer, , player);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
