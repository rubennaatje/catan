package view;

import java.util.HashMap;

import controller.TradeController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.TileType;
import view.javaFXTemplates.TradeViewTemplate;

public class CounterTradeView extends TradeViewTemplate {
	
	@FXML private Label sendingPlayer;
	
	public CounterTradeView(HashMap<TileType, Integer>[] offerData, TradeController controller) {
		super(CounterTradeView.class.getResource("fxml/counterTrade.fxml"), controller);
		fillLabels(offerData);
	}
	
	public void offerBtnClick(MouseEvent e) {
		controller.submitCounterTradeRequest(retrieveValues());
	}
}
