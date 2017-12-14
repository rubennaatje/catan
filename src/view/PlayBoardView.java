package view;

import java.awt.Point;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import model.GridLocation;
import model.LocationGenerator;
import model.Piece;
import model.PieceType;
import model.Street;
import model.Tile;
import view.javaFXTemplates.PaneTemplate;

public class PlayBoardView extends PaneTemplate {
	LocationGenerator locs;

	public PlayBoardView() {
		super(PlayBoardView.class.getResource("fxml/playboard.fxml"));
		
		locs = new LocationGenerator(getPrefHeight(),getPrefWidth());
		
		getStyleClass().add("playboard_background");
		
		
	}


	@Deprecated
	public void addHex(Point[] pointsIn, Point location, int value, String cssSel) {
		HexView poly = new HexView(pointsIn, location, value, cssSel);
		getChildren().add(poly);
	}

	public void addHex(Tile t) {
		HexView poly;
		try {
			poly = new HexView(locs.getHexEdges(t.getLocation()), locs.getCoordinate(t.getLocation()), t.getValue(),
					t.getTileType().getCssClass());
			getChildren().add(poly);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addStreet(Street streetData, EventHandler<? super MouseEvent> event)  {
		StreetView street = addStreet(streetData);
		street.getStyleClass().add("placeholder");
		street.setOnMouseClicked(event);
	}

	public StreetView addStreet(Street streetData)  {
		StreetView street = new StreetView(streetData);
		Point endPoint = null;
		Point startPoint = null;

		startPoint = locs.getCoordinate(streetData.getStartPos());
		endPoint = locs.getCoordinate(streetData.getEndPos());
		double squareSize = Math.sqrt(Math.pow((startPoint.getX() - endPoint.getX()), 2)
				+ Math.pow((startPoint.getY() - endPoint.getY()), 2));
		double squareWidth = squareSize / 5;

		// calculates Rectangle size according to difference between points
		street.getStyleClass().add(streetData.getPlayer().getType().getCSSClass());
		street.getStyleClass().add("player_piece");

		// calculates angle of square according to difference between items
		double angle = (float) Math
				.toDegrees(Math.atan2(startPoint.getY() - endPoint.getY(), startPoint.getX() - endPoint.getX()));
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
		return street;
	}

	public PieceView addPiece(Piece pieceData){
		PieceView piece = new PieceView(pieceData);
		if(pieceData.getType() == PieceType.DORP) {				
			piece.setHeight(20);
			piece.setWidth(20);
		} else {
			piece.setHeight(30);
			piece.setWidth(30);			
		}
		piece.setLayoutX(locs.getCoordinate(pieceData.getPos()).getX() - (piece.getWidth()/2));
		piece.setLayoutY(locs.getCoordinate(pieceData.getPos()).getY() - (piece.getHeight()/2));
		getChildren().add(piece);
		return piece;
	}
	public void addPiece(Piece pieceData, EventHandler<? super MouseEvent> event) {
		PieceView piece = addPiece(pieceData);
		piece.getStyleClass().add("placeholder");
		piece.setOnMouseClicked(event);
	}

	public RobberView addRobber(GridLocation posIn) {
		Point pos = locs.getCoordinate(posIn);
		RobberView robber = new RobberView(posIn);
		robber.setCenterX(pos.getX());
		robber.setCenterY(pos.getY());
		
		getChildren().add(robber);
		return robber;
	}

	public void drawHaven(GridLocation location, GridLocation location2) {
		Polyline line = new Polyline();
		line.getPoints().addAll(new Double[] {
			locs.getCoordinate(location).getX(), locs.getCoordinate(location).getY(), locs.getCoordinate(location2).getX(), locs.getCoordinate(location2).getY()	
		});
		line.setStrokeWidth(0.5);
		getChildren().add(line);
		
	}

	public void addRobber(GridLocation location, EventHandler<MouseEvent> robber) {
		RobberView el = addRobber(location);
		el.getStyleClass().add("placeholder");
		el.setOnMouseClicked(robber);
	}
}
