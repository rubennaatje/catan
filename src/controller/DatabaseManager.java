package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseManager {
	
	private final static String sUrl = "jdbc:mysql://projcatan.westeurope.cloudapp.azure.com/";    
//	private final static String sUrl = "jdbc:mysql://databases.aii.avans.nl/";    
    private final static String sDriverName = "com.mysql.jdbc.Driver";
    private static Connection connection;
    private static Statement statement;
    
    public static void connect() {
    	try {
			Class.forName(sDriverName);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
		}
    	try {
    		connection = DriverManager.getConnection(sUrl + "catan", "tajlinde", "Vtb1avans");
//    		connection = DriverManager.getConnection(sUrl + "2017_vsoprj2_catan_abcd", "42IN02VTSOb", "bedrijfsauto");
    		statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Could not open connection");
		}
    	
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			disconnect();
			System.out.println("databasemanager shut down");
		}));
    }
    
    public static void disconnect() {
    	try {
    		connection.close();
    		statement.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection");
		}
    }
    
    public static Statement createStatement() throws SQLException {
		return connection.createStatement();
	}
}