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
	String spelId;
	Integer usrPlayer; // 0..3
	PlayBoardView playboardview;
	Board board;
	EventHandler<? super MouseEvent> pieceEvent;

	public BoardController() {
		players = new Player[4];

		usrPlayer = 1;

		players[0] = new Player(PlayerType.WIT, "bart");
		players[1] = new Player(PlayerType.ORANJE, "rik");
		players[2] = new Player(PlayerType.BLAUW, "lesley");
		players[3] = new Player(PlayerType.ROOD, "ger");

		board = new Board();

		spelId = "770";

		this.pieceEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					if (event.getSource() instanceof PieceView) {
						PieceView caller = (PieceView) event.getSource();
						board.registerPlacement(caller.getPieceModel(), spelId);
					} else if (event.getSource() instanceof StreetView) {
						StreetView caller = (StreetView) event.getSource();
						board.registerPlacement(caller.getStreetModel(), spelId);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			};
		};
	}

	@Override
	public void start(Stage stage) throws Exception {

		playboardview = new PlayBoardView(stage);

		playboardview.show();

		refresh();
		showPiecePlacable();

	}

	public void showPiecePlacable() {
		ArrayList<Piece> listOfPiece;
		try {
			listOfPiece = board.getPlacebleVillagePos(players[usrPlayer], spelId);
			for (Piece piece : listOfPiece) {
				playboardview.addPiece(piece, pieceEvent);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showStreetPlacable() {
		ArrayList<Street> listOfStreet;
		try {
			listOfStreet = board.getPlacableStreePos(players[usrPlayer], spelId);
			for (Street piece : listOfStreet) {
				playboardview.addStreet(piece, pieceEvent);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// updateing field
	public void refresh() {
		// updates all hexes and attached values
		try {
			for (Tile t : board.getAllHexes(spelId)) {
				playboardview.addHex(t);
			}
			for (Player player : players) {
				// places all streets for player
				for (Street street : board.getStreetsPlayer(player, spelId)) {
					playboardview.addStreet(street);
				}
				// places all cities and towns for player
				for (Piece street : board.getPiecesPlayer(player, spelId)) {
					playboardview.addPiece(street);
				}
			}
		} catch (Exception e) {
			// TODO show error to user??? //
			e.printStackTrace();
		}
	}

}
