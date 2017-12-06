package view;

import com.jfoenix.controls.JFXButton;

import controller.CatanController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class MenuView extends PaneTemplate {
	
	private CatanController controller;
	@FXML private JFXButton btnCreate;
	@FXML private JFXButton btnChallenge;
	@FXML private JFXButton btnRanking;
	@FXML private JFXButton btnLogout;

	public MenuView(Stage stage, CatanController controller) {
		super(MenuView.class.getResource("fxml/MenuView.fxml"), stage);
		this.controller = controller;
		
		btnCreate.setOnAction(new EventHandler<ActionEvent>() {
				
			@Override
			public void handle(ActionEvent event) {
				
			}
		});

		btnChallenge.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openChallengeScreen();
			}
		});

		btnRanking.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openLeaderboardScreen();
			}
		});
		
		btnLogout.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openLoginScreen();
			}
		});
	}
	
}
