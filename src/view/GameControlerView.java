package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.javaFXTemplates.PaneTemplate;

public class GameControlerView extends PaneTemplate {

	private @FXML Button townBtn; 
	private @FXML Button streetBtn; 
	private @FXML Button cityBtn; 
	private @FXML Button endTurnBtn; 
	private @FXML Button tradeButton;
	private @FXML Button devCardButton;
	private @FXML Label road; 
	
	public GameControlerView(EventHandler<? super MouseEvent> clickHandler, EventHandler<MouseEvent> endTurnEvent, EventHandler<MouseEvent> tradeEvent) {
		super(GameControlerView.class.getResource("fxml/PlayboardButtons.fxml"));
		townBtn.setOnMouseClicked(clickHandler);
		streetBtn.setOnMouseClicked(clickHandler);
		cityBtn.setOnMouseClicked(clickHandler);
		endTurnBtn.setOnMouseClicked(endTurnEvent);
		tradeButton.setOnMouseClicked(tradeEvent);
		devCardButton.setOnMouseClicked(clickHandler);
		setDisabled();
	}
	
	public void setDisabled() {
		townBtn.setDisable(true);
		streetBtn.setDisable(true);
		cityBtn.setDisable(true);
		endTurnBtn.setDisable(true);
		tradeButton.setDisable(true);
		devCardButton.setDisable(true);
	}
	
	public void setEnabled() {
		townBtn.setDisable(false);
		streetBtn.setDisable(false);
		cityBtn.setDisable(false);
		endTurnBtn.setDisable(false);
		devCardButton.setDisable(true);
	}
	
	public void setLongestRoad(Integer i) {
		road.setText(i.toString());
	}
	public void setButtons(boolean town,boolean city, boolean street, boolean endTurn, boolean trade, boolean devCard) {
		townBtn.setDisable(!town);
		cityBtn.setDisable(!city);
		streetBtn.setDisable(!street);
		endTurnBtn.setDisable(!endTurn);
		tradeButton.setDisable(!trade);
		devCardButton.setDisable(!devCard);
	}

}
