package view;

import controller.CatanController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Challenges;
import view.javaFXTemplates.PaneTemplate;

public class ChallengesView extends PaneTemplate {
		
	private CatanController controller;
	
	@FXML private Button btnAccept; 
	@FXML private Button btnDecline;
	@FXML private Button btnBack;
	
	@FXML private TableView<Challenges> uitdager;
	@FXML private TableColumn<Challenges, String> playerName;
	@FXML private TableColumn<Challenges, String> gameId;
	
	public ChallengesView(Stage stage, CatanController controller) {
		super(ChallengesView.class.getResource("fxml/ChallengesView.fxml"), stage);
		this.controller = controller;
		
		addBoard(controller.getChallenges());
		
		btnAccept.setOnAction((ActionEvent e) -> {
			//if(uitdager.getSelectionModel().getSelectedItem() != null) {
				Challenges challenge = uitdager.getSelectionModel().getSelectedItem();
				controller.openWaitingScreen(uitdager.getSelectionModel().getSelectedItem());
				challenge.accept();		
			
			//} 
		});
		
		btnDecline.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(uitdager.getSelectionModel().getSelectedItem() != null) {
				Challenges challenge = uitdager.getSelectionModel().getSelectedItem();
				challenge.decline();
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
	
	public void addBoard(ObservableList<Challenges> data) {
		playerName.setCellValueFactory(new PropertyValueFactory<Challenges, String>("playerName"));
		gameId.setCellValueFactory(new PropertyValueFactory<Challenges, String>("gameId")); 
		uitdager.setItems(data);
	}	
}
