package view;

import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class ChallengeView extends PaneTemplate {
	
	public ChallengeView(Stage stage) {
		super(ChallengeView.class.getResource("fxml/ChallengeView.fxml"), stage);
	}
	
	public void show() {
		super.show();
	}
}
