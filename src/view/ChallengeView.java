package view;

import javafx.fxml.FXML;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;
import model.Challenges;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ChallengeView extends PaneTemplate {
	
	@FXML Button accept; 
	@FXML Button decline;
	@FXML Button back;
	
	@FXML private TableView<Challenges> TableView;
	@FXML private TableColumn<Challenges, String> playerName;
	@FXML private TableColumn<Challenges, String> gameId;
	
	//met die bovenste dingetejs doe ik eigl niks..
	
	public ChallengeView(Stage stage) {
		super(ChallengeView.class.getResource("fxml/ChallengeView.fxml"), stage);
	}
	
	public void Accept(MouseEvent event) {
		System.out.println("You clicked accept"); 
	}
	
	public void Decline(MouseEvent event) {
		System.out.println("You clicked decline"); 
	}

	public void Back(MouseEvent event) {
		System.out.println("You clicked back"); 
	}


	public void listView(MouseEvent event) {
		System.out.println("You clicked listview"); 
		//hier spelers id laten zien
		//hier game id laten zien 
	}
	
	
	public void addBoard(ObservableList<Challenges> fillChallenges){

		playerName.setCellValueFactory(new PropertyValueFactory<Challenges, String>("playerName"));
		gameId.setCellValueFactory(new PropertyValueFactory<Challenges, String>("gameId")); 
		TableView.setItems(fillChallenges);

	}
	
	
	
	
	
	
	
	
	
	
}
