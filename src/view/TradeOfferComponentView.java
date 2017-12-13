package view;

import java.util.HashMap;

import controller.TradeController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.TileType;
import view.javaFXTemplates.TradeViewTemplate;

public class TradeOfferComponentView extends TradeViewTemplate {

	@FXML
	private Label offerer;

	private String offerPlayer;

	public TradeOfferComponentView(TradeController controller, String playerName,
			HashMap<TileType, Integer>[] offerData) {
		super(TradeOfferComponentView.class.getResource("fxml/TradeOfferComponentView.fxml"), controller);
		this.controller = controller;
		this.offerPlayer = playerName;
		offerer.setText(playerName);
		fillLabels(offerData);
		offerer.setLayoutX(getPrefWidth()/2);
	}

	public void acceptOffer(MouseEvent e) {
		controller.acceptOffer(offerPlayer);
	}
}
