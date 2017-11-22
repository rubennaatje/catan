package model;

import javafx.beans.property.SimpleStringProperty;

public class Challenges {
	 
	private final SimpleStringProperty playerName;
 //   private final SimpleStringProperty playerId;
    
    public Challenges(String name) {
    	this.playerName = new SimpleStringProperty(name);
    //	this.playerId = new SimpleStringProperty(id); 
    	
    }
    
    
    public void setName(String name)
	{
		this.playerName.set(name);
	}
    

    public String getName()
	{
		return playerName.get();
	}

}
