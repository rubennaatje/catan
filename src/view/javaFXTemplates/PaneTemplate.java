package view.javaFXTemplates;
import java.io.IOException;
import java.net.URL;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

@DefaultProperty("extension")

public abstract class PaneTemplate extends Pane {

	private @FXML Pane extension;

	public PaneTemplate(URL fxmlIn) {
		super();
		loadFxml(fxmlIn, this);
	}

	public ObservableList<Node> getExtension() {
		return extension.getChildren();
	}

	protected static void loadFxml(URL fxmlFile, Object rootController) {
		FXMLLoader loader = new FXMLLoader(fxmlFile);
		loader.setController(rootController);
		loader.setRoot(rootController);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
