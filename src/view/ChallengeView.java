package view;

import javafx.fxml.FXML;

import javafx.scene.control.cell.PropertyValueFactory;
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
	
	//met die bovenste dingetejs doe ik eigl niks..
	
	public ChallengeView(Stage stage) {
		super(ChallengeView.class.getResource("fxml/ChallengeView.fxml"), stage);
		
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
	
	public void addBoard(ObservableList<Challenges> fillChallenges){

		playerName.setCellValueFactory(new PropertyValueFactory<Challenges, String>("playerName"));
		gameId.setCellValueFactory(new PropertyValueFactory<Challenges, String>("gameId")); 
		uitdager.setItems(fillChallenges);

	}	
}
