package view;

import java.util.HashMap;

import controller.TradeController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.TileType;
import view.javaFXTemplates.TradeViewTemplate;

public class CounterTradeView extends TradeViewTemplate {
	
	@FXML private Label sendingPlayer;
	@FXML private Button biedBtn;
	
	
	public CounterTradeView(TradeController controller) {
		super(CounterTradeView.class.getResource("fxml/counterTrade.fxml"), controller);
	}
	
	public void offerBtnClick(MouseEvent e) {
		controller.submitCounterTradeRequest(retrieveValues());
		controller.closeCounter();
	}
	
	@Override
	protected void checkInput() {
		super.checkInput();
		biedBtn.setDisable(!controller.checkAllSufficient(retrieveOfferValues()));
	}
	
	public void show(HashMap<TileType, Integer>[] offerData, String offerer) {
		fillLabels(offerData);
		sendingPlayer.setText(offerer);
		checkInput();
	}
	
	public void reject(MouseEvent e) {
		controller.registerReject();
		controller.closeCounter();
	}
}
