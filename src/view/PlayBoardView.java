package view;

import java.awt.Point;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class PlayBoardView extends PaneTemplate {

	public PlayBoardView(Stage stageIn) {
		super(PlayBoardView.class.getResource("fxml/playboard.fxml"), stageIn);
	}

	public void addHex(Point[] pointsIn, Point location, int value, Color color) {
		HexView poly = new HexView(pointsIn, location, value, color);
		getChildren().add(poly);
	}

	public void addStreet(Point start, Point end, Color color) {
		Rectangle street = new Rectangle();

		double squareSize = Math
				.sqrt(Math.pow((start.getX() - end.getX()), 2) + Math.pow((start.getY() - end.getY()), 2));
		double squareWidth = squareSize / 5;

		street.setFill(color);
		street.setStroke(Color.BLACK);
		double angle = (float) Math.toDegrees(Math.atan2(start.getY() - end.getY(), start.getX() - end.getX()));
		angle -= 270;
		if (angle < 0) {
			angle += 360;
		}

		street.setLayoutX(start.getX() - (squareWidth / 2));
		street.setLayoutY(start.getY());

		street.setHeight(squareSize);
		street.setWidth(squareWidth);

		street.getTransforms().add(new Rotate(angle, (squareWidth / 2), 0));
		getChildren().add(street);
	}
}
