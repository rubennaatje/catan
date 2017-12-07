package view;

import com.jfoenix.controls.JFXButton;

import controller.CatanController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class MenuView extends PaneTemplate {
	
	private CatanController controller;
	
	@FXML private JFXButton btnCreate;
	@FXML private JFXButton btnChallenge;
	@FXML private JFXButton btnRanking;
	@FXML private JFXButton btnLogout;
	@FXML private Text txtUsername;

	public MenuView(Stage stage, CatanController controller) {
		super(MenuView.class.getResource("fxml/MenuView.fxml"), stage);
		this.controller = controller;
		
		txtUsername.setText(controller.getPlayer().getUsername());
		txtUsername.setTextAlignment(TextAlignment.CENTER);
		
		btnCreate.setOnAction(new EventHandler<ActionEvent>() {
				
			@Override
			public void handle(ActionEvent event) {
				controller.openChallengeScreen();
			}
		});

		btnChallenge.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openChallengesScreen();
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
