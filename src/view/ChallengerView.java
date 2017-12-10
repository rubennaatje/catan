package view;

import java.sql.SQLException;

import controller.CatanController;
import controller.DatabaseManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
						for (PlayerUser player : uitdager.getSelectionModel().getSelectedItems()) {
							DatabaseManager.createStatement().executeQuery("INSERT INTO spel VALUES()");
						}
						 
						controller.openWaitingScreen();
					}
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
