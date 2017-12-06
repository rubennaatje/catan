package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controller.CatanController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class RegisterView extends PaneTemplate {

	private CatanController controller;
	
	@FXML private JFXTextField txtUsername;
	@FXML private JFXPasswordField txtPassword;
	@FXML private JFXPasswordField txtRePassword;
	@FXML private JFXButton btnRegister;
	@FXML private JFXButton btnBack;
	
	public RegisterView(Stage stage, CatanController controller) {
		super(MenuView.class.getResource("fxml/RegisterView.fxml"), stage);
		
		btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openLoginScreen();
			}
		});
	}
	
}