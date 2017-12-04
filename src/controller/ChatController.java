package controller;

import java.sql.SQLException;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.ChatModel;
import view.ChatView;

public class ChatController {
	ChatModel model;
	ChatView view;
	EventHandler<MouseEvent> sendEvent;
	
	
	public ChatController() throws SQLException {
		model = new ChatModel();
		view = new ChatView();
		
		sendEvent = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				
			}
		};
	}
	private void sendMessage() {
		
	}
	
}