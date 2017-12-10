package model;

import java.sql.ResultSet;

import controller.CatanController;
import controller.DatabaseManager;
import controller.GameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Waiting {

	private int waitingFor; 
	private CatanController controller;
	private Challenges selected; 

	
	public Waiting(CatanController controller, Challenges selected) {
		this.controller = controller;
		this.selected = selected; 
		accepted();
		waitForPlayers();
	}
	
	public void accepted(){
	
		new Thread(() -> {
			System.out.println("waiting for change in database"); 
				try {
					Thread.sleep(controller.refreshTime);
					ResultSet acceptedPlayers = DatabaseManager.createStatement().executeQuery("select count(speelstatus) from speler where idspel = "+ selected.getGameId() +"  and speelstatus = 'geaccepteerd'");
					
					  while(acceptedPlayers.next()) {
					       int count = acceptedPlayers.getInt(1);
							
		
					       if(count == 3){
					    	   System.out.println("count is 3: " + count);
					    	   controller.startGame(); 
					    	   //hier spel starten
					       }
					  }
				} catch (Exception e) {
					e.printStackTrace(); 
				}
			
			}).start();
		}
	
	public int waitForPlayers() {
	
		try {
			  ResultSet haveToAccept = DatabaseManager.createStatement().executeQuery("select count(speelstatus) from speler where idspel = " + selected.getGameId() + "and speelstatus = 'uitgedaagde'");

			  while(haveToAccept.next()) {
				  waitingFor = haveToAccept.getInt(1);
			  }
			  
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return waitingFor; 
	}
}
