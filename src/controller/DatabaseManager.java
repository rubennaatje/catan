package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseManager {
	  
	private final static String sUrl = "jdbc:mysql://databases.aii.avans.nl/";    
    private final static String sDriverName = "com.mysql.jdbc.Driver";
    private static Connection connection;
    private static Statement statement;
    
    public static void connect() {
    	try {
			Class.forName(sDriverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	try {
    		connection = DriverManager.getConnection(sUrl + "2017_vsoprj2_catan_abcd", "42IN02VTSOb", "bedrijfsauto");
    		statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			disconnect();
		}));
    }
    
    public static void disconnect() {
    	try {
    		connection.close();
    		statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public static Statement createStatement() throws SQLException {
		return connection.createStatement();
	}
}