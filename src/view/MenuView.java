package view;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class MenuView extends PaneTemplate {
	
	@FXML private JFXButton btnCreate;
	@FXML private JFXButton btnChallenge;
	@FXML private JFXButton btnRanking;
	@FXML private JFXButton btnLogout;

	public MenuView(Stage stage) {
		super(MenuView.class.getResource("fxml/MenuView.fxml"), stage);
		
		btnCreate.setOnAction(new EventHandler<ActionEvent>() {
				
			@Override
			public void handle(ActionEvent event) {
				
			}
		});

		btnChallenge.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				new ChallengeView(stage).show();
			}
		});

		btnRanking.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				new LeaderBoardView(stage).show();
			}
		});
		
		btnLogout.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				new LoginView(stage).show();
			}
		});
	}
	
}
