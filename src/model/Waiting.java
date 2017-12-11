package model;

import java.sql.ResultSet;
import controller.CatanController;
import controller.DatabaseManager;

public class Waiting {

	private int waitingFor; 
	private CatanController controller;
	private Challenge selected; 


	public Waiting(CatanController controller, Challenge selected) {
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
		
					       while(count < 4){
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
		new Thread(() -> {
			try {
				Thread.sleep(controller.refreshTime);
				  ResultSet haveToAccept = DatabaseManager.createStatement().executeQuery("select count(speelstatus) from speler where idspel = " + selected.getGameId() + "and speelstatus = 'uitgedaagde'");

				  while(haveToAccept.next()) {
					  waitingFor = haveToAccept.getInt(1);
				  }
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}).start();
		return waitingFor; 
	}
	
}
