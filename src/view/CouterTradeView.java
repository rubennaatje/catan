package view;

import java.util.HashMap;

import controller.TradeController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.TileType;
import view.javaFXTemplates.PaneTemplate;

public class CouterTradeView extends PaneTemplate {


	@FXML
	private Label sendingPlayer;

	//Opponents offer fields
	@FXML
	private TextField houtVraOffer;
	@FXML
	private TextField wolVraOffer;
	@FXML
	private TextField graanVraOffer;
	@FXML
	private TextField baksteenVraOffer;
	@FXML
	private TextField ertsVraOffer;
	
	@FXML
	private TextField houtAanOffer;
	@FXML
	private TextField wolAanOffer;
	@FXML
	private TextField graanAanOffer;
	@FXML
	private TextField baksteenAaOffer;
	@FXML
	private TextField ertsAanOffer;
	
	
	//player customisable offer fields
	@FXML
	private TextField houtVraLbl;
	@FXML
	private TextField wolVraLbl;
	@FXML
	private TextField graanVraLbl;
	@FXML
	private TextField baksteenVraLbl;
	@FXML
	private TextField ertsVraLbl;

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

	@FXML
	private Button biedBtn;

	private TradeController controller;

	public CouterTradeView(Stage primaryStage, TradeController controller) {
		super(CouterTradeView.class.getResource("fxml/trade.fxml"), primaryStage);

		this.controller = controller;
	}

	public CouterTradeView() {
		super(CouterTradeView.class.getResource("fxml/trade.fxml"));
	}

	public void addBtnClick(MouseEvent e) {
		Node source = (Node) e.getSource();
		switch (source.getParent().getId()) {
		case "houtVraGrp":
			if (Integer.parseInt(houtVraLbl.getText()) < 9)
				houtVraLbl.setText(((Integer) (Integer.parseInt(houtVraLbl.getText()) + 1)).toString());
			break;
		case "wolVraGrp":
			if (Integer.parseInt(wolVraLbl.getText()) < 9)
				wolVraLbl.setText(((Integer) (Integer.parseInt(wolVraLbl.getText()) + 1)).toString());
			break;
		case "graanVraGrp":
			if (Integer.parseInt(graanVraLbl.getText()) < 9)
				graanVraLbl.setText(((Integer) (Integer.parseInt(graanVraLbl.getText()) + 1)).toString());
			break;
		case "baksteenVraGrp":
			if (Integer.parseInt(baksteenVraLbl.getText()) < 9)
				baksteenVraLbl.setText(((Integer) (Integer.parseInt(baksteenVraLbl.getText()) + 1)).toString());
			break;
		case "ertsVraGrp":
			if (Integer.parseInt(ertsVraLbl.getText()) < 9)
				ertsVraLbl.setText(((Integer) (Integer.parseInt(ertsVraLbl.getText()) + 1)).toString());
			break;
		case "houtAanbGrp":
			if (Integer.parseInt(houtAanLbl.getText()) < 9)
				houtAanLbl.setText(((Integer) (Integer.parseInt(houtAanLbl.getText()) + 1)).toString());
			if (!controller.checkSufficient(TileType.H, (Integer.parseInt(houtAanLbl.getText())))
					&& !houtAanLbl.getStyleClass().contains("warning"))
				houtAanLbl.getStyleClass().add("warning");
			break;
		case "wolAanbGrp":
			if (Integer.parseInt(wolAanLbl.getText()) < 9)
				wolAanLbl.setText(((Integer) (Integer.parseInt(wolAanLbl.getText()) + 1)).toString());
			if (!controller.checkSufficient(TileType.W, (Integer.parseInt(wolAanLbl.getText())))
					&& !wolAanLbl.getStyleClass().contains("warning"))
				wolAanLbl.getStyleClass().add("warning");
			break;
		case "graanAanbGrp":
			if (Integer.parseInt(graanAanLbl.getText()) < 9)
				graanAanLbl.setText(((Integer) (Integer.parseInt(graanAanLbl.getText()) + 1)).toString());
			if (!controller.checkSufficient(TileType.G, (Integer.parseInt(graanAanLbl.getText())))
					&& !graanAanLbl.getStyleClass().contains("warning"))
				graanAanLbl.getStyleClass().add("warning");
			break;
		case "baksteenAanbGrp":
			if (Integer.parseInt(baksteenAanLbl.getText()) < 9)
				baksteenAanLbl.setText(((Integer) (Integer.parseInt(baksteenAanLbl.getText()) + 1)).toString());
			if (!controller.checkSufficient(TileType.B, (Integer.parseInt(baksteenAanLbl.getText())))
					&& !baksteenAanLbl.getStyleClass().contains("warning"))
				baksteenAanLbl.getStyleClass().add("warning");
			break;
		case "ertsAanbGrp":
			if (Integer.parseInt(ertsAanLbl.getText()) < 9)
				ertsAanLbl.setText(((Integer) (Integer.parseInt(ertsAanLbl.getText()) + 1)).toString());
			if (!controller.checkSufficient(TileType.E, (Integer.parseInt(ertsAanLbl.getText())))
					&& !ertsAanLbl.getStyleClass().contains("warning"))
				ertsAanLbl.getStyleClass().add("warning");
			break;
		}
	}

