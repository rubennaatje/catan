package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Catan;
import model.Challenges;
import model.PlayerUser;
import model.Waiting;
import view.ChallengerView;
import view.ChallengesView;
import view.LeaderBoardView;
import view.LoginView;
import view.MenuView;
import view.RegisterView;
import view.SplashScreenView;
import view.WaitingView;

public class CatanController {
	
	private Catan catan;
	private Stage stage;
	private PlayerUser player;
	
	public CatanController(Stage stage) {
		this.catan = new Catan();
		this.stage = stage;
		
		openSplashScreen();
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
		new ChallengesView(stage, this).show();
	}
	

	public void openWaitingScreen() {
		new WaitingView(stage, this).show(); 
		Waiting waitModel = new Waiting(); 
	}
	
	public ObservableList<Challenges> getChallenges(){
		
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
	
	public ObservableList<Challenges> getPlayers(){
		
		ObservableList<Challenges> data = null;
		
		try {
			ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT username FROM speler WHERE username != " + player.getUsername() + ";");
			data = FXCollections.observableArrayList();
			
			while (result.next()) {
				data.add(new Challenges(result.getString(1), result.getString(2)));
			}
		} catch (Exception e) {
			
		}
		
		return data; 
	}
	
	
	public Catan getCatan() {
		return catan;
	}
	
	public PlayerUser getPlayer() {
		return player;
	}
	
	public void setPlayer(String username) {
		this.player = new PlayerUser(username);
	}
}

