package view;

import javafx.scene.shape.Rectangle;
import model.Piece;

public class PieceView extends Rectangle {
	Piece pieceData;

	public PieceView(Piece pieceData) {
		getStyleClass().add("player_piece");
		this.pieceData = pieceData;
		getStyleClass().add(pieceData.getPlayer().getType().getCSSClass());
	}

	public Piece getPieceModel() {
		return pieceData;
	}
}
