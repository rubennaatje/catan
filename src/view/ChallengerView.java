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
import model.PlayerUser;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
					 controller.openWaitingScreen();
				 } else {
					 
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
