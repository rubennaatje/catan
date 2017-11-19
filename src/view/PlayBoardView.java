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

	
	public void addHex(Point[] pointsIn, Point location, int value) {
		HexView poly = new HexView(pointsIn, location, value);
		getChildren().add(0, poly);
	}
}
