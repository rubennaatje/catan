package controller;

import java.util.HashMap;

import javafx.stage.Stage;
import model.PlayerModel;
import model.TradeHelper;
import view.TradeView;

public class TradeController {
	TradeView view;
	String spelId;
	PlayerModel player;
	
	
	public TradeController(Stage primaryStage) {
		view = new TradeView(primaryStage, this);
		view.show();
	}
	
	public void submitTrade(HashMap<String, Integer>[] tradeSuggestion) {
		TradeHelper.registerTrade(spelId, player, tradeSuggestion);
		
	}
}
