package view;


import java.sql.SQLException;
import controller.DatabaseManager;
import controller.StealFromPlayerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.BoardHelper;
import model.PlayerModel;
import model.PlayerType;
import model.PlayerUser;
import view.javaFXTemplates.PaneTemplate;

public class PickPlayerviewComponent extends PaneTemplate{
	@FXML private HBox background; 
	@FXML private Button PlayerName; 
	
	//private PlayerType player; 
	private StealFromPlayerController controller; 
	
	public PickPlayerviewComponent(StealFromPlayerController controller, PlayerModel playerModel) {
		super(PickPlayerviewComponent.class.getResource("fxml/TradePlayer.fxml"));
		this.controller = controller; 

		PlayerName.setStyle("-fx-background-color: #B70000;");
		PlayerName.setText(playerModel.getUsername()); 
 

		PlayerName.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.hidePlayers(playerModel);
			}
		});
	}


}
