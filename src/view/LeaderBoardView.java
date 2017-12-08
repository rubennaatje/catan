package view;

import com.jfoenix.controls.JFXButton;

import controller.CatanController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.PlayerRank;
import view.javaFXTemplates.PaneTemplate;

public class LeaderBoardView extends PaneTemplate {
	
	private CatanController controller;
	
	@FXML private JFXButton btnBack;
	
    @FXML private TableView<PlayerRank> TableView;
    @FXML private TableColumn<PlayerRank, String> PlayerPosition;
    @FXML private TableColumn<PlayerRank, String> PlayerName;
    @FXML private TableColumn<PlayerRank, String> AmountOfWins;
	
	
	public LeaderBoardView(Stage stage, CatanController controller) {
		super(LeaderBoardView.class.getResource("fxml/LeaderBoardView.fxml"), stage);
				
		this.controller = controller;
		
		addBoard(controller.getLeaderboard());
		
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openMenuScreen();
			}
		});
	}

	public void addBoard(ObservableList<PlayerRank> fillLeaderboard) {
		PlayerPosition.setCellValueFactory(new PropertyValueFactory<PlayerRank, String>("rank"));
		PlayerName.setCellValueFactory(new PropertyValueFactory<PlayerRank, String>("name"));
		AmountOfWins.setCellValueFactory(new PropertyValueFactory<PlayerRank, String>("gamesWon"));
		TableView.setItems(fillLeaderboard);
	}	
	
}
