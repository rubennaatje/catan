package view;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;


import com.jfoenix.controls.JFXTextField;

import controller.CatanController;


public class WaitingView extends PaneTemplate{
	
	private CatanController controller;
	
	@FXML private Button btnBack;
	@FXML private JFXTextField notAccepted ;
	
	public WaitingView(Stage stage, CatanController controller) {
		super(WaitingView.class.getResource("fxml/WaitingView.fxml"), stage);
		this.controller = controller;
	
		notAccepted.setEditable(false); 
		
		new Thread(() -> {
			while (controller.getWaitingOn() > 0) {
				notAccepted.setText(Integer.toString(controller.getWaitingOn()));
				
				try {
					Thread.sleep(CatanController.refreshTime);
				} catch (Exception e) {
					e.printStackTrace(); 
				}
			}
			
			notAccepted.setText(Integer.toString(controller.getWaitingOn()));
			
		}).start();
		
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.openChallengesScreen();
			}
		});

	}
	
}
