package model;

import java.awt.Point;

//will be used as container/packet for street information
public class Street {
	private GridLocation startPos;
	private GridLocation endPos;
	private Player player;
	
	public Street(GridLocation startPos, GridLocation endPos, Player player) {
		this.startPos = startPos;
		this.endPos = endPos;
		this.player = player;
	}
	public Street(GridLocation startPos, GridLocation endPos) {
		this.startPos = startPos;
		this.endPos = endPos;
	}
	public GridLocation getEndPos() {
		return endPos;
	}
	public GridLocation getStartPos() {
		return startPos;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player user) {
		player = user;
	}
	
}
