package view;

import java.awt.Point;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class HexView extends Group {
	
	
	public HexView(Point[] pointsIn, Point location, Integer value, Color fillColor) {
		Polygon hex = new Polygon();
		
		hex.setFill(fillColor);
		for (int i = 0; i < pointsIn.length; i++) {
			hex.getPoints().add(pointsIn[i].getX() - location.getX());
			hex.getPoints().add(pointsIn[i].getY() - location.getY());
		}

		hex.setStroke(Color.BLACK);
		Circle fiche = new Circle();

		fiche.setRadius(15);
		fiche.setFill(Color.WHITE);
		fiche.setStroke(Color.BLACK);
		
		Label val = new Label();
		
		val.setText(value.toString());
		
		setLayoutX(location.getX());
		setLayoutY(location.getY());
		getChildren().addAll(hex, fiche, val);
		
	}
}
