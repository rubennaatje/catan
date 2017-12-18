package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import controller.DatabaseManager;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

public class ChatModel {

	private Timestamp lastLine = new Timestamp(0);
	private ObservableList<Text> chatLines = FXCollections.observableArrayList();
	private ObservableValue<Number> wordWrap;
	
	public ChatModel() {
		chatLines = FXCollections.observableArrayList();		
	}
	
	public void registerWordWrap(final ObservableValue<Number> wordWrap) {
		 this.wordWrap = wordWrap;
	}

	public void sendMessage(String message, PlayerModel player, String spelID) throws SQLException {
		DatabaseManager.createStatement()
				.executeUpdate("insert into chatregel values (" + spelID + ", '" + player.getUsername() + "' , CURRENT_TIMESTAMP, '" + message + "' )");
		updateChatbox(spelID);
	}

	/**retrieves all lines of the chat for the game since the last timestamp
	 * @param spelID
	 */
	public void updateChatbox(String spelID) {
				
		Platform.runLater(() -> {
			try {
				ResultSet rs = DatabaseManager.createStatement()
						.executeQuery("SELECT username, tijdstip, bericht FROM chatregel where idspel = " + spelID
								+ " and tijdstip > '" + lastLine.toString() + "'  order by tijdstip asc");
				while (rs.next()) {
					String sChatline = rs.getString("bericht");
					String sUsername = rs.getString("username");
					Timestamp sTime = rs.getTimestamp("tijdstip");
					if (sTime.compareTo(lastLine) > 0)
						lastLine = sTime;
					Date date = new Date(lastLine.getTime());
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					
					Text text = new Text(sUsername + " at " + sdf.format(date) + ": " + sChatline);
					text.wrappingWidthProperty().bind(wordWrap);
					
					chatLines.add(text);
					

				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		});
	}

	public ObservableList<Text> getChatLines() {
		return chatLines;
	}
}