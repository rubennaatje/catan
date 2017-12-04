package view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class TradeView extends PaneTemplate {

	// labels for trade ratios to bank
	@FXML
	Label houtBnkLbl;
	@FXML
	Label wolBnkLbl;
	@FXML
	Label graanBnkLbl;
	@FXML
	Label baksteenBnkLbl;
	@FXML
	Label ertsBnkLbl;

	@FXML
	TextField houtVraLbl;
	@FXML
	TextField wolVraLbl;
	@FXML
	TextField graanVraLbl;
	@FXML
	TextField baksteenVraLbl;
	@FXML
	TextField ertsVraLbl;

	@FXML
	TextField houtAanLbl;
	@FXML
	TextField wolAanLbl;
	@FXML
	TextField graanAanLbl;
	@FXML
	TextField baksteenAanLbl;
	@FXML
	TextField ertsAanLbl;

	@FXML
	Button biedBtn;

	public TradeView(Stage primaryStage) {
		super(TradeView.class.getResource("fxml/trade.fxml"), primaryStage);
	}

	public TradeView() {
		super(TradeView.class.getResource("fxml/trade.fxml"));
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
	}

	public void offerBtnClick() {

	}
}
