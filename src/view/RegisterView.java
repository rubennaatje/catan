package view;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class RegisterView
{
	protected void initUI(Stage primaryStage)
	{
		Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getResource("Registerview.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Catan");
			primaryStage.show();


		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
