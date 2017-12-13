package view;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Waiting;
import view.javaFXTemplates.PaneTemplate;

import java.sql.SQLException;

import com.jfoenix.controls.JFXTextField;

import controller.CatanController;
import controller.DatabaseManager;


public class WaitingView extends PaneTemplate{
	
	private CatanController controller;
	
	@FXML private JFXTextField notAccepted;
	@FXML private JFXTextField btnDecline;
	
	public WaitingView(Stage stage, CatanController controller, Waiting waiting) {
		super(WaitingView.class.getResource("fxml/WaitingView.fxml"), stage);
		this.controller = controller;
	
		notAccepted.setEditable(false);
		
		btnDecline.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					DatabaseManager.createStatement().executeQuery("UPDATE speler SET speelstatus = 'afgebroken' WHERE idspel = " + waiting.getChallenge().getGameId());
				} catch (SQLException e) {
					
				}
				
				controller.openMenuScreen();
			}
		});
		
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

	}
	
}
