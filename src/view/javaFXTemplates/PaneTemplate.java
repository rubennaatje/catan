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
