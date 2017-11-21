package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;

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
	public void start(Stage stage) {
		new LoginView(stage).show();
	}
}