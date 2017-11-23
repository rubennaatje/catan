package controller;

import java.awt.Point;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import view.*;

public class BoardController extends Application {

	Player[] players;
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		LocationGenerator locs = new LocationGenerator(1000);
		PlayBoardView playboardview = new PlayBoardView(stage);
		Board board = new Board();
		String spelId = "770";
		// board.createBoard(40);
		ArrayList<Tile> tiles = board.getAllHexes(40);
		for (Tile t : tiles) {
			playboardview.addHex(t);
		}

		EventHandler<? super MouseEvent> event = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				PieceView caller = (PieceView)event.getSource();
				try {
					board.registerPlacement(caller.getPieceModel(), "770");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
				System.out.println(caller.getPieceModel().getPos().toString());
			};
		};
		playboardview.show();
		
		
		Player white = new Player(PlayerType.WIT, "bart");
		Player orange = new Player(PlayerType.ORANJE, "rik");
		Player blue = new Player(PlayerType.BLAUW, "lesley");
		Player red = new Player(PlayerType.ROOD, "ger");
		
		
		 ArrayList<Street> listOfStr = board.getStreetsPlayer(white, spelId);
		for (Street street : listOfStr) {
			playboardview.addStreet(street);
		}
		listOfStr = board.getStreetsPlayer(orange, spelId);
		for (Street street : listOfStr) {
			playboardview.addStreet(street);
		}
		listOfStr = board.getStreetsPlayer(blue, spelId);
		for (Street street : listOfStr) {
			playboardview.addStreet(street);
		}
		listOfStr = board.getStreetsPlayer(red, spelId);
		for (Street street : listOfStr) {
			playboardview.addStreet(street);
		}
		listOfStr = board.getPlacableStreePos(orange, spelId);
		for (Street street : listOfStr) {
			playboardview.addStreet(street, event);
		}
		
/*		ArrayList<Street> listOfStr = board.populateStreetXYPairs(orange);
		for (Street street : listOfStr) {
			playboardview.addStreet(street, event);
		}*/
		ArrayList<Piece> listOfStreets = board.getPiecesPlayer(white, spelId);
		for (Piece street : listOfStreets) {
			playboardview.addPiece(street);
		}
		listOfStreets = board.getPiecesPlayer(orange, spelId);
		for (Piece street : listOfStreets) {
			playboardview.addPiece(street);
		}
		listOfStreets = board.getPiecesPlayer(blue, spelId);
		for (Piece street : listOfStreets) {
			playboardview.addPiece(street);
		}
		listOfStreets = board.getPiecesPlayer(red, spelId);
		for (Piece street : listOfStreets) {
			playboardview.addPiece(street);
		}
		
	}

}
