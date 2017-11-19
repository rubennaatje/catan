package view;

import java.awt.Point;

import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class PlayBoardView extends PaneTemplate {
	
	public PlayBoardView(Stage stageIn) {
		super(PlayerView.class.getResource("fxml/playboard.fxml"),stageIn);
	}

	
	public void addHex(Point[] pointsIn) {
		
	}
}
