import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import model.ChatLine;

public class ChatModel {

	ObservableList<ChatLine> chatLines = FXCollections.observableArrayList();
	
	public void sendMessage(MouseEvent e) throws SQLException {
		String sMessage = tfMessage.getText();
		System.out.println(sMessage);
		DatabaseManager.createStatement().executeUpdate("insert into chatregel values ('20', 'test_G', CURRENT_TIMESTAMP, '"+ sMessage +"' )");
		updateChatbox();
		tfMessage.clear();
	}
	

	public void updateChatbox() throws SQLException
	{
		DatabaseManager.connect();
		ResultSet rs = DatabaseManager.createStatement().executeQuery("SELECT username, tijdstip, bericht FROM chatregel order by tijdstip desc limit 1");
		while(rs.next()) {
			String sChatline = rs.getString("bericht");
			String sUsername = rs.getString("username");
			String sTime = rs.getString("tijdstip");
			chatLines.addAll(sChatline);
		}
		
		
	}	
}