package model;
/*Responsibilities:
	- Create playboard in db
	- Give list of available street placing positions for user
	- Give list of available town placing positions for user
	- update changes to the board placement*/

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import controller.DatabaseManager;

public class Board {

	// all legal (x,y) on hex edges
	private final Integer[][] conf1 = { { 1, 3 }, { 1, 4 }, { 2, 2 }, { 2, 3 }, { 2, 5 }, { 2, 6 }, { 3, 1 }, { 3, 2 },
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
	public ArrayList<Street> getStreetsPlayer(Player player, String spelId) throws Exception {
		ResultSet results = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = "
						+ spelId + " AND username = '" + player.getUsername() + "';");
		ArrayList<Street> returnStreet = new ArrayList<>();
		while (results.next()) {
			returnStreet.add(new Street(new GridLocation(results.getInt(0), results.getInt(1)),
					new GridLocation(results.getInt(2), results.getInt(3)), player));
		}
		results.close();
		return returnStreet;
	}

	public ArrayList<Piece> getPiecesPlayer(Player player, String spelId) throws Exception {
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
			returnPiece.add(new Piece(new GridLocation(results.getInt(0), results.getInt(1)), tempType, player));
		}
		results.close();
		return returnPiece;
	}

	// returns a list of all empty street positions
	public ArrayList<Street> getAvailableStreetPositions(String spelId, Player user) throws Exception {
		ResultSet results = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND idspel = "
						+ spelId + ";");
		ArrayList<Street> resultList = populateStreetXYPairs(user);

		
		while (results.next()) {
			GridLocation GridLocationA = new GridLocation(results.getInt(0), results.getInt(1));
			GridLocation GridLocationB = new GridLocation(results.getInt(2), results.getInt(3));
			for (int i = resultList.size() - 1; i >= 0; i--) {				
				if ((GridLocationA.equals(resultList.get(i).getStartPos()) && GridLocationB.equals(resultList.get(i).getEndPos()))
						|| (GridLocationB.equals(resultList.get(i).getStartPos()) && GridLocationA.equals(resultList.get(i).getEndPos()))) {
					resultList.remove(i);
				} else {
				}
			}
		}
		results.close();
		return resultList;
	}

	// returns only streetPositions where available and adjacent to users streets
	public ArrayList<Street> getPlacableStreePos(String spelId, Player user) throws Exception {
		ResultSet results = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = "
						+ spelId + " AND username = '" + user.getUsername() + "';");
		ArrayList<Street> emptyStreetPos = getAvailableStreetPositions(spelId, user);
		ArrayList<Street> validStreetPos = new ArrayList<>();
		while (results.next()) {
			GridLocation GridLocationA = new GridLocation(results.getInt(0), results.getInt(1));
			GridLocation GridLocationB = new GridLocation(results.getInt(2), results.getInt(3));
			for (int i = emptyStreetPos.size() - 1; i >= 0; i--) {
				if (GridLocationA.equals(emptyStreetPos.get(i).getStartPos()) || GridLocationA.equals(emptyStreetPos.get(i).getEndPos())
						|| GridLocationB.equals(emptyStreetPos.get(i).getStartPos()) || GridLocationB.equals(emptyStreetPos.get(i).getStartPos())) {
					validStreetPos.add(emptyStreetPos.get(i));
				}
			}
		}
		results.close();
		return validStreetPos;
	}

	// returns all possible location for villages on the map during first round
	public ArrayList<Piece> getValidFirstRoundVillagePos(String spelId, Player user) throws Exception {
		ArrayList<GridLocation> returnPos = getEmptyPiecePos(spelId);
		ArrayList<GridLocation> temp = checkDistanceRule(returnPos, spelId);
		ArrayList<Piece> returnPiece = new ArrayList<>();
		for(GridLocation p : temp) {
			returnPiece.add(new Piece(p, PieceType.VILLAGE, user));
		}
		return returnPiece;
	}

	// returns all possible location for villages on the map during first round ||
	// any that match
	public ArrayList<Street> getValidFirstRoundStreetPos(String spelId, Player user) throws Exception {
		ResultSet userStreetPos = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'dorp') AND x_van IS NOT NULL AND idspel = "
						+ spelId + " AND username = '" + user.getUsername() + "';");
		ArrayList<Street> allPossibleStreets = populateStreetXYPairs(user);
		ArrayList<Street> returnStreet = new ArrayList<>();
		while (userStreetPos.next()) {
			GridLocation GridLocationA = new GridLocation(userStreetPos.getInt(0), userStreetPos.getInt(1));
			for (Street pos : allPossibleStreets) {
				if (pos.getStartPos().equals(GridLocationA) || pos.getEndPos().equals(GridLocationA)) {
					returnStreet.add(pos);
				}
			}
		}
		userStreetPos.close();
		return returnStreet;
	}

	public ArrayList<GridLocation> getPlacebleVillagePos(String spelId, String username) throws Exception {
		ResultSet userStreetPos = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = "
						+ spelId + " AND username = '" + username + "';");

		ArrayList<GridLocation> returnPos = new ArrayList<>();
		ArrayList<GridLocation> empPiecePos = getEmptyPiecePos(spelId);
		// adding all legal(position by street from user) empty positions to returnPos
		while (userStreetPos.next()) {
			GridLocation GridLocationA = new GridLocation(userStreetPos.getInt(0), userStreetPos.getInt(1));
			GridLocation GridLocationB = new GridLocation(userStreetPos.getInt(2), userStreetPos.getInt(3));
			for (GridLocation GridLocation : empPiecePos) {
				if (GridLocationA.equals(GridLocation) || GridLocationB.equals(GridLocation))
					returnPos.add(GridLocation);
			}
		}
		userStreetPos.close();
		return checkDistanceRule(returnPos, spelId);
	}

	// removes all positions from a arraylist that are within 2 steps of a city or
	// village
	private ArrayList<GridLocation> checkDistanceRule(ArrayList<GridLocation> posToCheck, String spelId) throws Exception {
		// logic for distance rule| removing all that don't follow
		ResultSet placedPiece = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'stad' or stuksoort = 'dorp') AND idspel = "
						+ spelId + ";");
		while (placedPiece.next()) {
			GridLocation n = new GridLocation(placedPiece.getInt(0), placedPiece.getInt(1) + 1);
			GridLocation ne = new GridLocation(placedPiece.getInt(0) + 1, placedPiece.getInt(1) + 1);
			GridLocation nw = new GridLocation(placedPiece.getInt(0) - 1, placedPiece.getInt(1));
			GridLocation s = new GridLocation(placedPiece.getInt(0), placedPiece.getInt(1) - 1);
			GridLocation sw = new GridLocation(placedPiece.getInt(0) - 1, placedPiece.getInt(1) - 1);
			GridLocation se = new GridLocation(placedPiece.getInt(0) + 1, placedPiece.getInt(1));
			for (int i = posToCheck.size() - 1; i >= 0; i--) {
				GridLocation c = posToCheck.get(i);
				if (n.equals(c) || ne.equals(c) || nw.equals(c) || s.equals(c) || sw.equals(c) || se.equals(c)) {
					posToCheck.remove(i);
				}
			}
		}
		placedPiece.close();
		return posToCheck;
	}

	// returns all un-ocupied GridLocations on map
	public ArrayList<GridLocation> getEmptyPiecePos(String spelId) throws Exception {
		ResultSet results = DatabaseManager.getStatement().executeQuery(
				"SELECT x_van, y_van FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'stad' or stuksoort = 'dorp') AND idspel = "
						+ spelId + ";");
		ArrayList<GridLocation> outResult = getValidLocations();
		while (results.next()) {
			GridLocation GridLocationA = new GridLocation(results.getInt(0), results.getInt(1));
			for (int i = outResult.size(); i >= 0; i--) {
				if (outResult.get(i).equals(GridLocationA))
					outResult.remove(i);
			}
		}
		results.close();
		return outResult;
	}

	// creates a GridLocation[] arraylist with all possible street locations
	public ArrayList<Street> populateStreetXYPairs(Player user) throws Exception {
		ArrayList<Street> xyPair = new ArrayList<>();
		for (int j = 0; j < conf1.length; j++) {
			GridLocation current = new GridLocation(conf1[j][0], conf1[j][1]);
			GridLocation bottomRight = new GridLocation(conf1[j][0] + 1, conf1[j][1]);
			GridLocation top = new GridLocation(conf1[j][0], conf1[j][1] + 1);
			GridLocation topRight = new GridLocation(conf1[j][0] + 1, conf1[j][1] + 1);
			
			for (int j2 = 0; j2 < conf1.length; j2++) {
				GridLocation compare = new GridLocation(conf1[j2][0], conf1[j2][1]);
				if (bottomRight.equals(compare)) {
					xyPair.add(new Street(current, bottomRight, user));
				}
				if (top.equals(compare)) {
					xyPair.add(new Street(current, top, user));
				}
				if (topRight.equals(compare)) {
					xyPair.add(new Street(current, topRight, user));
				}
			}
		}
		return xyPair;
	}

	private ArrayList<GridLocation> getValidLocations() throws Exception {
		ArrayList<GridLocation> outResult = new ArrayList<>();
		for (int i = 0; i < conf1.length; i++) {
			outResult.add(new GridLocation(conf1[i][0], conf1[i][1]));
		}
		return outResult;
	}

	public ArrayList<Tile> getAllHexes(int gameId) throws Exception {
		ResultSet rs = DatabaseManager.getStatement().executeQuery("SELECT * FROM `tegels` where `idspel` =" + gameId);

		ArrayList<Tile> tiles = new ArrayList<Tile>();

		while (rs.next()) {

			int x = rs.getInt("x");
			int y = rs.getInt("y");

			System.out.println(rs.getInt("x"));

			TileType resource = TileType.valueOf(rs.getString("grondstof"));

			Tile tile = new Tile(new GridLocation(x, y), resource, rs.getInt("waarde"));

			tiles.add(tile);
		}

		rs.close();
		return tiles;
	}
}