package controller;

import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Catan;
import model.Challenges;
import model.PlayerRank;
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
		new ChallengerView(stage, this).show();
	}
	
	public void openChallengesScreen() {
		new ChallengesView(stage, this).show();
	}
	

	public void openWaitingScreen() {
		new WaitingView(stage, this).show(); 
		Waiting waitModel = new Waiting(); 
	}
	
	public ObservableList<Challenges> getChallenges(){
		ObservableList<Challenges> data = FXCollections.observableArrayList();
		
		try {
			ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT username, idspel FROM speler WHERE idspel IN (SELECT idspel from speler where username = '" + "lesley "+ "' AND speelstatus = 'uitgedaagde') AND speelstatus = 'uitdager';");
			
			while (result.next()) {
				data.add(new Challenges(result.getString(1), result.getString(2)));
			}
		} catch (Exception e) {
			
		}
		
		return data; 
	}
	
	public ObservableList<PlayerUser> getPlayers(){
		ObservableList<PlayerUser> data = FXCollections.observableArrayList();
		
		try {
			ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT username FROM speler WHERE username != '" + player.getUsername() + "';");
			
			while (result.next()) {
				data.add(new PlayerUser(result.getString(1)));
			}
		} catch (Exception e) {
			
		}
		
		return data; 
	}
	
	public ObservableList<PlayerRank> getLeaderboard(){
		ObservableList<PlayerRank> data = FXCollections.observableArrayList();
		
		try {
			ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT username FROM speler");
			int count = 0;
			while (result.next()) {
				count++;
				data.add(new PlayerRank(String.valueOf(count), result.getString(1), result.getString(1)));
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

