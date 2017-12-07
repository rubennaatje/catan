package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

import java.awt.TextArea;
import java.awt.TextField;

import com.jfoenix.controls.JFXTextField;

import controller.CatanController;


public class WaitingView extends PaneTemplate{
	
	private CatanController controller;
	
	@FXML private Button btnBack;
	
	public WaitingView(Stage stage, CatanController controller) {
		super(WaitingView.class.getResource("fxml/WaitingView.fxml"), stage);
		this.controller = controller;

		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.openChallengeScreen();
			}
		});
		
		//notAccepted.setEditable(false); 
	}
	
}
