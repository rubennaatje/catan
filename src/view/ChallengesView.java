package view;

import controller.AlertManager;
import controller.CatanController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Challenge;
import model.PlayerRank;
import view.javaFXTemplates.PaneTemplate;

public class ChallengesView extends PaneTemplate {
		
	private CatanController controller;
	
	@FXML private Button btnAccept; 
	@FXML private Button btnDecline;
	@FXML private Button btnBack;
	
	@FXML private TableView<Challenge> uitdager;
	@FXML private TableColumn<Challenge, String> playerName;
	@FXML private TableColumn<Challenge, String> gameId;
	
	public ChallengesView(Stage stage, CatanController controller) {
		super(ChallengesView.class.getResource("fxml/ChallengesView.fxml"), stage);
		this.controller = controller;
		
		new Thread(() -> {
			addBoard(controller.getChallenges());
		});
		
		btnAccept.setOnAction((ActionEvent e) -> {

			if (controller.isInGame() == null) {
				if(uitdager.getSelectionModel().getSelectedItem() != null ) {
					Challenge challenge = uitdager.getSelectionModel().getSelectedItem();
	
					controller.openWaitingScreen(uitdager.getSelectionModel().getSelectedItem());
					challenge.accept();		
				} else {
					new AlertManager(AlertType.ERROR, "Challenge error!", "selecteer een challenge om hem te accepteren");
				}
			} else {
				new AlertManager(AlertType.ERROR, "Challenge error!", "je zit al in een game");
			}
		});
		
		btnDecline.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (controller.isInGame() == null) {
					if(uitdager.getSelectionModel().getSelectedItem() != null) {
						Challenge challenge = uitdager.getSelectionModel().getSelectedItem();
						challenge.decline();
						addBoard(controller.getChallenges());
						
					} else {
						new AlertManager(AlertType.ERROR, "Challenge error!", "selecteer een challenge om hem te weigeren");
					}
				} else {
					new AlertManager(AlertType.ERROR, "Challenge error!", "je zit al in een game");
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
	
	public void addBoard(ObservableList<Challenge> data) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	uitdager.getItems().clear();
				playerName.setCellValueFactory(new PropertyValueFactory<Challenge, String>("uitdager"));
				gameId.setCellValueFactory(new PropertyValueFactory<Challenge, String>("gameId")); 
				uitdager.setItems(data);
		    }
		});
	}	
}
