package model;

public enum PieceType {
	CITY,VILLAGE;
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
