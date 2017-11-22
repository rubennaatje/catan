package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseManager {
	
	private final static String sUrl = "jdbc:mysql://databases.aii.avans.nl/";    
    private final static String sDriverName = "com.mysql.jdbc.Driver";
    private static Connection connection;
    private static Statement statement;
    
    public final static String sLogin = "SELECT password FROM account WHERE username = '%s'";
    public final static String sSendChat = "";
    
    public static void connect() {
    	try {
			Class.forName(sDriverName);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
		}
    	
    	try {
    		connection = DriverManager.getConnection(sUrl + "tajlinde_db", "tajlinde", "Vtb1avans");
    		statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Could not open connection");
		}
    }
    
    public static void disconnect() {
    	try {
    		connection.close();
    		statement.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection");
		}
    }
    
    public static Statement getStatement() {
    	return statement;
    }
}