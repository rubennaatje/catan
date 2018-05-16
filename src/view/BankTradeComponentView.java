package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import controller.TradeController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.TileType;
import view.javaFXTemplates.PaneTemplate;

public class BankTradeComponentView extends PaneTemplate {

	@FXML
	private Label sendingPlayer;
	@FXML
	private Button biedBtn;

	
	@FXML
	private TextField houtAanLbl;
	@FXML
	private TextField wolAanLbl;
	@FXML
	private TextField graanAanLbl;
	@FXML
	private TextField baksteenAanLbl;
	@FXML
	private TextField ertsAanLbl;

	@FXML private Label ratio;
	
	private TradeController controller;
	private Integer requiredAmount;
	private TileType targetResource;
	
	public BankTradeComponentView(TradeController controller, Integer requiredAmount, TileType resource) {
		super(BankTradeComponentView.class.getResource("fxml/bnkTrade.fxml"));
		this.controller = controller;
		this.requiredAmount = requiredAmount;
		this.targetResource = resource;
		ratio.setText( targetResource.getCssClass() + " " + requiredAmount + ":1");
	}

	public void offerBtnClick(MouseEvent e) {
		controller.purchaseBank(targetResource, retrieveOfferValues());
		controller.closeTrade();
	}

	private HashMap<TileType, Integer> retrieveOfferValues() {
		HashMap<TileType, Integer> offer = new HashMap<>();
		offer.put(TileType.B, Integer.parseInt(baksteenAanLbl.getText()));
		offer.put(TileType.E, Integer.parseInt(ertsAanLbl.getText()));
		offer.put(TileType.G, Integer.parseInt(graanAanLbl.getText()));
		offer.put(TileType.H, Integer.parseInt(houtAanLbl.getText()));
		offer.put(TileType.W, Integer.parseInt(wolAanLbl.getText()));
		return offer;
	}

	private int retrieveTotalRequestValue() {
		HashMap<TileType, Integer> offer = retrieveOfferValues();
		int result = 0;
		java.util.Iterator<Entry<TileType, Integer>> it = offer.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<TileType, Integer> pair = (Map.Entry<TileType, Integer>) it.next();
			result += pair.getValue();
			it.remove();
		}
		return result;
	}

	public void addBtnClick(MouseEvent e) {
		Node source = (Node) e.getSource();
		switch (source.getParent().getId()) {
		case "houtAanbGrp":
			if (Integer.parseInt(houtAanLbl.getText()) < 9)
				houtAanLbl.setText(((Integer) (Integer.parseInt(houtAanLbl.getText()) + 1)).toString());
			break;
		case "wolAanbGrp":
			if (Integer.parseInt(wolAanLbl.getText()) < 9)
				wolAanLbl.setText(((Integer) (Integer.parseInt(wolAanLbl.getText()) + 1)).toString());
			break;
		case "graanAanbGrp":
			if (Integer.parseInt(graanAanLbl.getText()) < 9)
				graanAanLbl.setText(((Integer) (Integer.parseInt(graanAanLbl.getText()) + 1)).toString());

			break;
		case "baksteenAanbGrp":
			if (Integer.parseInt(baksteenAanLbl.getText()) < 9)
				baksteenAanLbl.setText(((Integer) (Integer.parseInt(baksteenAanLbl.getText()) + 1)).toString());
			break;
		case "ertsAanbGrp":
			if (Integer.parseInt(ertsAanLbl.getText()) < 9)
				ertsAanLbl.setText(((Integer) (Integer.parseInt(ertsAanLbl.getText()) + 1)).toString());
			break;
		}
		checkInput();
	}

	public void subBtnClick(MouseEvent e) {
		Node source = (Node) e.getSource();
		switch (source.getParent().getId()) {
		case "houtAanbGrp":
			if (Integer.parseInt(houtAanLbl.getText()) > 0)
				houtAanLbl.setText(((Integer) (Integer.parseInt(houtAanLbl.getText()) - 1)).toString());
			break;
		case "wolAanbGrp":
			if (Integer.parseInt(wolAanLbl.getText()) > 0)
				wolAanLbl.setText(((Integer) (Integer.parseInt(wolAanLbl.getText()) - 1)).toString());
			break;
		case "graanAanbGrp":
			if (Integer.parseInt(graanAanLbl.getText()) > 0)
				graanAanLbl.setText(((Integer) (Integer.parseInt(graanAanLbl.getText()) - 1)).toString());
			break;
		case "baksteenAanbGrp":
			if (Integer.parseInt(baksteenAanLbl.getText()) > 0)
				baksteenAanLbl.setText(((Integer) (Integer.parseInt(baksteenAanLbl.getText()) - 1)).toString());
			break;
		case "ertsAanbGrp":
			if (Integer.parseInt(ertsAanLbl.getText()) > 0)
				ertsAanLbl.setText(((Integer) (Integer.parseInt(ertsAanLbl.getText()) - 1)).toString());
			break;
		}
		checkInput();
	}

	protected void checkInput() {
		if (controller.checkSufficient(targetResource, (Integer.parseInt(houtAanLbl.getText())*requiredAmount))) {
			houtAanLbl.getStyleClass().remove("warning");
		} else if (!houtAanLbl.getStyleClass().contains("warning")) {
			houtAanLbl.getStyleClass().add("warning");
		}
		if (controller.checkSufficient(targetResource, (Integer.parseInt(wolAanLbl.getText())*requiredAmount))) {
			wolAanLbl.getStyleClass().remove("warning");
		} else if (!wolAanLbl.getStyleClass().contains("warning")) {
			wolAanLbl.getStyleClass().add("warning");
		}
		if (controller.checkSufficient(targetResource, (Integer.parseInt(graanAanLbl.getText())*requiredAmount))) {
			graanAanLbl.getStyleClass().remove("warning");
		} else if (!graanAanLbl.getStyleClass().contains("warning")) {
			graanAanLbl.getStyleClass().add("warning");
		}
		if (controller.checkSufficient(targetResource, (Integer.parseInt(baksteenAanLbl.getText())*requiredAmount))) {
			baksteenAanLbl.getStyleClass().remove("warning");
		} else if (!baksteenAanLbl.getStyleClass().contains("warning")) {
			baksteenAanLbl.getStyleClass().add("warning");
		}
		if (controller.checkSufficient(targetResource, (Integer.parseInt(ertsAanLbl.getText())*requiredAmount))) {
			ertsAanLbl.getStyleClass().remove("warning");
		} else if (!ertsAanLbl.getStyleClass().contains("warning")) {
			ertsAanLbl.getStyleClass().add("warning");
		}

		if (controller.checkSufficient(targetResource ,retrieveTotalRequestValue()*requiredAmount)) {
			biedBtn.setDisable(false);
		} else {
			biedBtn.setDisable(true);
		}
	}

	public void reject(MouseEvent e) {
		controller.resetTrade();
	}
}
