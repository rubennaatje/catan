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
	
	//data for creating counteroffer boxes 
	private int boxAmount = 0;
	private final int[][] positions = {{162, 183}, {0, 457}, {323, 457}};
	
	public TradeView(TradeController controller) {
		super(TradeView.class.getResource("fxml/trade.fxml"), controller);
	}

	public void addPlayerOffer(HashMap<TileType, Integer>[] offer, String username) {
		if(boxAmount <3) {
			TradeOfferComponentView comView = new TradeOfferComponentView(controller, username, offer);
			comView.setLayoutX(positions[boxAmount][0]);
			comView.setLayoutY(positions[boxAmount][1]);
			getChildren().add(comView);
			boxAmount++;
		}
	}

	public void offerBtnClick(MouseEvent e) {
		controller.submitTradeRequest(retrieveValues());
		clearTradeFld();		
		getChildren().clear();
		
		
	}
	
	public void addRejectBtn() {
		Button rejectbtn = new Button("Afwijzen");
		getChildren().add(rejectbtn);
		rejectbtn.setLayoutX((getWidth()-rejectbtn.getWidth())/2);
		rejectbtn.setLayoutY(470);
		rejectbtn.setOnMouseClicked((e) -> rejectCounters(e));
	}
	
	public void rejectCounters(MouseEvent e) {
		controller.registerCounterReject();
	}
	public void setBankLabels(HashMap<TileType,Integer> tradeHavens)
	{
		houtBnkLbl.setText((tradeHavens.get(TileType.H)).toString());
		wolBnkLbl.setText((tradeHavens.get(TileType.W)).toString());
		graanBnkLbl.setText((tradeHavens.get(TileType.G)).toString());
		baksteenBnkLbl.setText((tradeHavens.get(TileType.B)).toString());
		ertsBnkLbl.setText((tradeHavens.get(TileType.E)).toString());
	}

	public void reset() {
		getChildren().clear();
		loadFxml(TradeView.class.getResource("fxml/trade.fxml"), this);
	}
}
