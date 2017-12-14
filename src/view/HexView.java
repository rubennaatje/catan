package view;

import java.awt.Point;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class HexView extends Group {
	
	
	public HexView(Point[] pointsIn, Point location, Integer value, String cssSel) {
		Polygon hex = new Polygon();
		
		hex.getStyleClass().add("hex_tile");
		//hex.getStyleClass().add(cssSel);
		Image im = new Image("view/images/h" + cssSel + ".png",false);
		hex.setFill(new ImagePattern(im));

		for (int i = 0; i < pointsIn.length; i++) {
			hex.getPoints().add(pointsIn[i].getX() - location.getX());
			hex.getPoints().add(pointsIn[i].getY() - location.getY());
		}

		hex.setStroke(Color.BEIGE);
		
		hex.setStrokeWidth(0.5);
		
		Circle fiche = new Circle();
		
		fiche.getStyleClass().add("fische");
		
		fiche.setRadius(15);
		Label val = new Label();
		
		val.setText(value.toString());
		
		setLayoutX(location.getX());
		setLayoutY(location.getY());
		getChildren().addAll(hex, fiche, val);
		
	}
}
