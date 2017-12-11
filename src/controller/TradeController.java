package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;
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

	/** Shows trade request screen
	 * @param player
	 * @param spelID
	 */
	@SuppressWarnings("unchecked")
	public TradeController(PlayerUser player, String spelID) {
		view = new TradeView(this);
		this.spelId = spelID;
		this.player = player;
		allOffers = new HashMap[2][3];
		popUp = new Stage();
		popUp.setScene(new Scene(view));
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
						if (!names.contains(playerName)) {
							names.add(playerName);

							HashMap<TileType, Integer> offer = new HashMap<>();
							HashMap<TileType, Integer> request = new HashMap<>();

							offer.put(TileType.B, r.getInt("geeft_baksteen"));
							offer.put(TileType.E, r.getInt("geeft_wol"));
							offer.put(TileType.G, r.getInt("geeft_erts"));
							offer.put(TileType.H, r.getInt("geeft_wol"));
							offer.put(TileType.W, r.getInt("geeft_graan"));

							request.put(TileType.B, r.getInt("vraagt_baksteen"));
							request.put(TileType.E, r.getInt("vraagt_wol"));
							request.put(TileType.G, r.getInt("vraagt_erts"));
							request.put(TileType.H, r.getInt("vraagt_graan"));
							request.put(TileType.W, r.getInt("vraagt_hout"));

							@SuppressWarnings("rawtypes")
							HashMap[] bloob = {offer, request};
							view.addPlayerOffer(playerName, bloob);
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
		TradeHelper.acceptOffer(offerPlayer);
	}

}
