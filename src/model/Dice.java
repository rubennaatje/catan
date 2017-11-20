package model;

import java.util.Random;

public class Dice {

	private int diceThrow;
	private Random random = new Random();
	
	public Dice() {
		
	}
	
	private void throwDice() {
		diceThrow = random.nextInt(6 + 1);
	}
	
	public int getThrow() {
		return diceThrow;
	}
}
