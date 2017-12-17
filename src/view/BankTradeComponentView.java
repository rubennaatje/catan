package view;

import java.util.HashMap;

import controller.TradeController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.TileType;
import view.javaFXTemplates.TradeViewTemplate;

public class BankTradeComponentView extends TradeViewTemplate {
	
	@FXML private Label sendingPlayer;
	@FXML private Button biedBtn;
	
	
	public BankTradeComponentView(TradeController controller) {
		super(BankTradeComponentView.class.getResource("fxml/bnkTrade.fxml"), controller);
	}
	
	public void offerBtnClick(MouseEvent e) {
		controller.submitCounterTradeRequest(retrieveValues());
		controller.closeCounter();
	}
	
	@Override
	protected void checkInput() {
		super.checkInput();
		biedBtn.setDisable(!controller.checkAllSufficient(retrieveValues()));
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
