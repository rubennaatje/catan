package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;
import view.SplashScreenView;

public class Main extends Application {

	public static void main(String[] args) {
		DatabaseManager.connect();
		
		launch(args);
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run() {
		    	DatabaseManager.disconnect();
		    }
		});
	}

	@Override
	public void start(Stage stage) throws Exception {
		//Load spashscreen 
		SplashScreenView Spl = new SplashScreenView();
		
		//Check if Splashscreen is done
		boolean ready = Spl.getDoneLoading();
		while(ready == false) {
			ready = Spl.getDoneLoading();
			System.out.println("loading");
		}
		
		//Load loginView
		LoginView loginview = new LoginView();
		loginview.laucnhview(stage);
		System.out.println("ready");
	}

}