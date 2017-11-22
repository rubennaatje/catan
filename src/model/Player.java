package model;

import java.util.Observable;

/*responsibility:
	-updates playerview
	-is observable
	-retrieves significant playerdata for current game
	-4 per game
	-stores amount of resources, turn and such
	*
	*/
public class Player extends Observable{
	PlayerType type;
	String username;
	
	public Player(PlayerType type, String username) {
		this.type = type;
		this.username = username;
	}
	
	public PlayerType getType() {
		return type;
	}
	public String getUsername() {
		return username;
	}
}
