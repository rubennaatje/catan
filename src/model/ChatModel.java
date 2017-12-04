package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import controller.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ChatLine;

public class ChatModel {

	private static Timestamp lastLine = new Timestamp(System.currentTimeMillis());
	private ObservableList<ChatLine> chatLines = FXCollections.observableArrayList();
	
	public void sendMessage(String message, PlayerModel player, String spelID) throws SQLException {
		System.out.println(message);
		DatabaseManager.createStatement().executeUpdate("insert into chatregel values (" + spelID + ", '" + player.getUsername() + "' , CURRENT_TIMESTAMP, '"+ message +"' )");
		updateChatbox(spelID);
	}
	
	
	public void updateChatbox(String spelID) throws SQLException
	{
		ResultSet rs = DatabaseManager.createStatement().executeQuery("SELECT username, tijdstip, bericht FROM chatregel where idspel = " + spelID + " and tijdstip >= '" + lastLine.toString() +  "'  order by tijdstip asc");
		while(rs.next()) {
			String sChatline = rs.getString("bericht");
			String sUsername = rs.getString("username");
			Timestamp sTime = rs.getTimestamp("tijdstip");
			if(lastLine.compareTo(sTime) > 0 ) lastLine = sTime;
			chatLines.add(new ChatLine(sUsername, sChatline,  sTime));
		}
	}	
	public ObservableList<ChatLine> getChatLines() {
		return chatLines;
	}	
}