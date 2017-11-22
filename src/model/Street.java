package model;

import java.awt.Point;

//will be used as container/packet for street information
public class Street {
	private Point startPos;
	private Point endPos;
	private Player player;
	
	public Street(Point startPos, Point endPos, Player player) {
		this.startPos = startPos;
		this.endPos = endPos;
		this.player = player;
	}
	public Point getEndPos() {
		return endPos;
	}
	public Point getStartPos() {
		return startPos;
	}
	public Player getPlayer() {
		return player;
	}
	
}
