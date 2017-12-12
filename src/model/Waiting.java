package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.CatanController;
import controller.DatabaseManager;
import javafx.application.Platform;

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
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
						if (controller.getPlayer().getUsername().equals(selected.getUitdager())) {
							controller.startGame(selected.getGameId(), true);
						}
						
						controller.startGame(selected.getGameId(), false);
				    }
				});
			} else {
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
						controller.openMenuScreen();
						try {
							DatabaseManager.createStatement().executeQuery("UPDATE speler SET speelstatus = 'afgebroken' WHERE idspel = " + selected.getGameId());
						} catch (SQLException e) {
							
						}
				    }
				});
			}
		}).start();
	}
	
	public int waitForPlayers() {
		try {
			ResultSet declined = DatabaseManager.createStatement().executeQuery("select count(speelstatus) from speler where idspel = " + selected.getGameId() + " and speelstatus = 'geweigerd'");
			declined.next();
			if (declined.getInt(1) <= 0) {
				try {
					  ResultSet result = DatabaseManager.createStatement().executeQuery("select count(speelstatus) from speler where idspel = " + selected.getGameId() + " and speelstatus = 'uitgedaagde'");
		
					  result.next();
					  waitingFor = result.getInt(1);
					  result.close();
					  
				} catch (Exception e) {
					e.printStackTrace(); 
				}
				
				return waitingFor; 
			}
		} catch (SQLException e1) {
			
		}
		
		return -1;
	}
}
