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

	// all legal (x,y) on hex edges
	private final Integer[][] conf1 = { { 1, 4 }, { 1, 3 }, { 2, 2 }, { 2, 3 }, { 2, 5 }, { 2, 6 }, { 3, 1 }, { 3, 2 },
			{ 3, 4 }, { 3, 5 }, { 3, 7 }, { 3, 8 }, { 4, 1 }, { 4, 3 }, { 4, 4 }, { 4, 6 }, { 4, 7 }, { 4, 9 },
			{ 5, 2 }, { 5, 3 }, { 5, 5 }, { 5, 6 }, { 5, 8 }, { 5, 9 }, { 6, 2 }, { 6, 4 }, { 6, 5 }, { 6, 7 },
			{ 6, 8 }, { 6, 10 }, { 7, 3 }, { 7, 4 }, { 7, 6 }, { 7, 7 }, { 7, 9 }, { 7, 10 }, { 8, 3 }, { 8, 5 },
			{ 8, 6 }, { 8, 8 }, { 8, 9 }, { 8, 11 }, { 9, 4 }, { 9, 5 }, { 9, 7 }, { 9, 8 }, { 9, 10 }, { 9, 11 },
			{ 10, 6 }, { 10, 7 }, { 10, 9 }, { 10, 10 }, { 11, 8 }, { 11, 9 } };

	// all tile id's tilecoordinates(x,y), fischevalues
	private final Integer[][] conf = { { 1, 2, 4, 12 }, { 2, 3, 6, 18 }, { 3, 4, 8, 14 }, { 4, 3, 3, 10 },
			{ 5, 4, 5, 16 },

			{ 6, 5, 7, 8 }, { 7, 6, 9, 1 }, { 8, 4, 2, 6 }, { 9, 5, 4, 2 }, { 10, 6, 6, 0 },

			{ 11, 7, 8, 4 }, { 12, 8, 10, 13 }, { 13, 6, 3, 9 }, { 14, 7, 5, 5 }, { 15, 8, 7, 3 },

			{ 16, 9, 9, 15 }, { 17, 8, 4, 17 }, { 18, 9, 6, 7 }, { 19, 10, 8, 11 } };

	public Board() {
	}

	public void createBoard(int gameId) throws Exception {
		if (gameId < 0)
			throw new Exception("Parameter cannot be negative!");
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
			if (i < 9)
				resourceValue = tiles[i].toString();
			if (i == 9) {
				resourceValue = "X";
				chipValue = "NULL";
			}
			if (i > 9)
				resourceValue = tiles[i - 1].toString();
			System.out.println(
					"INSERT INTO tegel (`idspel`, `idtegel`, `x`, `y`, `idgrondstofsoort`, `idgetalfiche`) VALUES ("
							+ gameId + ", " + conf[i][0] + ", " + conf[i][1] + ", " + conf[i][2] + ", '" + resourceValue
							+ "', " + chipValue + ")");
			DatabaseManager.getStatement().executeUpdate(
					"INSERT INTO tegel (`idspel`, `idtegel`, `x`, `y`, `idgrondstofsoort`, `idgetalfiche`) VALUES ("
							+ gameId + ", " + conf[i][0] + ", " + conf[i][1] + ", " + conf[i][2] + ", '" + resourceValue
							+ "', " + chipValue + ")");
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

	// returns all streets for a specific user in Street format
	public ArrayList<Street> getStreetsPlayer(Player player, String spelId) throws SQLException {
		ResultSet results = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = "
						+ spelId + " AND username = '" + player.getUsername() + "';");
		ArrayList<Street> returnStreet = new ArrayList<>();
		while (results.next()) {
			returnStreet.add(new Street(new Point(results.getInt(0), results.getInt(1)),
					new Point(results.getInt(2), results.getInt(3)), player));
		}
		return returnStreet;
	}

	public ArrayList<Piece> getPiecesPlayer(Player player, String spelId) throws SQLException {
		ResultSet results = DatabaseManager.getStatement().executeQuery(
				"SELECT s.x_van, s.y_van, s2.stuksoort FROM spelerstuk s inner join stuk s2 where s2.stuksoort in ('straat','dorp') and s.idspel = "
						+ spelId + " and username= '" + player.getUsername() + "';");
		ArrayList<Piece> returnPiece = new ArrayList<>();
		while (results.next()) {
			PieceType tempType = null;
			if (results.getString(3).equals("dorp")) {
				tempType = PieceType.VILLAGE;
			} else if (results.getString(3).equals("stad")) {
				tempType = PieceType.CITY;
			}
			returnPiece.add(new Piece(new Point(results.getInt(0), results.getInt(1)), tempType, player));
		}
		return returnPiece;
	}

	// returns a list of all empty street positions
	public ArrayList<Point[]> getAvailableStreetPositions(Integer spelId) throws SQLException {
		ResultSet results = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = "
						+ spelId + ";");
		ArrayList<Point[]> resultList = populateStreetXYPairs();

		while (results.next()) {
			Point pointA = new Point(results.getInt(0), results.getInt(1));
			Point pointB = new Point(results.getInt(2), results.getInt(3));

			for (int i = resultList.size() - 1; i >= 0; i--) {
				if ((pointA.equals(resultList.get(i)[0]) && pointB.equals(resultList.get(i)[1]))
						|| (pointB.equals(resultList.get(i)[0]) && pointA.equals(resultList.get(i)[1]))) {
					resultList.remove(i);
				}
			}
		}
		return resultList;
	}

	// returns only streetPositions where available and adjacent to users streets
	public ArrayList<Point[]> getPlacableStreePos(Integer spelId, String username) throws SQLException {
		ResultSet results = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = "
						+ spelId + " AND username = '" + username + "';");
		ArrayList<Point[]> emptyStreetPos = getAvailableStreetPositions(spelId);
		ArrayList<Point[]> validStreetPos = new ArrayList<Point[]>();
		while (results.next()) {
			Point pointA = new Point(results.getInt(0), results.getInt(1));
			Point pointB = new Point(results.getInt(2), results.getInt(3));
			for (int i = emptyStreetPos.size() - 1; i >= 0; i--) {
				if (pointA.equals(emptyStreetPos.get(i)[0]) || pointA.equals(emptyStreetPos.get(i)[1])
						|| pointB.equals(emptyStreetPos.get(i)[0]) || pointB.equals(emptyStreetPos.get(i)[i])) {
					validStreetPos.add(emptyStreetPos.get(i));
				}
			}
		}
		return validStreetPos;
	}

	// returns all possible location for villages on the map during first round
	public ArrayList<Point> getValidFirstRoundVillagePos(String spelId) throws SQLException {
		ArrayList<Point> returnPos = getEmptyPiecePos(spelId);
		return checkDistanceRule(returnPos, spelId);
	}

	// returns all possible location for villages on the map during first round ||
	// any that match
	public ArrayList<Point[]> getValidFirstRoundStreetPos(String spelId, String username) throws SQLException {
		ResultSet userStreetPos = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'dorp') AND x_van IS NOT NULL AND idspel = "
						+ spelId + " AND username = '" + username + "';");
		ArrayList<Point[]> allPossibleStreets = populateStreetXYPairs();
		ArrayList<Point[]> returnStreet = new ArrayList<>();
		while (userStreetPos.next()) {
			Point pointA = new Point(userStreetPos.getInt(0), userStreetPos.getInt(1));
			for (Point[] pos : allPossibleStreets) {
				if (pos[0].equals(pointA) || pos[1].equals(pointA)) {
					returnStreet.add(pos);
				}
			}
		}
		return returnStreet;
	}

	public ArrayList<Point> getPlacebleVillagePos(String spelId, String username) throws SQLException {
		ResultSet userStreetPos = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = "
						+ spelId + " AND username = '" + username + "';");

		ArrayList<Point> returnPos = new ArrayList<>();
		ArrayList<Point> empPiecePos = getEmptyPiecePos(spelId);
		// adding all legal(position by street from user) empty positions to returnPos
		while (userStreetPos.next()) {
			Point pointA = new Point(userStreetPos.getInt(0), userStreetPos.getInt(1));
			Point pointB = new Point(userStreetPos.getInt(2), userStreetPos.getInt(3));
			for (Point point : empPiecePos) {
				if (pointA.equals(point) || pointB.equals(point))
					returnPos.add(point);
			}
		}
		return checkDistanceRule(returnPos, spelId);
	}

	// removes all positions from a arraylist that are within 2 steps of a city or
	// village
	private ArrayList<Point> checkDistanceRule(ArrayList<Point> posToCheck, String spelId) throws SQLException {
		// logic for distance rule| removing all that don't follow
		ResultSet placedPiece = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'stad' or stuksoort = 'dorp') AND idspel = "
						+ spelId + ";");
		while (placedPiece.next()) {
			Point n = new Point(placedPiece.getInt(0), placedPiece.getInt(1) + 1);
			Point ne = new Point(placedPiece.getInt(0) + 1, placedPiece.getInt(1) + 1);
			Point nw = new Point(placedPiece.getInt(0) - 1, placedPiece.getInt(1));
			Point s = new Point(placedPiece.getInt(0), placedPiece.getInt(1) - 1);
			Point sw = new Point(placedPiece.getInt(0) - 1, placedPiece.getInt(1) - 1);
			Point se = new Point(placedPiece.getInt(0) + 1, placedPiece.getInt(1));
			for (int i = posToCheck.size() - 1; i >= 0; i--) {
				Point c = posToCheck.get(i);
				if (n.equals(c) || ne.equals(c) || nw.equals(c) || s.equals(c) || sw.equals(c) || se.equals(c)) {
					posToCheck.remove(i);
				}
			}
		}
		return posToCheck;
	}

	// returns all un-ocupied points on map
	public ArrayList<Point> getEmptyPiecePos(String spelId) throws SQLException {
		ResultSet results = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'stad' or stuksoort = 'dorp') AND idspel = "
						+ spelId + ";");
		ArrayList<Point> outResult = getValidLocations();
		while (results.next()) {
			Point pointA = new Point(results.getInt(0), results.getInt(1));
			for (int i = outResult.size(); i >= 0; i--) {
				if (outResult.get(i).equals(pointA))
					outResult.remove(i);
			}
		}
		return outResult;
	}

	// creates a Point[] arraylist with all possible street locations
	private ArrayList<Point[]> populateStreetXYPairs() {
		// for each viable coordinate we're going to if the 3 coordinates to the top
		// right are valid. if they are: add them to list
		ArrayList<Point[]> xyPair = new ArrayList<>();
		for (int j = 0; j < conf1.length; j++) {
			Point current = new Point(conf1[j][0] + 1, conf1[j][1]);
			Point bottomRight = new Point(conf1[j][0] + 1, conf1[j][1]);
			Point top = new Point(conf1[j][0], conf1[j][1] + 1);
			Point topRight = new Point(conf1[j][0] + 1, conf1[j][1] + 1);

			for (int j2 = 0; j2 < conf1.length; j2++) {
				Point[] tempPair = new Point[2];
				tempPair[0] = current;
				Point compare = new Point(conf1[j2][0], conf1[j2][1]);
				if (bottomRight.equals(compare)) {
					tempPair[1] = bottomRight;
					xyPair.add(tempPair);
				}
				if (top.equals(compare)) {
					tempPair[1] = top;
					xyPair.add(tempPair);
				}
				if (topRight.equals(compare)) {
					tempPair[1] = topRight;
					xyPair.add(tempPair);
				}
			}
		}
		return xyPair;
	}

	private ArrayList<Point> getValidLocations() {
		ArrayList<Point> outResult = new ArrayList<>();
		for (int i = 0; i < conf1.length; i++) {
			outResult.add(new Point(conf1[i][0], conf1[i][1]));
		}
		return outResult;
	}

	public ArrayList<Tile> getAllHexes(int gameId) throws Exception {
		DatabaseManager.connect();

		ResultSet rs = DatabaseManager.getStatement().executeQuery("SELECT * FROM `tegels` where `idspel` =" + gameId);

		  ArrayList<Tile> tiles = new ArrayList<Tile>(); 
		
		while (rs.next()) {

			int x = rs.getInt("x");
			int y = rs.getInt("y");
			
			
			
			System.out.println(rs.getInt("x"));

			TileType resource = TileType.valueOf(rs.getString("grondstof"));	
			
			Tile tile = new Tile(new Point(x,y), resource);
			
			tiles.add(tile);
		}
		
		rs.close();

		DatabaseManager.disconnect();
		
		return tiles;
	}
}