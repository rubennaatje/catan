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
	}
	
	public void accepted(){
	
		new Thread(() -> {
			System.out.println("waiting for change in database"); 
			
			int count = waitForPlayers();
			
			while (count > 0) {
				try {
					Thread.sleep(CatanController.refreshTime);
				} catch (Exception e) {
					e.printStackTrace(); 
				}
				
				controller.setWaitingOn(count);
				count = waitForPlayers();
			}
			
			Boolean accepted = false;
			
			try {
				  ResultSet result = DatabaseManager.createStatement().executeQuery("select count(speelstatus) from speler where idspel = " + selected.getGameId() + " and speelstatus = 'geweigerd'");

				  while(result.next()) {
					  accepted = result.getInt(1) == 0;
				  }
				  
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			
			if (accepted) {
				controller.startGame(selected.getGameId()); 
			} else {
				controller.openMenuScreen();
			}
		}).start();
	}
	
	public int waitForPlayers() {
	
		try {
			  ResultSet haveToAccept = DatabaseManager.createStatement().executeQuery("select count(speelstatus) from speler where idspel = " + selected.getGameId() + " and speelstatus = 'uitgedaagde'");

			  while(haveToAccept.next()) {
				  waitingFor = haveToAccept.getInt(1);
			  }
			  
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return waitingFor; 
	}
}
