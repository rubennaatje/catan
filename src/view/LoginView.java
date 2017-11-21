package view;

import com.jfoenix.controls.JFXPasswordField;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.javaFXTemplates.PaneTemplate;

public class LoginView extends PaneTemplate {
	
	@FXML JFXPasswordField testID;
	
	public LoginView(Stage stage) {
		super(LoginView.class.getResource("fxml/LoginView.fxml"), stage);
	}
	
	public void getFieldValue(MouseEvent event) {
		System.out.println(testID.getText());
	}
}
