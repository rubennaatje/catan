package model;

//will be used as container/packet for street information
public class Street {
	private GridLocation startPos;
	private GridLocation endPos;
	private PlayerModel player;

	public Street(GridLocation startPos, GridLocation endPos, PlayerModel player) {
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

	public PlayerModel getPlayer() {
		return player;
	}

	public void setPlayer(PlayerModel user) {
		player = user;
	}

	public boolean equals(Street obj) {
		if (obj.getEndPos().equals(this.getEndPos()) && obj.getStartPos().equals(this.getStartPos())) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Start: " + startPos.toString() + "|| End : " + endPos.toString();
	}

}
