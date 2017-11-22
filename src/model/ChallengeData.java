package model;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChallengeData {
	
	public ObservableList<Challenges> fillChallenges(){
		
		ResultSet allChallenges = null; 
		
		try {
			DatabaseManager.connect();
			
			allChallenges = DatabaseManager.getStatement().executeQuery("select username, idspel from speler where idspel IN (SELECT idspel from speler where username = 'dummy' and speelstatus = 'uitgedaagde') AND speelstatus = 'uitdager'"); 
			

			
			ObservableList<Challenges> data = FXCollections.observableArrayList(); 
			
			while(allChallenges.next()) {
				data.add(new Challenges(allChallenges.getString(1), allChallenges.getString(2))); 
			}
			return data; 
			
		} catch (SQLException e) {
			System.out.println("Challange error: " + e.getMessage());
		}
		
		return null; 
	}

	
}
