package model;

import java.sql.SQLException;

import controller.DatabaseManager;
import javafx.beans.property.SimpleStringProperty;

public class Challenge {
	 
	private final SimpleStringProperty uitdager;
    private final SimpleStringProperty gameId;
    private final PlayerUser user;
    
    public Challenge(String name, String id, PlayerUser user) {
    	this.uitdager = new SimpleStringProperty(name);
    	this.gameId = new SimpleStringProperty(id);
    	this.user = user;
    }
    
    public void setUitdager(String name)
	{
		this.uitdager.set(name);
	}

    public String getUitdager() {
		return uitdager.get();
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
    		DatabaseManager.createStatement().executeUpdate("Update speler SET speelstatus='geaccepteerd' where idspel = " + gameId.get() + " and username = '" + user.getUsername() + "';");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void decline() {
		try {
			DatabaseManager.createStatement().executeUpdate("Update speler SET speelstatus='geweigerd' where idspel = " + gameId.get() + " and username = '" + user.getUsername() + "';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
