package controller;

import java.sql.SQLException;
import java.util.HashMap;

import javafx.stage.Stage;
import model.PlayerModel;
import model.PlayerUser;
import model.TileType;
import model.TradeHelper;
import view.TradeView;

public class TradeController {
	TradeView view;
	String spelId;
	PlayerUser player;
	
	
	public TradeController(Stage primaryStage, PlayerUser player, String spelID) {
		view = new TradeView(primaryStage, this);
		this.spelId = spelID;
		this.player = player;
		view.show();
	}
	
	public void submitTradeRequest(HashMap<TileType, Integer>[] tradeSuggestion) {
		try {
			TradeHelper.registerTrade(spelId, player, tradeSuggestion, "NULL");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean checkSufficient(TileType type, Integer amount) {
		return player.hasResource(type, amount);
	}

	public void submitCounterTradeRequest(HashMap<TileType, Integer>[] bloob) {		
		try {
			TradeHelper.registerTrade(spelId, player, bloob, "TRUE");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void registerReject() {
		try {
			TradeHelper.registerReject(spelId, player);
			//TODO something to remove 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
