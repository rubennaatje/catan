package view;

import java.awt.Point;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class PlayBoardView extends PaneTemplate {

	public PlayBoardView(Stage stageIn) {
		super(PlayBoardView.class.getResource("fxml/playboard.fxml"), stageIn);
	}

	public void addHex(Point[] pointsIn, Point location, int value, String cssSel) {
		HexView poly = new HexView(pointsIn, location, value, cssSel);
		getChildren().add(poly);
	}

	public void addStreet(Point start, Point end, String cssSel, boolean placeHolder) {
		Rectangle street = new Rectangle();

		// calculates Rectangle size according to difference between points
		double squareSize = Math
				.sqrt(Math.pow((start.getX() - end.getX()), 2) + Math.pow((start.getY() - end.getY()), 2));
		double squareWidth = squareSize / 5;

		// adds input CSS selector to item
		street.getStyleClass().add(cssSel);
		street.getStyleClass().add("player_piece");

		// binds fill on mouseover as event
		if (placeHolder) {
			street.getStyleClass().add("placeholder");
			street.setOnMouseClicked(e -> pieceClick(e));
		}
		

		// calculates angle of square according to difference between items
		double angle = (float) Math.toDegrees(Math.atan2(start.getY() - end.getY(), start.getX() - end.getX()));
		angle -= 270;
		if (angle < 0) {
			angle += 360;
		}

		// offsets positioning to align with middle
		street.setLayoutX(start.getX() - (squareWidth / 2));

		street.setLayoutY(start.getY());

		// sets size
		street.setHeight(squareSize);
		street.setWidth(squareWidth);

		// adds rotation on anchorpoint
		street.getTransforms().add(new Rotate(angle, (squareWidth / 2), 0));

		// adds item to "this"
		getChildren().add(street);
	}

	public void addPiece(Point posIn, String cssSel, boolean placeHolder) {
		Rectangle piece = new Rectangle();
		piece.setLayoutX(posIn.getX() - 10);
		piece.setLayoutY(posIn.getY() - 10);
		piece.setHeight(20);
		piece.setWidth(20);
		// adds input CSS selector to item
		piece.getStyleClass().add(cssSel);
		piece.getStyleClass().add("player_piece");

		// binds fill on mouseover as event
		if (placeHolder) {
			piece.getStyleClass().add("placeholder");
			
			piece.setOnMouseClicked(e -> pieceClick(e));
		}
		;

		getChildren().add(piece);
	}

	public void addRobber(Point posIn) {
		Ellipse robber = new Ellipse();
		robber.setCenterX(posIn.getX());
		robber.setCenterY(posIn.getY());

		robber.setRadiusY(60);
		robber.setRadiusX(20);
		robber.setFill(Color.BLACK);
		robber.setStroke(Color.BLACK);
		getChildren().add(robber);
	}

	public void pieceClick(MouseEvent t) {
		System.out.println("this is a test");
	}
}
