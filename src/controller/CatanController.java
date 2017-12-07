package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Catan;
import model.Challenges;
import model.Waiting;
import view.ChallengeView;
import view.LeaderBoardView;
import view.LoginView;
import view.MenuView;
import view.RegisterView;
import view.SplashScreenView;
import view.WaitingView;

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
		//System.out.println(challengeView.getId()); 
	}
	
	public void openWaitingScreen() {
		new WaitingView(stage, this).show(); 
		Waiting waitModel = new Waiting(); 
	}
	
	public ObservableList<Challenges> fillChallenges(){
		
		ObservableList<Challenges> data = null;
		
		try {
			ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT username, idspel FROM speler WHERE idspel IN (SELECT idspel from speler where username = 'bart' AND speelstatus = 'uitgedaagde') AND speelstatus = 'uitdager';");
			data = FXCollections.observableArrayList();
			
			while (result.next()) {
				data.add(new Challenges(result.getString(1), result.getString(2)));
			}
		} catch (Exception e) {
			
		}
		
		return data; 
	}
}
