package view.javaFXTemplates;

import java.io.IOException;
import java.net.URL;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@DefaultProperty("extension")
public abstract class PaneTemplate extends Pane {

	private @FXML Pane extension;
	protected Stage stage;

	public PaneTemplate(URL url, Stage stage) {
		super();
		this.stage = stage;
		loadFxml(url, this);
	}

	private void loadFxml(URL url, Object rootController) {
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(rootController);
		loader.setRoot(rootController);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
<<<<<<< HEAD

	/**
	 * Should only be used for quick testing off separate functions. Not, controller
=======
	/**
	 * Should only be used for quick testing of separate functions. Not, controller
>>>>>>> 9b34aa087c68b5747dc066a9cdb41de52866390e
	 * functionality. All viewComponents extended on PaneTemplate should be managed
	 * in one scene, not created in its object.
	 * 
	 */
	@Deprecated
	public void show() {
		Scene scene = new Scene(this);
		scene.getStylesheets().add(getClass().getResource("/view/style/application.css").toExternalForm());
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

	public ObservableList<Node> getExtension() {
		return extension.getChildren();
	}
}
