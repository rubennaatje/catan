package controller;

import java.awt.Point;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import view.*;

public class BoardController extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
        LocationGenerator locs = new LocationGenerator(1000);
		PlayBoardView playboardview = new PlayBoardView(stage);
        Board board = new Board();
        String spelId = "40";
        //board.createBoard(40);        
        ArrayList<Tile> tiles = board.getAllHexes(40);
        for(Tile t : tiles) {
        	
        	int x = (int)t.getLocation().getX();
        	int y = (int)t.getLocation().getY();
        	playboardview.addHex(locs.getHexEdges(x,y), locs.getCoordinate(x,y), 0, t.getTileType().getCssClass());
        }
        playboardview.show();
        Player orange = new Player(PlayerType.ORANJE, spelId);
        ArrayList<Street> listOfStreets = board.getValidFirstRoundStreetPos(spelId, orange);
        for(Street street: listOfStreets) {
        	playboardview.addStreet(street, null);
        }
	}

}
