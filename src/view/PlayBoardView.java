package view;

import java.awt.Point;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import model.LocationGenerator;
import model.Street;
import view.javaFXTemplates.PaneTemplate;

public class PlayBoardView extends PaneTemplate {
	LocationGenerator locs;
	public PlayBoardView(Stage stageIn) {
		super(PlayBoardView.class.getResource("fxml/playboard.fxml"), stageIn);
		locs = new LocationGenerator(1000);
		
	}
	
	
	private void updateLocations() {
		locs = new LocationGenerator(Math.round(getHeight()));
	}
	
	public void addHex(Point[] pointsIn, Point location, int value, String cssSel) {
		HexView poly = new HexView(pointsIn, location, value, cssSel);
		getChildren().add(poly);
	}

	public void addStreet(Street streetData, EventHandler<? super MouseEvent> event) {
		Rectangle street = new Rectangle();
		Point endPoint = null;
		Point startPoint = null;
		try {
			startPoint = locs.getCoordinate(streetData.getStartPos().x,  streetData.getStartPos().y);
			endPoint = locs.getCoordinate(streetData.getEndPos().x,  streetData.getEndPos().y);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// calculates Rectangle size according to difference between points
		double squareSize = Math
				.sqrt(Math.pow((startPoint.getX() - endPoint.getX()), 2) + Math.pow((startPoint.getY() - endPoint.getY()), 2));
		double squareWidth = squareSize / 5;

		// adds input CSS selector to item
		street.getStyleClass().add(streetData.getPlayer().getType().getCSSClass());
		street.getStyleClass().add("player_piece");

		// binds fill on mouseover as event
		if (event != null) {
			street.getStyleClass().add("placeholder");
			street.setOnMouseClicked(event);
		}
		

		// calculates angle of square according to difference between items
		double angle = (float) Math.toDegrees(Math.atan2(startPoint.getY() - endPoint.getY(), startPoint.getX() - endPoint.getX()));
		angle -= 270;
		if (angle < 0) {
			angle += 360;
		}

		// offsets positioning to align with middle
		street.setLayoutX(startPoint.getX() - (squareWidth / 2));

		street.setLayoutY(startPoint.getY());

		// sets size
		street.setHeight(squareSize);
		street.setWidth(squareWidth);

		// adds rotation on anchorpoint
		street.getTransforms().add(new Rotate(angle, (squareWidth / 2), 0));

		// adds item to "this"
		getChildren().add(street);
	}

	public void addPiece(Point posIn, String cssSel, EventHandler<? super MouseEvent> event) {
		Rectangle piece = new Rectangle();
		piece.setLayoutX(posIn.getX() - 10);
		piece.setLayoutY(posIn.getY() - 10);
		piece.setHeight(20);
		piece.setWidth(20);
		// adds input CSS selector to item
		piece.getStyleClass().add(cssSel);
		piece.getStyleClass().add("player_piece");

		// binds placeholder selector and event
		if (event != null) {
			piece.getStyleClass().add("placeholder");
			piece.setOnMouseClicked(event);
		}


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
	@Override
	public void show() {
		super.show();
	}
}
