package view;

import java.util.HashMap;

import controller.TradeController;
import javafx.event.EventDispatchChain;
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
	private Label houtBnkLbl;
	@FXML
	private Label wolBnkLbl;
	@FXML
	private Label graanBnkLbl;
	@FXML
	private Label baksteenBnkLbl;
	@FXML
	private Label ertsBnkLbl;

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

	
	public TradeView(Stage primaryStage, TradeController controller) {
		super(TradeView.class.getResource("fxml/trade.fxml"), primaryStage);
		
		this.controller = controller;
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


	@SuppressWarnings("unchecked")
	public void offerBtnClick(MouseEvent e) {
		HashMap<String, Integer> offer = new HashMap<>();
		HashMap<String, Integer> request = new HashMap<>();
		
		
		offer.put("Baksteen", Integer.parseInt(baksteenAanLbl.getText()));
		offer.put("Erts", Integer.parseInt(ertsAanLbl.getText()));
		offer.put("Graan", Integer.parseInt(graanAanLbl.getText()));
		offer.put("Hout", Integer.parseInt(houtAanLbl.getText()));
		offer.put("Wol", Integer.parseInt(wolAanLbl.getText()));
		
		request.put("Baksteen", Integer.parseInt(baksteenVraLbl.getText()));
		request.put("Erts", Integer.parseInt(ertsVraLbl.getText()));
		request.put("Graan", Integer.parseInt(graanVraLbl.getText()));
		request.put("Hout", Integer.parseInt(houtVraLbl.getText()));
		request.put("Wol", Integer.parseInt(wolVraLbl.getText()));
		
		
		
		
		clearTradeFld();
		
		@SuppressWarnings("rawtypes")
		HashMap[] bloob = {request, offer};
		controller.submitTrade(bloob);
	}
	
	
	private void clearTradeFld() {
		baksteenAanLbl.setText("0");
		 baksteenVraLbl.setText("0");
		 
		 ertsAanLbl.setText("0");
		 ertsVraLbl.setText("0");
		 
		 graanAanLbl.setText("0");
		 graanVraLbl.setText("0");
		 
		 houtAanLbl.setText("0");
		 houtVraLbl.setText("0");
		 
		 wolAanLbl.setText("0");
		 wolVraLbl.setText("0");
	}
}
