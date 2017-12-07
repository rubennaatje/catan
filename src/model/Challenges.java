package model;

import java.sql.SQLException;

import controller.DatabaseManager;
import javafx.beans.property.SimpleStringProperty;

public class Challenges {
	 
	private final SimpleStringProperty playerName;
    private final SimpleStringProperty gameId;
    
    public Challenges(String name, String id) {
    	this.playerName = new SimpleStringProperty(name);
    	this.gameId = new SimpleStringProperty(id); 	
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
			DatabaseManager.createStatement().executeUpdate("Update speelstatus SET speelstatus='accepteerd' where idspel = " + getGameId() + "and username = " + getPlayerName() + " ;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void decline() {
		try {
			DatabaseManager.createStatement().executeUpdate("Update speelstatus SET speelstatus='gewijgerd' where idspel = " + getGameId() + "and username = " + getPlayerName() + " ;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
