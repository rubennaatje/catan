package controller;

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
		TradeHelper.registerTrade(spelId, player, tradeSuggestion);
	}
	public boolean checkSufficient(TileType type, Integer amount) {
		return player.hasResource(type, amount);
	}
}
