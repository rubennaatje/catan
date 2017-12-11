package model;

import java.sql.SQLException;

import controller.DatabaseManager;
import javafx.beans.property.SimpleStringProperty;

public class Challenge {
	 
	private final SimpleStringProperty playerName;
    private final SimpleStringProperty gameId;
    private PlayerUser player; 
    
    public Challenge(String name, String id, PlayerUser player) {
    	this.playerName = new SimpleStringProperty(name);
    	this.gameId = new SimpleStringProperty(id);
    	this.player = player; 
    }
    
    public void setName(String name)
	{
		this.playerName.set(name);
	}

    public String getPlayerName() {
		return playerName.get();
	}
    
    public void setGameID(String id)
	{
		this.gameId.set(id);
	}

    public String getGameId()
	{
		return gameId.get();
	}
    
    public void accept() {
    	try {
    		DatabaseManager.createStatement().executeUpdate("Update speler SET speelstatus='geaccepteerd' where idspel = " + gameId.get() + " and username = '" + player.getUsername() + "' ;");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void decline() {
		try {
			DatabaseManager.createStatement().executeUpdate("Update speler SET speelstatus='geweigerd' where idspel = " + gameId.get() + " and username = '" + player.getUsername() + "';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
