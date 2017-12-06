package model;
/*Responsibilities:
	- Create playboard in db
	- Give list of available street placing positions for user
	- Give list of available town placing positions for user
	- update changes to the board placement
	- handle user re
	*/

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import controller.DatabaseManager;

public class BoardHelper {

	// all legal (x,y) on hex edges
	private static final Integer[][] conf1 = { { 1, 3 }, { 1, 4 }, { 2, 2 }, { 2, 3 }, { 2, 5 }, { 2, 6 }, { 3, 1 },
			{ 3, 2 }, { 3, 4 }, { 3, 5 }, { 3, 7 }, { 3, 8 }, { 4, 1 }, { 4, 3 }, { 4, 4 }, { 4, 6 }, { 4, 7 },
			{ 4, 9 }, { 5, 2 }, { 5, 3 }, { 5, 5 }, { 5, 6 }, { 5, 8 }, { 5, 9 }, { 6, 2 }, { 6, 4 }, { 6, 5 },
			{ 6, 7 }, { 6, 8 }, { 6, 10 }, { 7, 3 }, { 7, 4 }, { 7, 6 }, { 7, 7 }, { 7, 9 }, { 7, 10 }, { 8, 3 },
			{ 8, 5 }, { 8, 6 }, { 8, 8 }, { 8, 9 }, { 8, 11 }, { 9, 4 }, { 9, 5 }, { 9, 7 }, { 9, 8 }, { 9, 10 },
			{ 9, 11 }, { 10, 6 }, { 10, 7 }, { 10, 9 }, { 10, 10 }, { 11, 8 }, { 11, 9 } };

	// all tile id's tilecoordinates(x,y), fischevalues
	private static final Integer[][] conf = { { 1, 2, 4, 12 }, { 2, 3, 6, 18 }, { 3, 4, 8, 14 }, { 4, 3, 3, 10 },
			{ 5, 4, 5, 16 },

			{ 6, 5, 7, 8 }, { 7, 6, 9, 1 }, { 8, 4, 2, 6 }, { 9, 5, 4, 2 }, { 10, 6, 6, 0 },

			{ 11, 7, 8, 4 }, { 12, 8, 10, 13 }, { 13, 6, 3, 9 }, { 14, 7, 5, 5 }, { 15, 8, 7, 3 },

			{ 16, 9, 9, 15 }, { 17, 8, 4, 17 }, { 18, 9, 6, 7 }, { 19, 10, 8, 11 } };

	private BoardHelper() {
	}

