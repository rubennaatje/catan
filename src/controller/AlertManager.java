package controller;

import javafx.scene.control.Alert;

public class AlertManager extends Alert {

	public AlertManager(AlertType type, String header, String content) {
		super(type, "Catan");
		this.setHeaderText(header);
		this.setContentText(content);
		this.showAndWait();
	}

}
