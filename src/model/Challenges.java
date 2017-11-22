package model;

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

}
