package view;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class ChatView extends PaneTemplate {

	@FXML TextField tfMessage;
	
	public ChatView(Stage stage) throws SQLException {

		super(ChatView.class.getResource("fxml/ChatView.fxml"), stage);
		updateChatbox();
	}
	
	public void sendMessage(MouseEvent e) throws SQLException {
		String sMessage = tfMessage.getText();
		System.out.println(sMessage);
		DatabaseManager.getStatement().executeUpdate("insert into chatregel values ('20', 'test_G', CURRENT_TIMESTAMP, '"+ sMessage +"' )");
		tfMessage.clear();
	}
	
	public void updateChatbox() throws SQLException {
		;
		ResultSet rs = DatabaseManager.getStatement().executeQuery("SELECT * FROM chatregel");
		while(rs.next()) {
			tfMessage.appendText(rs.getString("bericht"));
		}
	}
}
