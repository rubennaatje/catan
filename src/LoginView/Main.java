package LoginView;

import java.net.MalformedURLException;

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
		//Load spashscreen
		SplashScreen Spl = new SplashScreen();
		
		//Check if Splashscreen is done
		boolean ready = Spl.getDoneLoading();
		while(ready == false) {
			ready = Spl.getDoneLoading();
			System.out.println("loading");
		}
		
		//Load loginView
		LoginView loginview = new LoginView();
		loginview.laucnhview(primaryStage);
	}

	public static void main(String[] args)
	{
		//start the application
		launch(args);
	}
}
