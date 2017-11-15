package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatabaseManager;

public class Catan {
	
	public boolean login(String username, String password) {
		try {
			ResultSet result = DatabaseManager.getStatement().executeQuery(String.format(DatabaseManager.sLogin, username));
		if (result != null && result.next()) {
			if (result.getString("password") == password) {
				return true;
			}
		}
		
		} catch (SQLException e) {

		}
		
		return false;
	}
	
	public boolean register(String username, String password) {
		try {
			return DatabaseManager.getStatement().execute(String.format(DatabaseManager.sLogin, username, password));
		} catch (SQLException e) {

		}
		
		return false;
	}

}
