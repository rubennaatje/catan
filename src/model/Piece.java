package model;

//will be used as container/packet for street information
public class Piece {
	private GridLocation pos;
	private PieceType type;
	private PlayerModel player;
	
	public Piece(GridLocation pos, PieceType type, PlayerModel player) {
		this.pos = pos;
		this.type = type;
		this.player = player;
	}
	
	public PlayerModel getPlayer() {
		return player;
	}
	public GridLocation getPos() {
		return pos;
	}
	public PieceType getType() {
		return type;
	}
}
