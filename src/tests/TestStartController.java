package tests;

import controller.DatabaseManager;
import controller.DevelopCardController;
import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.BoardHelper;
import model.Catan;
import model.PlayerModel;
import model.PlayerUser;

public class TestStartController extends Application {

	
	static GameController gameController;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		DatabaseManager.connect();
		
		Catan catan = new Catan();
		//catan.initGame();
		Catan.setGameId("770");
		//catan.addResourceCards();
		launch(args);
		System.exit(0);

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
        gameController = new GameController(spelId, players, player.getPlayerNumber() -1 ,primaryStage);
      
        primaryStage.setTitle(player.getUsername());
        //tests wouter
     /*   DevelopCardController devcard = new DevelopCardController(player, gameController );
        devcard.refreshDevCards();
        if(devcard.checkForResource(0)) {
        	 devcard.setResourceType(0, "hout");
        }
        devcard.playCard(0);*/
		new Thread(() -> gameController.start()).start();
  }
}
