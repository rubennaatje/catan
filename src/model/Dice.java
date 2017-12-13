package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import controller.DatabaseManager;

public class Dice {

	private int [] diceThrow = new int[2];
	private int totalThrow;
	private String spelid;
	private Random random = new Random();
	
	public Dice(String spelid) {
		this.spelid = spelid;
	}
	
	public void throwDice() {
		diceThrow[0] = random.nextInt(6) + 1;
		diceThrow[1] = random.nextInt(6) + 1;
		totalThrow = diceThrow[0] + diceThrow[1];
		try {
            DatabaseManager.createStatement().executeUpdate("UPDATE SPEL SET LAATSTE_WORP = "+ totalThrow + ", gedobbeld = 1 WHERE idspel = "+ spelid +";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
    public boolean throwDiceIfNotThrown() throws SQLException {
        ResultSet r = DatabaseManager.createStatement().executeQuery("SELECT gedobbeld, laatste_worp FROM spel WHERE idspel = '" + Catan.getGameId() + "';");
        
        r.next();
        
        if(!r.getBoolean(1)) {
            throwDice();
            return true;
        } else {
            this.totalThrow = r.getInt(2);
            return false;
        }
    }
    
    public int getDBThrow() throws SQLException  {
    	ResultSet r = DatabaseManager.createStatement().executeQuery("SELECT  laatste_worp FROM spel WHERE idspel = '" + Catan.getGameId() + "';");
    	r.next();
    	this.totalThrow = r.getInt(1);
    	return r.getInt(1);
    }
	
	public int[] getThrows() {
		return diceThrow;
	}
	
	public int getTotalthrow() {
		return totalThrow;
	}
	
}
