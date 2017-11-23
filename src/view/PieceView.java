package view;

import javafx.scene.shape.Rectangle;
import model.Piece;

public class PieceView extends Rectangle {
	Piece pieceData;

	public PieceView() {
		
	}

	public Piece getPieceModel() {
		return pieceData;
	}

	public void setPieceData(Piece pieceData) {
		this.pieceData = pieceData;
	}
}