	public void subBtnClick(MouseEvent e) {
		Node source = (Node) e.getSource();
		switch (source.getParent().getId()) {
		case "houtVraGrp":
			if (Integer.parseInt(houtVraLbl.getText()) > 0)
				houtVraLbl.setText(((Integer) (Integer.parseInt(houtVraLbl.getText()) - 1)).toString());
			break;
		case "wolVraGrp":
			if (Integer.parseInt(wolVraLbl.getText()) > 0)
				wolVraLbl.setText(((Integer) (Integer.parseInt(wolVraLbl.getText()) - 1)).toString());
			break;
		case "graanVraGrp":
			if (Integer.parseInt(graanVraLbl.getText()) > 0)
				graanVraLbl.setText(((Integer) (Integer.parseInt(graanVraLbl.getText()) - 1)).toString());
			break;
		case "baksteenVraGrp":
			if (Integer.parseInt(baksteenVraLbl.getText()) > 0)
				baksteenVraLbl.setText(((Integer) (Integer.parseInt(baksteenVraLbl.getText()) - 1)).toString());
			break;
		case "ertsVraGrp":
			if (Integer.parseInt(ertsVraLbl.getText()) > 0)
				ertsVraLbl.setText(((Integer) (Integer.parseInt(ertsVraLbl.getText()) - 1)).toString());
			break;
		case "houtAanbGrp":
			if (Integer.parseInt(houtAanLbl.getText()) > 0)
				houtAanLbl.setText(((Integer) (Integer.parseInt(houtAanLbl.getText()) - 1)).toString());
			if (controller.checkSufficient(TileType.H, (Integer.parseInt(houtAanLbl.getText()))))
				houtAanLbl.getStyleClass().remove("warning");
			break;
		case "wolAanbGrp":
			if (Integer.parseInt(wolAanLbl.getText()) > 0)
				wolAanLbl.setText(((Integer) (Integer.parseInt(wolAanLbl.getText()) - 1)).toString());
			if (controller.checkSufficient(TileType.W, (Integer.parseInt(wolAanLbl.getText()))))
				wolAanLbl.getStyleClass().remove("warning");
			break;
		case "graanAanbGrp":
			if (Integer.parseInt(graanAanLbl.getText()) > 0)
				graanAanLbl.setText(((Integer) (Integer.parseInt(graanAanLbl.getText()) - 1)).toString());
			if (controller.checkSufficient(TileType.G, (Integer.parseInt(graanAanLbl.getText()))))
				graanAanLbl.getStyleClass().remove("warning");
			break;
		case "baksteenAanbGrp":
			if (Integer.parseInt(baksteenAanLbl.getText()) > 0)
				baksteenAanLbl.setText(((Integer) (Integer.parseInt(baksteenAanLbl.getText()) - 1)).toString());
			if (controller.checkSufficient(TileType.B, (Integer.parseInt(baksteenAanLbl.getText()))))
				baksteenAanLbl.getStyleClass().remove("warning");
			break;
		case "ertsAanbGrp":
			if (Integer.parseInt(ertsAanLbl.getText()) > 0)
				ertsAanLbl.setText(((Integer) (Integer.parseInt(ertsAanLbl.getText()) - 1)).toString());
			if (controller.checkSufficient(TileType.E, (Integer.parseInt(ertsAanLbl.getText()))))
				ertsAanLbl.getStyleClass().remove("warning");
			break;
		}
	}

	@SuppressWarnings("unchecked")
	public void offerBtnClick(MouseEvent e) {
		HashMap<TileType, Integer> offer = new HashMap<>();
		HashMap<TileType, Integer> request = new HashMap<>();

		offer.put(TileType.B, Integer.parseInt(baksteenAanLbl.getText()));
		offer.put(TileType.E, Integer.parseInt(ertsAanLbl.getText()));
		offer.put(TileType.G, Integer.parseInt(graanAanLbl.getText()));
		offer.put(TileType.H, Integer.parseInt(houtAanLbl.getText()));
		offer.put(TileType.W, Integer.parseInt(wolAanLbl.getText()));

		request.put(TileType.B, Integer.parseInt(baksteenVraLbl.getText()));
		request.put(TileType.E, Integer.parseInt(ertsVraLbl.getText()));
		request.put(TileType.G, Integer.parseInt(graanVraLbl.getText()));
		request.put(TileType.H, Integer.parseInt(houtVraLbl.getText()));
		request.put(TileType.W, Integer.parseInt(wolVraLbl.getText()));

		clearTradeFld();

		@SuppressWarnings("rawtypes")
		HashMap[] bloob = {offer, request};
		controller.submitCounterTradeRequest(bloob);
	}

	private void clearTradeFld() {
		baksteenAanLbl.setText("0");
		baksteenAanLbl.getStyleClass().remove("warning");
		
		baksteenVraLbl.setText("0");

		ertsAanLbl.setText("0");
		ertsAanLbl.getStyleClass().remove("warning");
		ertsVraLbl.setText("0");

		graanAanLbl.setText("0");
		graanAanLbl.getStyleClass().remove("warning");
		graanVraLbl.setText("0");

		houtAanLbl.setText("0");
		houtAanLbl.getStyleClass().remove("warning");
		houtVraLbl.setText("0");

		wolAanLbl.setText("0");
		wolAanLbl.getStyleClass().remove("warning");
		wolVraLbl.setText("0");
	}
}
