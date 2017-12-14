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
		
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> {
			
			try {
				//DatabaseManager.createStatement().execute("DELETE FROM spelergrondstofkaart WHERE idspel = 788");
				//DatabaseManager.createStatement().execute("DELETE FROM chatregel WHERE idspel = 788");
				//DatabaseManager.createStatement().execute("DELETE FROM spelerontwikkelingskaart WHERE idspel = 788");
				//DatabaseManager.createStatement().execute("DELETE FROM speler WHERE idspel = 788");
				//DatabaseManager.createStatement().execute("DELETE FROM spel WHERE idspel = 788");
			} catch (Exception ex) {
				
			}
			
			new LoginView(stage, controller).show();
		});
		
		pause.play();
	}
}
