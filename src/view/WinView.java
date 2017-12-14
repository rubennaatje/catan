<<<<<<< HEAD
package view;

import controller.CatanController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class WinView extends PaneTemplate{
	
	private CatanController controller; 
	@FXML private Button btnBack;
	
	public WinView(Stage stage, CatanController controller) {
		super(WinView.class.getResource("fxml/WinView.fxml"), stage);
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

public class WinView extends PaneTemplate{
	
	private CatanController controller; 
	@FXML private Button btnBack;
	
	public WinView(Stage stage, CatanController controller) {
		super(WinView.class.getResource("fxml/WinView.fxml"), stage);
		this.controller =  controller;
		
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openMenuScreen();
			}
		});
	}
}
>>>>>>> f1de59ecfa9402b08ee6a4f704f8d37311a9535c
