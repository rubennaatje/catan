package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.PlayerRank;
import view.javaFXTemplates.PaneTemplate;

public class LeaderBoardView extends PaneTemplate {
	
    @FXML private TableView<PlayerRank> TableView;
    @FXML private TableColumn<PlayerRank, String> PlayerPosition;
    @FXML private TableColumn<PlayerRank, String> PlayerName;
    @FXML private TableColumn<PlayerRank, String> AmountOfWins;
	
	
	public LeaderBoardView(Stage stage)
	{
		super(LeaderBoardView.class.getResource("fxml/LeaderBoardView.fxml"), stage);
	}

	public void addBoard(ObservableList<PlayerRank> fillLeaderboard)
	{
		
		
		PlayerPosition = new TableColumn<>("Rank");
		PlayerPosition.setCellValueFactory(new PropertyValueFactory<PlayerRank, String>("rank"));
		PlayerName.setCellValueFactory(new PropertyValueFactory<PlayerRank, String>("name"));
		PlayerName = new TableColumn<>("Name");
		AmountOfWins.setCellValueFactory(new PropertyValueFactory<PlayerRank, String>("gamesWon"));
		AmountOfWins = new TableColumn<>("AmountOfWins");
		TableView.setItems(fillLeaderboard);
		TableView.getColumns().addAll(PlayerPosition,PlayerName,AmountOfWins);
	}	
	
}
