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
	
	
	public TradeController(Stage primaryStage, PlayerUser player) {
		view = new TradeView(primaryStage, this);
		this.player = player;
		view.show();
	}
	
	public void submitTrade(HashMap<TileType, Integer>[] tradeSuggestion) {
		try {
			TradeHelper.registerTrade(spelId, player, tradeSuggestion, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean checkSufficient(TileType type, Integer amount) {
		return player.hasResource(type, amount);
	}
}
