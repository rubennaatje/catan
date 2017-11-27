package view;

import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class GameControlerView extends PaneTemplate {

	@FXML Button townBtn; 
	@FXML Button streetBtn; 
	@FXML Button cityBtn; 
	@FXML Label road; 
	
	
	
	public GameControlerView(Stage stage, EventHandler<? super MouseEvent> clickHandler) {
		super(GameControlerView.class.getResource("fxml/PlayboardButtons.fxml"), stage);
		townBtn.setOnMouseClicked(clickHandler);
		streetBtn.setOnMouseClicked(clickHandler);
		cityBtn.setOnMouseClicked(clickHandler);
	}
	
	public void setLongestRoad(Integer i) {
		road.setText(i.toString());
	}

}
