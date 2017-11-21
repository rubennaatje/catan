package view;

import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.javaFXTemplates.PaneTemplate;

public class SplashScreenView extends PaneTemplate
{
	public SplashScreenView(Stage stage) {
		super(SplashScreenView.class.getResource("fxml/SplashScreenView.fxml"), stage);
	}

	public void show() {
		super.show();
		
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> {
			new LoginView(stage).show();
		});
		
		pause.play();
	}
}
