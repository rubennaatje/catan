package model;

import java.sql.SQLException;
import java.util.Random;
import controller.DatabaseManager;

public class Dice {

	private int [] diceThrow = new int[2];
	private int totalThrow;
	private int spelid;
	private Random random = new Random();
	
	public Dice(int spelid) {
		this.spelid = spelid;
	}
	
	public void throwDice() {
		diceThrow[0] = random.nextInt(6) + 1;
		diceThrow[1] = random.nextInt(6) + 1;
		totalThrow = diceThrow[0] + diceThrow[1];
		try {
			DatabaseManager.getStatement().executeUpdate("UPDATE SPEL SET LAATSTE_WORP = "+ totalThrow + ", gedobbeld = 1 WHERE idspel = "+spelid+";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int[] getThrows() {
		return diceThrow;
	}
	
	public int getTotalthrow() {
		return totalThrow;
	}
	
}
