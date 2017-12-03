package tests;

import java.sql.SQLException;

import controller.DatabaseManager;
import model.BoardHelper;

public class TestBoardLogic {
	public static void main(String[] args) throws SQLException {
		DatabaseManager.connect();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DatabaseManager.disconnect();
			System.out.println("databasemanager shut down");
		}));
		
		BoardHelper.nextTurnBackward("770");
	}
}
