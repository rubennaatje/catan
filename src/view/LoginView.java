package view;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.javaFXTemplates.PaneTemplate;

public class LoginView extends PaneTemplate {
	
	public LoginView(Stage stage) {
		super(LoginView.class.getResource("fxml/LoginView.fxml"), stage);
	}
	
	public void show() {
		super.show();
	}
}
