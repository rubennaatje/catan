package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Catan;
import model.Challenges;
import view.ChallengeView;
import view.LeaderBoardView;
import view.LoginView;
import view.MenuView;
import view.RegisterView;
import view.SplashScreenView;

public class CatanController {
	
	private Catan catan;
	private Stage stage;
	
	public CatanController(Stage stage) {
		this.catan = new Catan();
		this.stage = stage;
		
		openSplashScreen();
	}
	
	public Catan getCatan() {
		return catan;
	}
	
	public void openSplashScreen() {
		new SplashScreenView(stage, this).show();
	}
	
	public void openLoginScreen() {
		new LoginView(stage, this).show();
	}
	
	public void openRegisterScreen() {
		new RegisterView(stage, this).show();
	}
	
	public void openMenuScreen() {
		new MenuView(stage, this).show();
	}
	
	public void openLeaderboardScreen() {
		new LeaderBoardView(stage, this).show();
	}
	
	public void openChallengeScreen() {
		new ChallengeView(stage, this).show();
	}
	
	public ObservableList<Challenges> fillChallenges(){
		
		ResultSet allChallenges = null; 
		
		try {
			DatabaseManager.connect();
			
			allChallenges = DatabaseManager.createStatement().executeQuery("select username, idspel from speler where idspel IN (SELECT idspel from speler where username = 'dummy' and speelstatus = 'uitgedaagde') AND speelstatus = 'uitdager'"); 
			

			
			ObservableList<Challenges> data = FXCollections.observableArrayList(); 
			
			while(allChallenges.next()) {
				data.add(new Challenges(allChallenges.getString(1), allChallenges.getString(2))); 
			}
			return data; 
			
		} catch (SQLException e) {
			System.out.println("Challange error: " + e.getMessage());
		}
		
		return null; 
	}
}
