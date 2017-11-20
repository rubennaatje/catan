package view;

import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class PlayerView extends PaneTemplate {

	public PlayerView(Stage stage) {
		super(PlayerView.class.getResource("fxml/PlayerView.fxml"), stage);
	}

}
