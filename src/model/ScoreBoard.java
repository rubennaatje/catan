package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import controller.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScoreBoard
{


	public ObservableList<PlayerRank> fillLeaderboard()
	{
		ResultSet scores = null;
		try
		{
			DatabaseManager.connect();
			scores = DatabaseManager.executeSelectQuery(
					"SELECT username, aantal_spellen_gewonnen FROM speelresultaat ORDER BY som_behaalde_punten DESC");
			Integer index = 0;
			ObservableList<PlayerRank> data = FXCollections.observableArrayList();
			while (scores.next())
			{
				index++;
				data.add(new PlayerRank(index.toString(),scores.getString(1), scores.getString(2)));
			}
			return data;
		} catch (SQLException e)
		{
			System.out.println("Scoreboard error : " + e.getMessage());
		}
		return null;
	}

}
