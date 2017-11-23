package model;

//will be used as container/packet for street information
public class Piece {
	private GridLocation pos;
	private PieceType type;
	private Player player;
	
	public Piece(GridLocation pos, PieceType type, Player player) {
		this.pos = pos;
		this.type = type;
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	public GridLocation getPos() {
		return pos;
	}
	public PieceType getType() {
		return type;
	}
}
