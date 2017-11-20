package model;

import java.sql.Timestamp;

public class ChatLine {
	String username;
	Timestamp time;
	String message;
	
	public ChatLine(String username, Timestamp time, String message) {
		this.username = username;
		this.time = time;
		this.message = message;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getTime() {
		return time.toString();
	}
}
