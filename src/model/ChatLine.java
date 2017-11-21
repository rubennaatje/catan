package model;

import java.sql.Timestamp;

public class ChatLine {
	String username;
	String message;
	String timeStamp;
	
	public ChatLine(String username, String message) {
		this.username = username;
		this.message = message;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean save() {
		
//		sQuery = "";
		
		return true;
	    
//	    public static void sendChat(int idSpel, String userName, String message, String time) {
//	    	
//	    }
	}
}
