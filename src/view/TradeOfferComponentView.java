package view;

import java.util.HashMap;

import controller.TradeController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.TileType;
import view.javaFXTemplates.TradeViewTemplate;

public class TradeOfferComponentView extends TradeViewTemplate {
	@FXML private Button acceptBtn;
	@FXML
	private Label offerer;
	private String offerPlayer;
	private HBox background; 
	
	
	public TradeOfferComponentView(TradeController controller, String playerName,
			HashMap<TileType, Integer>[] offerData) {
		super(TradeOfferComponentView.class.getResource("fxml/TradeOfferComponentView.fxml"), controller);
		this.controller = controller;
		this.offerPlayer = playerName;
		offerer.setText(playerName);
		fillLabels(offerData);
		offerer.setLayoutX(getPrefWidth()/2);
		background.setStyle("-fx-background-color: #ffc100;");
	}
	
	@Override
	protected void checkInput() {
		super.checkInput();
		acceptBtn.setDisable(!controller.checkAllSufficient(retrieveValues()));
	}

	public void acceptOffer(MouseEvent e) {
		if(controller.acceptOffer(offerPlayer)) {
			controller.closeTrade();
		}
	}
	public void background() {
		background.setStyle("-fx-background-color: #336699;");
	}
}