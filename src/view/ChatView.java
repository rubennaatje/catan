package view;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import view.javaFXTemplates.PaneTemplate;

public class ChatView extends PaneTemplate {
	@FXML TextField tfMessage;
	@FXML ListView<String> listView;
	
	@FXML Button sendButton;
	
	public ChatView(EventHandler<MouseEvent> sendEvent, ObservableList<?> list) throws SQLException {
		super(ChatView.class.getResource("fxml/ChatView.fxml"));
		sendButton.setOnMouseClicked(sendEvent);
	}
	 
}
