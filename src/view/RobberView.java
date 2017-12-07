package view;

import javafx.scene.shape.Ellipse;
import model.GridLocation;

public class RobberView extends Ellipse {
	GridLocation loc;
	public RobberView(GridLocation loc) {
		super();
		this.loc = loc;
		
		setRadiusY(60);
		setRadiusX(20);
		
		getStyleClass().add("robber");
	}
	public GridLocation getLoc() {
		return loc;
	}
}
