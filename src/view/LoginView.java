package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controller.AlertManager;
import controller.CatanController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class LoginView extends PaneTemplate {
	
	@FXML private JFXTextField txtUsername;
	@FXML private JFXPasswordField txtPassword;
	@FXML private JFXButton btnLogin;
	@FXML private JFXButton btnRegister;
	
	private CatanController controller;
	
	public LoginView(Stage stage, CatanController controller) {
		super(LoginView.class.getResource("fxml/LoginView.fxml"), stage);
		this.controller = controller;
		
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (controller.getCatan().login(txtUsername.getText(), txtPassword.getText())) {
					controller.setPlayer(txtUsername.getText());
					controller.openMenuScreen();
				} else {
					new AlertManager(AlertType.ERROR, "Login error!", "username and/or password are incorrect");
					txtPassword.setText("");
				}
			}
		});
		
		btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openRegisterScreen();
			}
		});
	}
}
