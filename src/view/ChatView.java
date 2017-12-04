package view;

import java.sql.SQLException;

import controller.ChatController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.PlayerModel;
import view.javaFXTemplates.PaneTemplate;

public class ChatView extends PaneTemplate {
	@FXML
	TextField tfMessage;
	@FXML
	ListView<String> listView;
	@FXML
	Button sendButton;

	ChatController controller;

	public ChatView(ObservableList<String> list, ChatController controller) throws SQLException {
		super(ChatView.class.getResource("fxml/ChatView.fxml"));
		this.controller = controller;
		listView.setItems(list);
	}

	public void sendMessage(MouseEvent arg) {
		if (!tfMessage.getText().equals("")) {
			controller.sendMessage(tfMessage.getText());
			tfMessage.clear();
		}
	}

}
