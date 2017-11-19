package view;

import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class SplashScreenView extends PaneTemplate {
	
	public SplashScreenView(Stage stage) {
		super(SplashScreenView.class.getResource("fxml/SplashScreenView.fxml"), stage);
	}
	
	public void show() {
		super.show();
	}
}
