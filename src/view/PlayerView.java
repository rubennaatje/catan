package view;

import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import model.PlayerModel;
import view.javaFXTemplates.PaneTemplate;

public class PlayerView extends PaneTemplate implements Observer {
	
	  	@FXML private Text cards;
	    @FXML private Text devcards;
	    @FXML private Text knight;
	    @FXML private Text score;
	    @FXML private Text road;
	    @FXML private Text naam;
		

	public PlayerView() {
		super(PlayerView.class.getResource("fxml/PlayerViewComp.fxml"));
	}


	@Override
	public void update(Observable arg0, Object arg1)
	{
		
		getStyleClass().clear();
		
		PlayerModel playerData = (PlayerModel)arg0;
		cards.setText(playerData.getCards());
		devcards.setText(playerData.getDevCards());
		knight.setText(playerData.getKnights());
		score.setText(playerData.getScore());
		road.setText(playerData.getRoad());
		naam.setText(playerData.getUsername());
		
		switch(playerData.getPlayerNumber())
		{
		case 1:  getStyleClass().add("playerRed");
			break;
		case 2: getStyleClass().add("playerWhite");
			break;
		case 3: getStyleClass().add("playerBlue");
			break;
		case 4:getStyleClass().add("playerOrange");
			break;
		}
		if(playerData.getPlayerTurn())
		{
			getStyleClass().add("playerOnTurn");
		}
		else
		{
			getStyleClass().add("playerNotOnTurn");
		}
	}	
	

}
