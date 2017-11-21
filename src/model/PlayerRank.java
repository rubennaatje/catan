package model;

import javafx.beans.property.SimpleStringProperty;

public class PlayerRank
{
	 private final SimpleStringProperty rank;
     private final SimpleStringProperty name;
     private final SimpleStringProperty gamesWon;

     public PlayerRank(String rank, String name, String gamesWon) {
         this.rank = new SimpleStringProperty(rank);
         this.name = new SimpleStringProperty(name);
         this.gamesWon = new SimpleStringProperty(gamesWon);
     }

	public String getRank()
	{
		return rank.get();
	}
	public void setRank(String rank)
	{
		this.rank.set(rank);
	}

	public String getName()
	{
		return name.get();
	}
	public void setName(String name)
	{
		this.name.set(name);
	}

	public String getGamesWon()
	{
		return gamesWon.get();
	}
	public void setGamesWon(String gamesWon)
	{
		this.gamesWon.set(gamesWon);
	}
     
}
