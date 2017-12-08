package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.javaFXTemplates.PaneTemplate;

public class GameControlerView extends PaneTemplate {

	@FXML Button townBtn; 
	@FXML Button streetBtn; 
	@FXML Button cityBtn; 
	@FXML Button endTurnBtn; 
	@FXML Label road; 
	
	
	
	public GameControlerView(EventHandler<? super MouseEvent> clickHandler, EventHandler<MouseEvent> endTurnEvent) {
		super(GameControlerView.class.getResource("fxml/PlayboardButtons.fxml"));
		townBtn.setOnMouseClicked(clickHandler);
		streetBtn.setOnMouseClicked(clickHandler);
		cityBtn.setOnMouseClicked(clickHandler);
		endTurnBtn.setOnMouseClicked(endTurnEvent);
		setDisabled();
	}
	
	public void setDisabled() {
		townBtn.setDisable(true);
		streetBtn.setDisable(true);
		cityBtn.setDisable(true);
		endTurnBtn.setDisable(true);
	}
	
	public void setEnabled() {
		townBtn.setDisable(false);
		streetBtn.setDisable(false);
		cityBtn.setDisable(false);
		endTurnBtn.setDisable(false);
	}
	
	public void setLongestRoad(Integer i) {
		road.setText(i.toString());
	}

}
