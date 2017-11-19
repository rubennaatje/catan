package view;

import java.awt.Point;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class PlayBoardView extends PaneTemplate {
	

	public PlayBoardView(Stage stageIn) {
		super(PlayBoardView.class.getResource("fxml/playboard.fxml"),stageIn);
	}

	
	public void addHex(Point[] pointsIn, Point location) {
		Polygon poly = new Polygon();
		poly.setFill(Color.RED);
		for (int i = 0; i < pointsIn.length; i++) {
			poly.getPoints().add(pointsIn[i].getX() - location.getX());
			poly.getPoints().add(pointsIn[i].getY() - location.getY());
		}
		poly.setLayoutX(location.getX());
		poly.setLayoutY(location.getY());
		poly.setStroke(Color.BLACK);
		poly.setStroke(Color.BLACK);
		getChildren().add(0, poly);
	}
}
