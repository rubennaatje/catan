package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import controller.DatabaseManager;

public class Board {
	
	Integer[][] conf = {{1,2,4,12},
			{2,3,6,18},
			{3,4,8,14},
			{4,3,3,10},
			{5,4,5,16},
			
			{6,5,7,8},
			{7,6,9,1},
			{8,4,2,6},
			{9,5,4,2},
			{10,6,6,0},
			
			{11,7,8,4},
			{12,8,10,13},
			{13,6,3,9},
			{14,7,5,5},
			{15,8,7,3},
			
			{16,9,9,15},
			{17,8,4,17},
			{18,9,6,7},
			{19,10,8,11}};
	
	public Board() {
		
	}

	public void createBoard(int gameId) throws Exception {
		if(gameId < 0) throw new Exception("Parameter cannot be negative!");
		TileType[] tiles = new TileType[18];

		for (int i = 0; i < tiles.length; i++) {
			if (i <= 3)
				tiles[i] = TileType.H;
			if (i >= 4 && i <= 7)
				tiles[i] = TileType.W;
			if (i >= 8 && i <= 11)
				tiles[i] = TileType.G;
			if (i >= 12 && i <= 14)
				tiles[i] = TileType.B;
			if (i >= 15 && i <= 18)
				tiles[i] = TileType.E;
		}
		tiles = shuffleArray(tiles);
		
		int i = 0;
		while (i < conf.length) {
			String resourceValue = null;
			String chipValue = conf[i][3].toString();
			if(i < 9) resourceValue = tiles[i].toString();
			if(i == 9) {
				resourceValue = "X";
				chipValue = "NULL";
			}
			if(i > 9) resourceValue = tiles[i-1].toString();
			System.out.println("INSERT INTO tegel (`idspel`, `idtegel`, `x`, `y`, `idgrondstofsoort`, `idgetalfiche`) VALUES (" + gameId + ", " + conf[i][0] + ", " + conf[i][1] + ", " + conf[i][2] + ", '" + resourceValue + "', " + chipValue + ")");
			DatabaseManager.getStatement().executeUpdate("INSERT INTO tegel (`idspel`, `idtegel`, `x`, `y`, `idgrondstofsoort`, `idgetalfiche`) VALUES (" + gameId + ", " + conf[i][0] + ", " + conf[i][1] + ", " + conf[i][2] + ", '" + resourceValue + "', " + chipValue + ")");
			i++;
		}
	}

	private TileType[] shuffleArray(TileType[] array) {
		Random rnd = new Random();
		for (int i = array.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			TileType a = array[index];
			array[index] = array[i];
			array[i] = a;
		}
		return array;
	}
	
	public void getAvailableStreetPositions(Integer spelId, String username) throws SQLException {
		ResultSet results = DatabaseManager.getStatement().executeQuery("select username, idstuk, x_van, y_van, x_naar, y_naar from spelerstuk where idstuk IN (select idstuk from stuk where stuksoort = \"straat\") and idspel = " + spelId.toString() + ")");
		//String[]
		while(results.next()) {
			results.getString(0);
		}
	}
}