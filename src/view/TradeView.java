package view;

import java.util.HashMap;

import controller.TradeController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.TileType;
import view.javaFXTemplates.TradeViewTemplate;

public class TradeView extends TradeViewTemplate {

	// labels for trade ratios to bank
	@FXML
	private Label houtBnkLbl;
	@FXML
	private Label wolBnkLbl;
	@FXML
	private Label graanBnkLbl;
	@FXML
	private Label baksteenBnkLbl;
	@FXML
	private Label ertsBnkLbl;
	
	//button for creating offer
	@FXML
	private Button biedBtn;
	
	private final int[][] positions = new int[3][2];
	
	public TradeView(TradeController controller) {
		super(TradeView.class.getResource("fxml/trade.fxml"), controller);
	}

	public void addOfferBox(HashMap<TileType, Integer>[] offer, String username) {
		
	}

	public void offerBtnClick(MouseEvent e) {
		controller.submitTradeRequest(retrieveValues());
		clearTradeFld();		
		getChildren().clear();
		
	}

	public void addPlayerOffer(String playerName, HashMap<TileType, Integer>[] offerData) {
		getChildren().add(new TradeOfferComponentView(controller, playerName, offerData));
	}
}
