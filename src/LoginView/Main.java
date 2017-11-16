package LoginView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		LoginView loginview = new LoginView();
		loginview.laucnhview(primaryStage);
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
