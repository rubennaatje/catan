package view;

import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SplashScreenView
{
	protected void initUI(Stage primaryStage)
	{
		Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getResource("SplashScreenView.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Splash Screen");
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();

			PauseTransition pause = new PauseTransition(Duration.seconds(3));
			pause.setOnFinished(e -> {
				primaryStage.hide();
			});
			pause.play();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
