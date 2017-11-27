package controller;

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
	GameControlerView buttons;
	Board board;
	EventHandler<? super MouseEvent> pieceEvent;
	EventHandler<MouseEvent> buyEvent;

	private EventHandler<MouseEvent> endTurn;

	public BoardController(String spelId, Player[] players, int usrPlayer) {
		this.players = new Player[4];

		this.usrPlayer = 2;

		board = new Board();

		this.players[0] = new Player(PlayerType.WIT, "bart");
		this.players[1] = new Player(PlayerType.ORANJE, "rik");
		this.players[2] = new Player(PlayerType.BLAUW, "lesley");
		this.players[3] = new Player(PlayerType.ROOD, "ger");

		this.spelId = "770";

		buyEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				refresh();
				Node caller = (Node) arg0.getSource();
				switch (caller.getId()) {
				case "townBtn":
					showTownPlacable();
					break;
				case "streetBtn":
					showStreetPlacable();
					break;
				case "cityBtn":
					showCityPlacable();
					break;
				}
			}
		};

		/*
		 * EventHandler<MouseEvent> streetEvent = new EventHandler<MouseEvent>() {
		 * 
		 * @Override public void handle(MouseEvent arg0) { showStreetPlacable(); } };
		 * 
		 * EventHandler<MouseEvent> cityEvent = new EventHandler<MouseEvent>() {
		 * 
		 * @Override public void handle(MouseEvent arg0) { showStreetPlacable(); } };
		 */


		this.pieceEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				piecePlacement(arg0);
				refresh();
			}
		};

	}

	private void piecePlacement(MouseEvent event) {
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

	@Override
	public void start(Stage stage) throws Exception {
		buttons = new GameControlerView(stage, buyEvent);
		playboardview = new PlayBoardView(stage);
		GameMergeView mergeView = new GameMergeView(playboardview, buttons, stage);
		mergeView.show();

		refresh();

	}

	public void showTownPlacable() {
		ArrayList<Piece> listOfPiece;
		try {
			listOfPiece = board.getPlacableTownPos(players[usrPlayer], spelId);
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

	public void showCityPlacable() {
		ArrayList<Piece> listOfTowns;
		try {
			listOfTowns = board.getPlacableCity(players[usrPlayer], spelId);
			for (Piece piece : listOfTowns) {
				playboardview.addPiece(piece, pieceEvent);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// update ing field
	public void refresh() {
		playboardview.getChildren().clear();
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
			buttons.setLongestRoad(board.getLongestRoad(players[usrPlayer], spelId));
		} catch (Exception e) {
			// TODO show error to user??? //
			e.printStackTrace();
		}
	}

}
