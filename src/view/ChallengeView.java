package view;

import javafx.fxml.FXML;

import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;
import model.Challenges;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ChallengeView extends PaneTemplate {
		
	@FXML private Button btnAccept; 
	@FXML private Button btnDecline;
	@FXML private Button btnBack;
	
	@FXML private TableView<Challenges> uitdager;
	@FXML private TableColumn<Challenges, String> playerName;
	@FXML private TableColumn<Challenges, String> gameId;
	
	public ChallengeView(Stage stage) {
		super(ChallengeView.class.getResource("fxml/ChallengeView.fxml"), stage);
		
		addBoard();
		
		btnAccept.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Challenges challenge = uitdager.getSelectionModel().getSelectedItem();
				try {
					DatabaseManager.createStatement().executeUpdate("Update speelstatus SET speelstatus='accepteerd' where idspel = " + challenge.getGameId() + ";");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		btnDecline.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Challenges challenge = uitdager.getSelectionModel().getSelectedItem();
				try {
					DatabaseManager.createStatement().executeUpdate("Update speelstatus SET speelstatus='gewijgerd' where idspel = " + challenge.getGameId() + ";");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				new MenuView(stage).show();
			}
		});
	}


	public void listView(MouseEvent event) {
		System.out.println("You clicked listview"); 
		//hier spelers id laten zien
		//hier game id laten zien 
	}
	
	public void addBoard() {
		ObservableList<Challenges> data = null;
		
		try {
			ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT username, idspel FROM speler WHERE idspel IN (SELECT idspel from speler where username = 'ger' AND speelstatus = 'uitgedaagde') AND speelstatus = 'uitdager';");
			data = FXCollections.observableArrayList();
			
			while (result.next()) {
				data.add(new Challenges(result.getString(1), result.getString(2)));
			}
		} catch (SQLException e) {
			
		}
		
		playerName.setCellValueFactory(new PropertyValueFactory<Challenges, String>("playerName"));
		gameId.setCellValueFactory(new PropertyValueFactory<Challenges, String>("gameId")); 
		uitdager.setItems(data);
	}	
}
