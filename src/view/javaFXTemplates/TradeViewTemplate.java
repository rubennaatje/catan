package view.javaFXTemplates;

import java.net.URL;
import java.util.HashMap;

import controller.TradeController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.TileType;

public abstract class TradeViewTemplate extends PaneTemplate {

	protected TradeController controller;
	@FXML
	private Label sendingPlayer;
	@FXML
	protected TextField houtVraLbl;
	@FXML
	protected TextField wolVraLbl;
	@FXML
	protected TextField graanVraLbl;
	@FXML
	protected TextField baksteenVraLbl;
	@FXML
	protected TextField ertsVraLbl;

	@FXML
	protected TextField houtAanLbl;
	@FXML
	protected TextField wolAanLbl;
	@FXML
	protected TextField graanAanLbl;
	@FXML
	protected TextField baksteenAanLbl;
	@FXML
	protected TextField ertsAanLbl;

	public TradeViewTemplate(URL url, TradeController controller) {
		super(url);
		this.controller = controller;
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

	protected void checkInput() {
		if (controller.checkSufficient(TileType.H, (Integer.parseInt(houtAanLbl.getText())))) {
			houtAanLbl.getStyleClass().remove("warning");
		} else if (!houtAanLbl.getStyleClass().contains("warning")) {
			houtAanLbl.getStyleClass().add("warning");
		}
		if (controller.checkSufficient(TileType.W, (Integer.parseInt(wolAanLbl.getText())))) {
			wolAanLbl.getStyleClass().remove("warning");
		} else if (!wolAanLbl.getStyleClass().contains("warning")) {
			wolAanLbl.getStyleClass().add("warning");
		}
		if (controller.checkSufficient(TileType.G, (Integer.parseInt(graanAanLbl.getText())))) {
			graanAanLbl.getStyleClass().remove("warning");
		} else if (!graanAanLbl.getStyleClass().contains("warning")) {
			graanAanLbl.getStyleClass().add("warning");
		}
		if (controller.checkSufficient(TileType.B, (Integer.parseInt(baksteenAanLbl.getText())))) {
			baksteenAanLbl.getStyleClass().remove("warning");
		} else if (!baksteenAanLbl.getStyleClass().contains("warning")) {
			baksteenAanLbl.getStyleClass().add("warning");
		}
		if (controller.checkSufficient(TileType.E, (Integer.parseInt(ertsAanLbl.getText())))) {
			ertsAanLbl.getStyleClass().remove("warning");
		} else if (!ertsAanLbl.getStyleClass().contains("warning")) {
			ertsAanLbl.getStyleClass().add("warning");
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

	@SuppressWarnings("unchecked")
	protected HashMap<TileType, Integer>[] retrieveValues() {
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

		@SuppressWarnings("rawtypes")
		HashMap[] bloob = { offer, request };
		return bloob;
	}

	protected void fillLabels(HashMap<TileType, Integer>[] offerData) {
		houtVraLbl.setText(offerData[0].get(TileType.H).toString());
		wolVraLbl.setText(offerData[0].get(TileType.W).toString());
		graanVraLbl.setText(offerData[0].get(TileType.G).toString());
		baksteenVraLbl.setText(offerData[0].get(TileType.B).toString());
		ertsVraLbl.setText(offerData[0].get(TileType.E).toString());

		houtAanLbl.setText(offerData[1].get(TileType.H).toString());
		wolAanLbl.setText(offerData[1].get(TileType.W).toString());
		graanAanLbl.setText(offerData[1].get(TileType.G).toString());
		baksteenAanLbl.setText(offerData[1].get(TileType.B).toString());
		ertsAanLbl.setText(offerData[1].get(TileType.E).toString());
	}

	protected void clearTradeFld() {
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
