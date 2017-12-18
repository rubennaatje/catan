package view;

import java.util.HashMap;

import com.jfoenix.controls.JFXButton;

import controller.TradeController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.TileType;
import view.javaFXTemplates.TradeViewTemplate;

public class TradeView extends TradeViewTemplate {

	// labels for trade ratios to bank
	@FXML
	private JFXButton houtBnkLbl;
	@FXML
	private JFXButton wolBnkLbl;
	@FXML
	private JFXButton graanBnkLbl;
	@FXML
	private JFXButton baksteenBnkLbl;
	@FXML
	private JFXButton ertsBnkLbl;

	// button for creating offer
	@FXML
	private Button biedBtn;

	// data for creating counteroffer boxes
	private int boxAmount = 0;
	private final int[][] positions = { { 162, 133 }, { 0, 407 }, { 323, 407 } };

	public TradeView(TradeController controller) {
		super(TradeView.class.getResource("fxml/trade.fxml"), controller);

	}

	public void bnkTrade(MouseEvent e) {
		controller.showBank(e);
	}

	public void addPlayerOffer(HashMap<TileType, Integer>[] offer, String username) {
		if (boxAmount < 3) {
			TradeOfferComponentView comView = new TradeOfferComponentView(controller, username, offer);
			comView.setLayoutX(positions[boxAmount][0]);
			comView.setLayoutY(positions[boxAmount][1]);
			getChildren().add(comView);
			boxAmount++;
		}
	}

	@Override
	protected void checkInput() {
		super.checkInput();
		biedBtn.setDisable(!controller.checkAllSufficient(retrieveOfferValues()));
	}

	public void offerBtnClick(MouseEvent e) {
		controller.submitTradeRequest(retrieveValues());
		clearTradeFld();
		getChildren().clear();
	}

	public void addRejectBtn() {
		Button rejectbtn = new Button("Afwijzen");
		getChildren().add(rejectbtn);
		rejectbtn.setLayoutX(260);
		rejectbtn.setLayoutY(600);
		rejectbtn.setOnMouseClicked((e) -> {
			rejectCounters(e);
			System.out.println("clicking button");
		});
	}

	public void rejectCounters(MouseEvent e) {
		controller.registerCounterReject();
	}

	public void setBankLabels(HashMap<TileType, Integer> tradeHavens) {
		houtBnkLbl.setText((tradeHavens.get(TileType.H)).toString() + ":1");
		wolBnkLbl.setText((tradeHavens.get(TileType.W)).toString() + ":1");
		graanBnkLbl.setText((tradeHavens.get(TileType.G)).toString() + ":1");
		baksteenBnkLbl.setText((tradeHavens.get(TileType.B)).toString() + ":1");
		ertsBnkLbl.setText((tradeHavens.get(TileType.E)).toString() + ":1");
	}


	public void showBankTradeWindow(TradeController tradeController, Integer integer, TileType h) {
		BankTradeComponentView bnk = new BankTradeComponentView(tradeController, integer, h);
		getChildren().clear();
		getChildren().add(bnk);
		bnk.setLayoutY(50);
	}

	public void cancel(MouseEvent e) {
		controller.closeTrade();
	}
}
