package view;

import java.awt.Point;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
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

		
		//calculates Rectangle size according to difference between points
		double squareSize = Math
				.sqrt(Math.pow((start.getX() - end.getX()), 2) + Math.pow((start.getY() - end.getY()), 2));
		double squareWidth = squareSize / 5;

		
		//fills in the color
		street.setFill(color);
		street.setStroke(Color.BLACK);
		
		//calculates angle of square according to difference between items
		double angle = (float) Math.toDegrees(Math.atan2(start.getY() - end.getY(), start.getX() - end.getX()));
		angle -= 270;
		if (angle < 0) {
			angle += 360;
		}

		//offsets positioning to align with middle
		street.setLayoutX(start.getX() - (squareWidth / 2));
		
		street.setLayoutY(start.getY());

		//sets size
		street.setHeight(squareSize);
		street.setWidth(squareWidth);
		
		//adds rotation on anchorpoint
		street.getTransforms().add(new Rotate(angle, (squareWidth / 2), 0));
		
		//adds item to "this"
		getChildren().add(street);
	}
	
	public void addPiece(Point posIn, Paint color) {
		Rectangle piece = new Rectangle();
		piece.setLayoutX(posIn.getX()-10);
		piece.setLayoutY(posIn.getY()-10);
		piece.setHeight(20);
		piece.setWidth(20);
		piece.setFill(color);
		piece.setStroke(Color.BLACK);
		getChildren().add(piece);
	}
	
	public void addRobber(Point posIn) {
		Ellipse robber = new Ellipse();
		robber.setCenterX(posIn.getX());
		robber.setCenterY(posIn.getY());;
		robber.setRadiusY(60);
		robber.setRadiusX(20);
		robber.setFill(Color.BLACK);
		robber.setStroke(Color.BLACK);
		getChildren().add(robber);
	}
}
