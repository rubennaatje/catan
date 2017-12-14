package view;

import controller.CatanController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class LoseView extends PaneTemplate {
	
	private CatanController controller; 
	@FXML private Button btnBack;
	
	public LoseView(Stage stage, CatanController controller) {
		super(LoseView.class.getResource("fxml/LoseView.fxml"), stage);
		this.controller =  controller;
		
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openMenuScreen();
			}
		});
	}
}
=======
package view;

import controller.CatanController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class LoseView extends PaneTemplate {
	
	private CatanController controller; 
	@FXML private Button btnBack;
	
	public LoseView(Stage stage, CatanController controller) {
		super(LoseView.class.getResource("fxml/LoseView.fxml"), stage);
		this.controller =  controller;
		
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openMenuScreen();
			}
		});
	}
}
