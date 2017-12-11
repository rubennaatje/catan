package view;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controller.AlertManager;
import controller.CatanController;
import controller.DatabaseManager;
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
					
					String gameid = null;
					
					try {
						ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT DISTINCT(idspel), username FROM speler WHERE idspel NOT IN (SELECT idspel FROM speler WHERE speelstatus = 'uitgespeeld' OR speelstatus = 'afgebroken' OR speelstatus = 'geweigerd' OR speelstatus = 'uitgedaagde') AND username = '" + controller.getPlayer().getUsername() + "'");
						result.next();
						gameid = result.getString(1);
						result.close();
					} catch(SQLException e) {
						
					}
					
					if (gameid != null) {
						controller.startGame(gameid, false);
					} else {
						controller.openMenuScreen();
					}
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
