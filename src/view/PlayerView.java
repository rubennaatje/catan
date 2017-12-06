package view;

import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.PlayerModel;
import view.javaFXTemplates.PaneTemplate;

public class PlayerView extends PaneTemplate implements Observer {
	
	  	@FXML private Text cards;
	    @FXML private Text devcards;
	    @FXML private Text knights;
	    @FXML private Text score;
	    @FXML private Text road;
		

	public PlayerView() {
		super(PlayerView.class.getResource("fxml/PlayerViewComp.fxml"));
	}


	@Override
	public void update(Observable arg0, Object arg1)
	{
		PlayerModel playerData = (PlayerModel)arg0;
		cards.setText(playerData.getCards());
		devcards.setText(playerData.getDevCards());
		knights.setText(playerData.getKnights());
		score.setText(playerData.getScore());
		road.setText(playerData.getRoad());
	}	
	

}
