package view;

import java.sql.SQLException;

import controller.CatanController;
import controller.DatabaseManager;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.javaFXTemplates.PaneTemplate;

public class SplashScreenView extends PaneTemplate
{
	private CatanController controller;
	
	public SplashScreenView(Stage stage, CatanController controller) {
		super(SplashScreenView.class.getResource("fxml/SplashScreenView.fxml"), stage);
		this.controller = controller;
	}

	public void show() {
		super.show();
		
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		pause.setOnFinished(e -> {
			
			try {
				//DatabaseManager.createStatement().execute("DELETE FROM spel WHERE idspel = 793");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			new LoginView(stage, controller).show();
		});
		
		pause.play();
	}
}
