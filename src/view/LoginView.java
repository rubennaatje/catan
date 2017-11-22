package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class LoginView extends PaneTemplate {
	
	@FXML private JFXTextField txtUsername;
	@FXML private JFXPasswordField txtPassword;
	@FXML private JFXButton btnLogin;
	@FXML private JFXButton btnRegister;
	
	public LoginView(Stage stage) {
		super(LoginView.class.getResource("fxml/LoginView.fxml"), stage);
		
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				new MenuView(stage).show();
			}
		});
		
		btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				new RegisterView(stage).show();
			}
		});
	}
}
