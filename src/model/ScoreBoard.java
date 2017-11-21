package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScoreBoard
{


	public ObservableList<PlayerRank> fillLeaderboard()
	{
		ArrayList<PlayerRank> returnScores = new ArrayList<>();
		ResultSet scores = null;
		try
		{
			DatabaseManager.connect();
			scores = DatabaseManager.executeQuery(
					"SELECT username, aantal_spellen_gewonnen FROM speelresultaat ORDER BY som_behaalde_punten DESC");
			Integer i = 0;
			ObservableList<PlayerRank> data = FXCollections.observableArrayList();
			while (scores.next())
			{
				Integer index = i+1;
				data.add(new PlayerRank(index.toString(),scores.getString(1), scores.getString(2)));
				i++;
			}
			return data;
		} catch (SQLException e)
		{
			System.out.println("Scoreboard error : " + e.getMessage());
		}
		return null;
	}

}
