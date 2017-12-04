package controller;

import java.sql.SQLException;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.ChatModel;
import model.PlayerModel;
import view.ChatView;

public class ChatController {
	private ChatModel model;
	private ChatView view;
	private PlayerModel player;
	private String spelID;
	
	public ChatController(PlayerModel player) throws SQLException {
		model = new ChatModel();
		view = new ChatView(model.getChatLines(), this);
	}
	
	public void sendMessage(String message) {
		try {
			model.sendMessage(message, player, spelID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}