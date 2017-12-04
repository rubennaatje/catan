package model;

import java.sql.Timestamp;
/*responsibility:
	-builds a chatline
	-returns chatline values*/
public class ChatLine {
	String username;
	String message;
	Timestamp timeStamp;
	
	public ChatLine(String username, String message, Timestamp time) {
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
