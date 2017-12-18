package tests;

import controller.DatabaseManager;
import model.PlayerType;
import model.PlayerUser;
import model.TileType;

public class PlayerResourceTest {

	public static void main(String[] args) throws Exception {
		DatabaseManager.connect();
		
		PlayerUser player = new PlayerUser("rik", "770", PlayerType.BLAUW);
		
		player.removeResource(TileType.E, 1);
		player.addResource(TileType.E, 1);
	}

}
