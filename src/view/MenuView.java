package view;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MenuView
{

	public void initUI(Stage primaryStage)
	{
		Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();


		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
