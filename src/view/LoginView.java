package view;

import controller.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginView {

	public void launchview(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/fxml/LoginView.fxml"));
			AnchorPane pane = loader.load();

			Scene scene = new Scene(pane);

			scene.getStylesheets().add(getClass().getResource("/styling/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
