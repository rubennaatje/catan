package controller;

import javafx.stage.Stage;
import model.TradeModel;
import view.TradeView;

public class TradeController {
	TradeView view;
	TradeModel model;
	
	public TradeController(Stage primaryStage) {
		view = new TradeView(primaryStage);
		model = new TradeModel();
		view.show();
	}
}
