package model;

import java.sql.ResultSet;

import controller.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Waiting {


	private int playerCount; 
	private boolean enoughPlayers; 
	private int currentGameId; 
	private PlayerUser player; 
	
	public Waiting() {
		accepted();
	}
	
	public void accepted(){
		
		//DatabaseManager.connect();
		//select count(idspel) from speler where idspel = 770
		//ObservableList<Challenges> data = null;

		try {
			ResultSet acceptedPlayers = DatabaseManager.createStatement().executeQuery("select count(speelstatus) from speler where idspel = 781 and speelstatus = 'geaccepteerd'");
			
			  while(acceptedPlayers.next()) {
			       int count = acceptedPlayers.getInt(1);
			     
			       if(count == 3){
			    	   System.out.println("count is 3: " + count);
			    	 //ga naar controller
					//controller verwijst naar het spel starten. 
			       }
			  }

		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	
	
}
