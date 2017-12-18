package view;

import javafx.scene.shape.Rectangle;
import model.Street;

public class StreetView extends Rectangle {
	Street streetData;

	public StreetView(Street streetData) {
		this.streetData = streetData;
	}

	public Street getStreetModel() {
		return streetData;
	}
}