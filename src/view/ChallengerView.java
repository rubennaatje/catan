package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import controller.AlertManager;
import controller.CatanController;
import controller.DatabaseManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Challenge;
import model.PlayerUser;
import view.javaFXTemplates.PaneTemplate;

public class ChallengerView extends PaneTemplate {
		
	private CatanController controller;
	
	@FXML private Button btnChallenge; 
	@FXML private Button btnBack;
	
	@FXML private TableView<PlayerUser> uitdager;
	@FXML private TableColumn<PlayerUser, String> playerName;
	
	public ChallengerView(Stage stage, CatanController controller) {
		super(ChallengerView.class.getResource("fxml/ChallengerView.fxml"), stage);
		this.controller = controller;
		
		addBoard(controller.getPlayers());
		
		btnChallenge.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				 if (uitdager.getSelectionModel().getSelectedItems().size() == 3) {
					try {
						ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT MAX(idspel) as idspel FROM spel");
						result.next();
						String gameId = (result.getString("idspel") + 1);
						result.close();
						
						ArrayList<String> kleuren = new ArrayList<>();
						ResultSet kleurenResult = DatabaseManager.createStatement().executeQuery("SELECT kleur FROM speelkleur");
						
						while (kleurenResult.next()) {
							kleuren.add(kleurenResult.getString(1));
						}
						
						kleurenResult.close();
						
						int count = 1;
						String kleur = kleuren.get(ThreadLocalRandom.current().nextInt(0, kleuren.size()));
						kleuren.remove(kleur);
						
						DatabaseManager.createStatement().executeQuery("INSERT INTO speler VALUES('" + gameId + "', '" + controller.getPlayer().getUsername() + "', '" + kleur + "', 'uitdager', 0, " + count + ", 0)");
						
						for (PlayerUser player : uitdager.getSelectionModel().getSelectedItems()) {
							count++;
							kleur = kleuren.get(ThreadLocalRandom.current().nextInt(0, kleuren.size()));
							kleuren.remove(kleur);
							DatabaseManager.createStatement().executeQuery("INSERT INTO speler VALUES('" + gameId + "', '" + player.getUsername() + "', '" + kleur + "', 'uitgedaagde', 0, " + count + ", 0)");
						}
						 
						controller.openWaitingScreen(new Challenge(gameId, controller.getPlayer().getUsername()));
					}
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 } else {
					 new AlertManager(AlertType.ERROR, "Challenge error!", "Please challenge 3 players");
				 }
			}
		});

		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openMenuScreen();
			}
		});
	}
	
	public void addBoard(ObservableList<PlayerUser> data) {
		playerName.setCellValueFactory(new PropertyValueFactory<PlayerUser, String>("username"));
		uitdager.setItems(data);
	}	
}
