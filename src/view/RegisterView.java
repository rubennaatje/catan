package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controller.AlertManager;
import controller.CatanController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
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
				new Thread(() ->  {
					if (txtPassword.getText().equals(txtRePassword.getText())) {
						if (controller.getCatan().register(txtUsername.getText(), txtPassword.getText())) {
							Platform.runLater(new Runnable() {
							    @Override
							    public void run() {
							    	controller.openLoginScreen();
							    }
							});
						} else {
							Platform.runLater(new Runnable() {
							    @Override
							    public void run() {
							    	new AlertManager(AlertType.ERROR, "Register error!", "could not register");
							    }
							});
						}
					} else {
						
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	new AlertManager(AlertType.ERROR, "Register error!", "please fill in the same passwords");
								txtRePassword.setText("");
						    }
						});
					}
				}).start();
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