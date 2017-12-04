package controller;

import java.sql.SQLException;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ChatModel;
import model.PlayerModel;
import view.ChatView;

public class ChatController implements Runnable {
	private ChatModel model;
	private ChatView view;
	private PlayerModel player;
	private String spelID;

	public ChatController(PlayerModel player, Stage primaryStage, String spelID) throws SQLException {
		model = new ChatModel();
		view = new ChatView(model.getChatLines(), this);
		this.player = player;
		this.spelID = spelID;
		// for testing purpouses only
		Scene scene = new Scene(view);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public void sendMessage(String message) {
		try {
			model.sendMessage(message, player, spelID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				model.updateChatbox(spelID);
				//updates chatbox every second
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}