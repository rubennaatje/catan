package tests;

import controller.DatabaseManager;
import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Catan;
import model.PlayerModel;
import model.PlayerUser;

public class TestStartController extends Application {

	
	static GameController gameController;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		DatabaseManager.connect();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DatabaseManager.disconnect();
			System.out.println("databasemanager shut down");
		}));
		
		Catan catan = new Catan();
		//catan.initGame();
		Catan.setGameId("770");
		//catan.addResourceCards();
		launch(args);
		
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		String spelId = "770";

        Catan catan = new Catan();
        //catan.initGame();
        Catan.setGameId("770");
        
        PlayerUser player = new PlayerUser("ger", Catan.getGameId());
        catan.setPlayer(player);
        PlayerModel[] players = catan.getCurrentPlayers();
        gameController = new GameController(spelId, players, player.getPlayerNumber() ,primaryStage);
        
        Runnable boob = new Runnable () {
			@Override
			public void run() {
				gameController.start();
			}
        };
		
		
		
		new Thread(boob).start();

		

	}

}
