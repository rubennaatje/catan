package view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class DiceView extends PaneTemplate {

	
	@FXML private ImageView dice1;
	@FXML private ImageView dice2;
	
	public DiceView() {
		super(DiceView.class.getResource("fxml/Dice.fxml"));
	}
	
	public void showDice(int dicethrows[]) { //shows two dices
		dice1.setImage(new Image("/view/images/dice" + Integer.toString(dicethrows[0]) +".png"));
		dice2.setImage(new Image("/view/images/dice" + Integer.toString(dicethrows[1]) +".png"));
	}

	
	
}