	public static void createBoard(int gameId) throws Exception {
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
			DatabaseManager.createStatement().executeUpdate(
					"INSERT INTO tegel (`idspel`, `idtegel`, `x`, `y`, `idgrondstofsoort`, `idgetalfiche`) VALUES ("
							+ gameId + ", " + conf[i][0] + ", " + conf[i][1] + ", " + conf[i][2] + ", '" + resourceValue
							+ "', " + chipValue + ")");
			i++;
		}
	}

	private static TileType[] shuffleArray(TileType[] array) {
		Random rnd = new Random();
		for (int i = array.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			TileType a = array[index];
			array[index] = array[i];
			array[i] = a;
		}
		return array;
	}

	/**
	 * check grootste riddermacht !!!!WERKT NOG NEIT!!!!!!
	 * @param player
	 * @param spelId
	 * @return
	 * @throws SQLException
	 */
	public static String getLargestArmy(PlayerModel player, String spelId) throws SQLException {
		
		String sResult = null;
		
		ResultSet result = DatabaseManager.createStatement().executeQuery(
				"SELECT COUNT(o.idontwikkelingskaart) as countKnightCards, s.username FROM spelerontwikkelingskaart s"
				+ " join ontwikkelingskaart o on o.idontwikkelingskaart = s.idontwikkelingskaart"
				+ " where o.naam = 'ridder' and s.gespeeld = '1' AND `idspel` = '"+ spelId +"'"
				+ " GROUP BY s.username"
				+ " order by countKnightCards DESC LIMIT 1");
		
		while(result.next()) {
			int c = result.getInt("countKnightCards");
			if(c > 2) {
				sResult = result.getString("username");
			}
		}
		
		return spelId;
		
	}
	
	// retrieves count for longest road for player and game
	public static int getLongestRoad(PlayerModel player, String spelId) throws SQLException  {

		ArrayList<GridLocation> enemyPieces = getEnemyPieceLocation(player, spelId);
		ArrayList<GridLocation> endStreets = getDeadEndGridlocation(player, spelId);
		int returnVal = 0;
		for (GridLocation street : endStreets) {
			int result = getStreetLength(getStreetsPlayer(player, spelId), street, enemyPieces);

			if (result > returnVal)
				returnVal = result;
		}
		return returnVal;
	}

	public static ArrayList<GridLocation> getTileLocations() {
		ArrayList<GridLocation> positions = new ArrayList<>();
		for (int i = 0; i < conf.length; i++) {
			positions.add(new GridLocation(conf[i][1], conf[i][2])); 
		}
		return positions;
	}
	
	private static ArrayList<GridLocation> getEnemyPieceLocation(PlayerModel player, String spelId) throws SQLException  {
		ResultSet results = DatabaseManager.createStatement().executeQuery(
				"SELECT s.x_van, s.y_van, s2.stuksoort FROM spelerstuk s inner join stuk s2 on s.idstuk = s2.idstuk where s2.stuksoort in ('stad','dorp') and s.idspel = "
						+ spelId + " and not username = '" + player.getUsername() + "' and x_van is not null;");
		ArrayList<GridLocation> returnPiece = new ArrayList<>();
		while (results.next()) {
			returnPiece.add(new GridLocation(results.getInt(1), results.getInt(2)));
		}
		return returnPiece;
	}

	// recursive function to find street length
	public static int getStreetLength(ArrayList<Street> streetsIn, GridLocation start,
			ArrayList<GridLocation> enemyPieces) {
		GridLocation a = null;
		GridLocation b = null;
		for (GridLocation gridLocation : enemyPieces) {
			if (start.equals(gridLocation))
				return 0;
		}
		for (int i = streetsIn.size() - 1; i >= 0; i--) {
			if (streetsIn.get(i).getEndPos().equals(start)) {
				b = streetsIn.get(i).getStartPos();
				streetsIn.remove(streetsIn.get(i));
			} else if (streetsIn.get(i).getStartPos().equals(start)) {

				a = streetsIn.get(i).getEndPos();
				streetsIn.remove(streetsIn.get(i));
			}
		}
		if (a != null && b != null) {

			return Math.max(getStreetLength(streetsIn, a, enemyPieces), getStreetLength(streetsIn, b, enemyPieces)) + 1;
		} else if (a != null) {
			return getStreetLength(streetsIn, a, enemyPieces) + 1;
		} else if (b != null) {
			return getStreetLength(streetsIn, b, enemyPieces) + 1;

		} else {
			return 0;
		}
	}

	// returns all streets on a deadEnd for a player
	public static ArrayList<GridLocation> getDeadEndGridlocation(PlayerModel player, String spelId) throws SQLException  {
		ArrayList<Street> playerStreet = getStreetsPlayer(player, spelId);
		ArrayList<GridLocation> endLocations = new ArrayList<>();
		for (Street street : playerStreet) {
			boolean checkB = false;
			boolean checkF = false;
			for (Street street1 : playerStreet) {
				if (!street1.equals(street)) {
					if (street.getStartPos().equals(street1.getStartPos())
							|| street.getStartPos().equals(street1.getEndPos())) {
						checkF = true;
					}
					if (street.getEndPos().equals(street1.getStartPos())
							|| street.getEndPos().equals(street1.getEndPos())) {
						checkB = true;
					}
				}
			}
			if (checkB ^ checkF) {
				if (checkF)
					endLocations.add(street.getEndPos());
				if (checkB)
					endLocations.add(street.getStartPos());
			}
		}
		return endLocations;
	}

	// returns all streets for a specific user in Street format
	public static ArrayList<Street> getStreetsPlayer(PlayerModel player, String spelId) throws SQLException  {
		ResultSet results = DatabaseManager.createStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = "
						+ spelId + " AND username = '" + player.getUsername() + "';");
		ArrayList<Street> returnStreet = new ArrayList<>();
		while (results.next()) {
			returnStreet.add(new Street(new GridLocation(results.getInt(1), results.getInt(2)),
					new GridLocation(results.getInt(3), results.getInt(4)), player));
		}
		results.close();
		return returnStreet;
	}

	public static ArrayList<Piece> getPiecesPlayer(PlayerModel player, String spelId) throws SQLException  {
		ResultSet results = DatabaseManager.createStatement().executeQuery(
				"SELECT s.x_van, s.y_van, s2.stuksoort FROM spelerstuk s inner join stuk s2 on s.idstuk = s2.idstuk where s2.stuksoort in ('stad','dorp') and s.idspel = "
						+ spelId + " and username= '" + player.getUsername() + "' and x_van is not null;");
		ArrayList<Piece> returnPiece = new ArrayList<>();
		while (results.next()) {
			PieceType tempType = null;
			if (results.getString(3).equals("dorp")) {
				tempType = PieceType.DORP;
			} else if (results.getString(3).equals("stad")) {
				tempType = PieceType.STAD;
			}
			returnPiece.add(new Piece(new GridLocation(results.getInt(1), results.getInt(2)), tempType, player));
		}
		results.close();
		return returnPiece;
	}

	// returns a list of all empty street positions
	public static ArrayList<Street> getAvailableStreetPositions(PlayerModel user, String spelId) throws SQLException{
		ResultSet results = DatabaseManager.createStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND idspel = "
						+ spelId + ";");
		ArrayList<Street> resultList = populateStreetXYPairs(user);

		while (results.next()) {
			GridLocation a = new GridLocation(results.getInt(1), results.getInt(2));
			GridLocation b = new GridLocation(results.getInt(3), results.getInt(4));
			for (int i = resultList.size() - 1; i >= 0; i--) {
				if ((a.equals(resultList.get(i).getStartPos()) && b.equals(resultList.get(i).getEndPos()))
						|| (b.equals(resultList.get(i).getStartPos()) && a.equals(resultList.get(i).getEndPos()))) {
					resultList.remove(i);
				} else {
				}
			}
		}
		results.close();
		return resultList;
	}

	// returns only streetPositions where available and adjacent to users streets
	public static ArrayList<Street> getPlacableStreePos(PlayerModel user, String spelId) throws SQLException{
		ResultSet playersStrt = DatabaseManager.createStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = "
						+ spelId + " AND username = '" + user.getUsername() + "';");

		ArrayList<Street> emptyStreetPos = getAvailableStreetPositions(user, spelId);
		ArrayList<Street> validStreetPos = new ArrayList<>();
		while (playersStrt.next()) {
			GridLocation a = new GridLocation(playersStrt.getInt(1), playersStrt.getInt(2));
			GridLocation b = new GridLocation(playersStrt.getInt(3), playersStrt.getInt(4));
			for (int i = emptyStreetPos.size() - 1; i >= 0; i--) {
				if (a.equals(emptyStreetPos.get(i).getStartPos()) || a.equals(emptyStreetPos.get(i).getEndPos())
						|| b.equals(emptyStreetPos.get(i).getStartPos())
						|| b.equals(emptyStreetPos.get(i).getEndPos())) {
					validStreetPos.add(emptyStreetPos.get(i));
				}
			}
		}
		playersStrt.close();
		return validStreetPos;
	}

	// returns all possible location for villages on the map during first round
	public static ArrayList<Piece> getValidFirstRoundTownPos(PlayerModel user, String spelId) throws SQLException{
		ArrayList<GridLocation> returnPos = getEmptyPiecePos(spelId);
		ArrayList<GridLocation> temp = checkDistanceRule(returnPos, spelId);
		ArrayList<Piece> returnPiece = new ArrayList<>();
		for (GridLocation p : temp) {
			returnPiece.add(new Piece(p, PieceType.DORP, user));

		}
		return returnPiece;
	}

	// returns all possible location for villages on the map during first round ||
	// any that match
	public static ArrayList<Street> getValidFirstRoundStreetPos(PlayerModel user, String spelId) throws SQLException {
		ResultSet userStreetPos = DatabaseManager.createStatement().executeQuery(
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

	public static ArrayList<Piece> getPlacebleTownPos(PlayerModel user, String spelId) throws SQLException  {

		ResultSet userStreetPos = DatabaseManager.createStatement().executeQuery(
				"SELECT x_van, y_van, x_naar, y_naar FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'straat') AND x_van IS NOT NULL AND idspel = "
						+ spelId + " AND username = '" + user.getUsername() + "';");

		ArrayList<GridLocation> returnPos = new ArrayList<>();
		ArrayList<GridLocation> empPiecePos = getEmptyPiecePos(spelId);
		// adding all legal(position by street from user) empty positions to returnPos
		while (userStreetPos.next()) {
			GridLocation GridLocationA = new GridLocation(userStreetPos.getInt(1), userStreetPos.getInt(2));
			GridLocation GridLocationB = new GridLocation(userStreetPos.getInt(3), userStreetPos.getInt(4));

			for (GridLocation GridLocation : empPiecePos) {
				if (GridLocationA.equals(GridLocation) || GridLocationB.equals(GridLocation))
					returnPos.add(GridLocation);
			}
		}
		userStreetPos.close();
		ArrayList<GridLocation> temp = checkDistanceRule(returnPos, spelId);
		ArrayList<Piece> returnPiece = new ArrayList<>();
		for (GridLocation p : temp) {
			returnPiece.add(new Piece(p, PieceType.DORP, user));
		}
		return returnPiece;
	}

	// removes all positions from a arraylist that are within 2 steps of a city or
	// village
	private static ArrayList<GridLocation> checkDistanceRule(ArrayList<GridLocation> posToCheck, String spelId) throws SQLException
			 {
		// logic for distance rule| removing all that don't follow
		ResultSet placedPiece = DatabaseManager.createStatement().executeQuery(
				"SELECT x_van, y_van FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'stad' or stuksoort = 'dorp') AND x_van is not null AND idspel = "
						+ spelId + ";");

		while (placedPiece.next()) {
			GridLocation n = new GridLocation(placedPiece.getInt(1), placedPiece.getInt(2) + 1);
			GridLocation ne = new GridLocation(placedPiece.getInt(1) + 1, placedPiece.getInt(2) + 1);
			GridLocation nw = new GridLocation(placedPiece.getInt(1) - 1, placedPiece.getInt(2));
			GridLocation s = new GridLocation(placedPiece.getInt(1), placedPiece.getInt(2) - 1);
			GridLocation sw = new GridLocation(placedPiece.getInt(1) - 1, placedPiece.getInt(2) - 1);
			GridLocation se = new GridLocation(placedPiece.getInt(1) + 1, placedPiece.getInt(2));
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
	public static ArrayList<GridLocation> getEmptyPiecePos(String spelId) throws SQLException {
		ResultSet results = DatabaseManager.createStatement().executeQuery(
				"SELECT x_van, y_van FROM spelerstuk WHERE idstuk IN (SELECT idstuk FROM stuk WHERE stuksoort = 'stad' or stuksoort = 'dorp') and x_van is not null AND idspel = "
						+ spelId + ";");

		ArrayList<GridLocation> outResult = getValidLocations();
		while (results.next()) {
			GridLocation GridLocationA = new GridLocation(results.getInt(1), results.getInt(2));
			for (int i = outResult.size() - 1; i >= 0; i--) {
				if (outResult.get(i).equals(GridLocationA))
					outResult.remove(i);
			}
		}
		results.close();
		return outResult;
	}

	// creates a GridLocation[] arraylist with all possible street locations
	public static ArrayList<Street> populateStreetXYPairs(PlayerModel user)  {
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

	private static ArrayList<GridLocation> getValidLocations() {
		ArrayList<GridLocation> outResult = new ArrayList<>();
		for (int i = 0; i < conf1.length; i++) {
			outResult.add(new GridLocation(conf1[i][0], conf1[i][1]));
		}
		return outResult;
	}

	public static ArrayList<Tile> getAllHexes(String spelId) throws SQLException  {

		ResultSet rs = DatabaseManager.createStatement()
				.executeQuery("SELECT * FROM `tegels` where `idspel` =" + spelId);

		ArrayList<Tile> tiles = new ArrayList<Tile>();

		while (rs.next()) {

			int x = rs.getInt("x");
			int y = rs.getInt("y");

			TileType resource = TileType.valueOf(rs.getString("grondstof"));

			Tile tile = new Tile(new GridLocation(x, y), resource, rs.getInt("waarde"));
			tiles.add(tile);
		}

		rs.close();
		return tiles;
	}

	// for pieces
	public static void registerPlacement(Piece pieceModel, String idSpel) throws SQLException {
		DatabaseManager.createStatement().executeUpdate("UPDATE spelerstuk SET x_van = " + pieceModel.getPos().x
				+ ", y_van = " + pieceModel.getPos().y
				+ "	WHERE idstuk = (select one from (SELECT MIN(s2.idstuk) as one FROM stuk s1 LEFT JOIN spelerstuk s2 ON s1.idstuk = s2.idstuk WHERE s2.idspel = "
				+ idSpel + " AND x_van IS NULL AND s1.stuksoort = '" + pieceModel.getType().toString()
				+ "' AND username = '" + pieceModel.getPlayer().getUsername() + "')as a) and idspel = " + idSpel
				+ " and username = '" + pieceModel.getPlayer().getUsername() + "'");

	}

	// for street
	public static void registerPlacement(Street streetModel, String idSpel) throws SQLException {
		DatabaseManager.createStatement().executeUpdate("UPDATE spelerstuk SET x_van = " + streetModel.getStartPos().x
				+ ", y_van = " + streetModel.getStartPos().y + ", x_naar = " + streetModel.getEndPos().x + ", y_naar = "
				+ streetModel.getEndPos().y
				+ "	WHERE idstuk = (select one from (SELECT MIN(s2.idstuk) as one FROM stuk s1 LEFT JOIN spelerstuk s2 ON s1.idstuk = s2.idstuk WHERE s2.idspel = "
				+ idSpel + " AND x_van IS NULL AND s1.stuksoort = 'straat" + "' AND username = '"
				+ streetModel.getPlayer().getUsername() + "')as a) and idspel = " + idSpel + " and username = '"
				+ streetModel.getPlayer().getUsername() + "'");

	}

	public static ArrayList<Piece> getPlacableCity(PlayerModel player, String spelId) throws SQLException {
		ResultSet results = DatabaseManager.createStatement().executeQuery(
				"SELECT s.x_van, s.y_van, s2.stuksoort FROM spelerstuk s inner join stuk s2 on s.idstuk = s2.idstuk where s2.stuksoort in ('dorp') and s.idspel = "
						+ spelId + " and username= '" + player.getUsername() + "' and x_van is not null;");
		ArrayList<Piece> returnPiece = new ArrayList<>();
		while (results.next()) {
			returnPiece.add(new Piece(new GridLocation(results.getInt(1), results.getInt(2)), PieceType.STAD, player));
		}
		results.close();
		return returnPiece;

	}

	public static void giveResources(String spelId, int nThrow) throws Exception {
		HashMap<String, ArrayList<Piece>> resourceKindPieces = new HashMap<String, ArrayList<Piece>>();

		for (TileType type : TileType.values()) {
			resourceKindPieces.put(type.toString(), new ArrayList<Piece>());
		}

		for (Tile t : getAllHexes(spelId)) {
			if (t.getValue() == nThrow) {
				resourceKindPieces.get(t.getTileType().toString())
						.addAll(getSurroundingPieces(spelId, t.getLocation().x, t.getLocation().y));

			}
		}

		for (Entry<String, ArrayList<Piece>> entry : resourceKindPieces.entrySet()) {
			String key = entry.getKey();
			ArrayList<Piece> value = entry.getValue();

			for (Piece p : value) {
				p.getPlayer().addResource(key, p);
			}
		}

	}

	public static ArrayList<Piece> getSurroundingPieces(String spelId, int x, int y) throws SQLException  {

		ResultSet results = DatabaseManager.createStatement()
				.executeQuery("SELECT * FROM spelerstuk s INNER JOIN stuk s2 ON s.idstuk = s2.idstuk WHERE idspel = '"
						+ spelId + "' AND s2.stuksoort IN ('dorp' , 'stad') AND ((s.x_van - " + x
						+ ") <= 1 AND (s.x_van - " + x + ") >= - 1) AND ((s.y_van - " + y + ") <= 1 AND (s.y_van - " + y
						+ ") >= - 1);  ");
		ArrayList<Piece> returnPiece = new ArrayList<>();
		while (results.next()) {
			// System.out.println(results.getInt("x_van") + " " + results.getInt("y_van") +
			// " " + results.getString("username") + " " + results.getString("stuksoort"));
			returnPiece.add(new Piece(new GridLocation(results.getInt("x_van"), results.getInt("Y_van")),
					PieceType.valueOf(results.getString("stuksoort").toUpperCase()),
					new PlayerModel(results.getString("username"), spelId)));
		}
		results.close();
		return returnPiece;
	}

	public static void nextTurnForward(String spelId) throws SQLException {
		ResultSet results = DatabaseManager.createStatement().executeQuery(
				"select volgnr from speler where username = (select beurt_username from spel where idspel = speler.idspel) and idspel = "
						+ spelId);
		results.next();
		int volgnr = results.getInt(1);
		
		
		if (volgnr == 4) {
			volgnr = 1;
		} else {
			volgnr++;
		}
		DatabaseManager.createStatement().executeUpdate("update spel set beurt_username= (select username from speler where idspel = spel.idspel and volgnr = " + volgnr + ") where spel.idspel = " + spelId);
		DatabaseManager.createStatement().executeUpdate("UPDATE speler set shouldrefresh = 1 where volgnr = " + volgnr + " and idspel = " + spelId);
	}

	public static void nextTurnBackward(String spelId) throws SQLException {
		ResultSet results = DatabaseManager.createStatement().executeQuery(
				"select volgnr from speler where username = (select beurt_username from spel where idspel = speler.idspel) and idspel = "
						+ spelId);
		results.next();
		int volgnr = results.getInt(1);
		if (volgnr == 1) {
			volgnr = 4;
		} else {
			volgnr--;
		}
		DatabaseManager.createStatement().executeUpdate("update spel set beurt_username= (select username from speler where idspel = spel.idspel and volgnr = " + volgnr + ") where spel.idspel = " + spelId);
		DatabaseManager.createStatement().executeUpdate("UPDATE speler set shouldrefresh = 1 where volgnr = " + volgnr + " and idspel = " + spelId);
	}
	
	public void placeRobber(String spelId, GridLocation loc) throws SQLException {		
		DatabaseManager.createStatement().executeUpdate("UPDATE struikrover SET idtegel = (SELECT idtegel from tegel where x = " + loc.x + " and y= " + loc.y + " and idspel = struikrover.idspel) where idspel = " + spelId);
	}
}