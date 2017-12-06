package view;

import javafx.fxml.FXML;

import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.CatanController;
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
		
	private CatanController controller;
	
	@FXML private Button btnAccept; 
	@FXML private Button btnDecline;
	@FXML private Button btnBack;
	
	@FXML private TableView<Challenges> uitdager;
	@FXML private TableColumn<Challenges, String> playerName;
	@FXML private TableColumn<Challenges, String> gameId;
	
	public ChallengeView(Stage stage, CatanController controller) {
		super(ChallengeView.class.getResource("fxml/ChallengeView.fxml"), stage);
		this.controller = controller;
		
		addBoard(controller.fillChallenges());
		
		btnAccept.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Challenges challenge = uitdager.getSelectionModel().getSelectedItem();
				challenge.accept();
			}
		});
		
		btnDecline.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Challenges challenge = uitdager.getSelectionModel().getSelectedItem();
				challenge.decline();
			}
		});

		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openMenuScreen();
			}
		});
	}
	
	public void addBoard(ObservableList<Challenges> data) {
		playerName.setCellValueFactory(new PropertyValueFactory<Challenges, String>("playerName"));
		gameId.setCellValueFactory(new PropertyValueFactory<Challenges, String>("gameId")); 
		uitdager.setItems(data);
	}	
}
