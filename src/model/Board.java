package model;
/*Responsibilities:
	- Create playboard in db
	- Give list of available street placing positions for user
	- Give list of available town placing positions for user
	- update changes to the board placement*/

import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import controller.DatabaseManager;

public class Board {
	
	//all legal (x,y) on hex edges
	Integer[][] conf1 = 
			{{1,4},
			{1,3},
			{2,2},
			{2,3},
			{2,5},
			{2,6},
			{3,1},
			{3,2},
			{3,4},
			{3,5},
			{3,7},
			{3,8},
			{4,1},
			{4,3},
			{4,4},
			{4,6},
			{4,7},
			{4,9},
			{5,2},
			{5,3},
			{5,5},
			{5,6},
			{5,8},
			{5,9},
			{6,2},
			{6,4},
			{6,5},
			{6,7},
			{6,8},
			{6,10},
			{7,3},
			{7,4},
			{7,6},
			{7,7},
			{7,9},
			{7,10},
			{8,3},
			{8,5},
			{8,6},
			{8,8},
			{8,9},
			{8,11},
			{9,4},
			{9,5},
			{9,7},
			{9,8},
			{9,10},
			{9,11},
			{10,6},
			{10,7},
			{10,9},
			{10,10},
			{11,8},
			{11,9}};
	
	//all tile id's tilecoordinates(x,y), fischevalues 
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
	
	//returns a list of all empty street positions
	public ArrayList<Point[]> getAvailableStreetPositions(Integer spelId) throws SQLException {
		ResultSet results = DatabaseManager.getStatement().executeQuery("SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = " + spelId + ";");
		ArrayList<Point[]> resultList = populateStreetXYPairs();
		
		while(results.next()) {
			Point pointA = new Point(results.getInt(0), results.getInt(1));
			Point pointB = new Point(results.getInt(2), results.getInt(3));
			
			for (int i = resultList.size() -1; i >= 0; i--) {
				if((pointA.equals(resultList.get(i)[0]) && pointB.equals(resultList.get(i)[1])) || (pointB.equals(resultList.get(i)[0]) && pointA.equals(resultList.get(i)[1]))) {
					resultList.remove(i);
				} 
			}
		}
		return resultList;
	}
	
	//returns only streetPositions where available and adjacent to users streets
	public ArrayList<Point[]> getPlacableStreePos(Integer spelId, String username) throws SQLException {
		ResultSet results = DatabaseManager.getStatement().executeQuery("SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = " + spelId + " AND username = '" + username + "';");
		ArrayList<Point[]> emptyStreetPos = getAvailableStreetPositions(spelId);
		ArrayList<Point[]> validStreetPos = new ArrayList<Point[]>();
		while(results.next()) {
			Point pointA = new Point(results.getInt(0), results.getInt(1));
			Point pointB = new Point(results.getInt(2), results.getInt(3));
			for (int i = emptyStreetPos.size() -1; i >= 0; i--) {
				if(pointA.equals(emptyStreetPos.get(i)[0]) || pointA.equals(emptyStreetPos.get(i)[1]) || pointB.equals(emptyStreetPos.get(i)[0]) || pointB.equals(emptyStreetPos.get(i)[i])) {
					validStreetPos.add(emptyStreetPos.get(i));
				} 
			}
		}
		return validStreetPos;
	}
	
	private ArrayList<Point[]> populateStreetXYPairs() {
		//for each viable coordinate we're going to if the 3 coordinates to the top right are valid. if they are: add them to list
		ArrayList<Point[]> xyPair = new ArrayList<>();
		for (int j = 0; j < conf1.length; j++) {
			Point current =	new Point(conf1[j][0] +1, conf1[j][1]);
			Point bottomRight = new Point(conf1[j][0] +1, conf1[j][1]);
			Point top = new Point(conf1[j][0], conf1[j][1]+1);
			Point topRight = new Point(conf1[j][0]+1, conf1[j][1]+1);
			
			for (int j2 = 0; j2 < conf1.length; j2++) {
				Point[] tempPair = new Point[2];
				tempPair[0] = current;
				Point compare = new Point(conf1[j2][0], conf1[j2][1]);
				if(bottomRight.equals(compare)) {
					tempPair[1] = bottomRight;
					xyPair.add(tempPair);
				}
				if(top.equals(compare)) {
					tempPair[1] = top;
					xyPair.add(tempPair);
				}
				if(topRight.equals(compare)) {
					tempPair[1] = topRight;
					xyPair.add(tempPair);
				}
			}
		}
		return xyPair;
	}
}