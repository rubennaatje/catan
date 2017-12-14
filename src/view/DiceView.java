package view;

import java.util.Random;

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

	public void showDice(int totalthrow) {
		if(totalthrow < 2)
			return;
		
		double max = (double)totalthrow / 2;
		double min = (double)totalthrow - 6;
	
		if(min < 1) {
			min = 1;
		}
		
		Random r = new Random();
		int n1 = (int) (r.nextDouble() * (max - min) + min);
		int n2 = totalthrow - n1;
		dice1.setImage(new Image("/view/images/dice" + Integer.toString(n1) +".png"));
		dice2.setImage(new Image("/view/images/dice" + Integer.toString(n2) +".png"));
	}

	
	
}
