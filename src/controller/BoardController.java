package controller;

import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.*;
import view.*;

public class BoardController extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
        Location locs = new Location(1000);

		PlayBoardView playboardview = new PlayBoardView(stage);
		
		playboardview.show();
		
        
        
        Board board = new Board();
        
        ArrayList<Tile> tiles = board.getAllHexes(40);
//        board.createBoard(43);        
        for(Tile t : tiles) {
        	System.out.println(t.getLocation().getX());
        	
        	int x = (int)t.getLocation().getX();
        	int y = (int)t.getLocation().getY();
        	 playboardview.addHex(locs.getHexEdges(x,y), locs.getCoordinate(x,y), 0, t.getTileType().getCssClass());
        }

	}

}
