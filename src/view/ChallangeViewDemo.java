package view;

import java.net.URL;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class ChallangeViewDemo extends PaneTemplate {

	public ChallangeViewDemo(Stage stage) {
		super(ChallengeView.class.getResource("fxml/ChallengeView.fxml"), stage);
	}
	
	
	public void testFunctiebutton(MouseEvent event) {
		System.out.println("testing12334444");
	}
}
