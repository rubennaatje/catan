package model;

import java.sql.ResultSet;

import controller.CatanController;
import controller.DatabaseManager;
import controller.GameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Waiting {


	private int playerCount; 
	private boolean enoughPlayers; 
	private int currentGameId; 
	private PlayerUser player; 
	
	
	private CatanController controller;
	
	public Waiting() {
		accepted();
	}
	
	public void accepted(){
		
	
		//DatabaseManager.connect();
		//ObservableList<Challenges> data = null;

		try {
			ResultSet acceptedPlayers = DatabaseManager.createStatement().executeQuery("select count(speelstatus) from speler where idspel = 781 and speelstatus = 'geaccepteerd'");
			
			  while(acceptedPlayers.next()) {
			       int count = acceptedPlayers.getInt(1);
			     
			       if(count == 3){
			    	   System.out.println("count is 3: " + count);
			    	  //hier het spel starten. 
			    	   
			    	   //new GameController
			    	   // start() functie aanroepen
			       }
			  }

		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	
	
}
