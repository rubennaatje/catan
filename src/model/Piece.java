package model;

import java.awt.Point;

//will be used as container/packet for street information
public class Piece {
	private Point pos;
	private PieceType type;
	private Player player;
	
	public Piece(Point pos, PieceType type, Player player) {
		this.pos = pos;
		this.type = type;
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	public Point getPos() {
		return pos;
	}
	public PieceType getType() {
		return type;
	}
}
