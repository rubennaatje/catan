package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Catan;
import model.Challenge;
import model.PlayerModel;
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
	public final static int refreshTime = 1000;
	
	private int WaitingOn; 
	
	public CatanController(Stage stage) {
		this.catan = new Catan();
		this.stage = stage;
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
	

	public void openWaitingScreen(Challenge selected) {
		Waiting waitModel = new Waiting(this, selected);
		setWaitingOn(waitModel.waitForPlayers());
		new WaitingView(stage, this).show(); 
	}

	public int getWaitingOn() {
		 return WaitingOn; 
	}
	
	public void setWaitingOn(int WaitingOn) {
		 this.WaitingOn = WaitingOn; 
	}
	
	public void startGame(String gameid, boolean creation) {

		try {
			catan.initGame(gameid, creation);
			catan.setPlayer(player);
	        PlayerModel[] players = catan.getCurrentPlayers();
	        GameController gameController = new GameController(gameid, players, player.getPlayerNumber() , stage);
	        
			new Thread(() -> gameController.start()).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ObservableList<Challenge> getChallenges(){
		ObservableList<Challenge> data = FXCollections.observableArrayList();
		
		try {
			ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT username, idspel FROM speler WHERE idspel IN (SELECT idspel from speler where username = '" + player.getUsername() + "' AND speelstatus = 'uitgedaagde') AND speelstatus = 'uitdager';");
			while (result.next()) {
				data.add(new Challenge(result.getString(1), result.getString(2), player));
			}
		} catch (Exception e) {
			
		}
		
		return data; 
	}
	 
	public ObservableList<PlayerUser> getPlayers(){
		ObservableList<PlayerUser> data = FXCollections.observableArrayList();
		
		try {
			ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT username FROM account WHERE username != '" + player.getUsername() + "';");
			
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
			ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT username, aantal_spellen_gewonnen FROM speelresultaat ORDER BY som_behaalde_punten DESC");
			int count = 0;
			while (result.next()) {
				count++;
				data.add(new PlayerRank(String.valueOf(count), result.getString(1), result.getString(2)));
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
	
	public void createGame(ObservableList<PlayerUser> items) {
		try {
			ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT MAX(idspel) as idspel FROM spel");
			result.next();
			String gameId = String.valueOf(Integer.parseInt(result.getString("idspel")) + 1);
			result.close();
			
			DatabaseManager.createStatement().executeUpdate(
					"INSERT INTO spel   (idspel, grootste_rm_username, langste_hr_username, beurt_username, gedobbeld, laatste_worp, israndomboard, eersteronde) VALUES ("
							+ gameId + ", NULL, NULL, NULL, NULL, NULL, TRUE, 0);");
			
			ArrayList<String> kleuren = new ArrayList<>();
			ResultSet kleurenResult = DatabaseManager.createStatement().executeQuery("SELECT kleur FROM speelkleur");
			
			while (kleurenResult.next()) {
				kleuren.add(kleurenResult.getString(1));
			}
			
			kleurenResult.close();
			
			int count = 1;
			String kleur = kleuren.get(ThreadLocalRandom.current().nextInt(0, kleuren.size()));
			kleuren.remove(kleur);
			
			DatabaseManager.createStatement().executeUpdate("INSERT INTO speler VALUES('" + gameId + "', '" + getPlayer().getUsername() + "', '" + kleur + "', 'uitdager', 0, " + count + ", 0)");
			
			for (PlayerUser player : items) {
				count++;
				kleur = kleuren.get(ThreadLocalRandom.current().nextInt(0, kleuren.size()));
				kleuren.remove(kleur);
				DatabaseManager.createStatement().executeUpdate("INSERT INTO speler VALUES('" + gameId + "', '" + player.getUsername() + "', '" + kleur + "', 'uitgedaagde', 0, " + count + ", 0)");
			}
			 
			openWaitingScreen(new Challenge(getPlayer().getUsername(), gameId, getPlayer()));
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String isInGame() {
		String gameid = null;
		
		try {
			ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT DISTINCT(idspel), username FROM speler WHERE idspel NOT IN (SELECT idspel FROM speler WHERE speelstatus = 'uitgespeeld' OR speelstatus = 'afgebroken' OR speelstatus = 'geweigerd' OR speelstatus = 'uitgedaagde') AND username = '" + getPlayer().getUsername() + "'");
			result.next();
			gameid = result.getString(1);
			result.close();
		} catch(SQLException e) {
			
		}
		
		return gameid;
	}
}
