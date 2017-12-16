<<<<<<< HEAD
package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.PickResourceView;

public class StealResourceController {
	private PickResourceView ResourcesView;
	private Stage popUpPickResources;

	public StealResourceController() {
		
		popUpPickResources = new Stage();
		ResourcesView = new PickResourceView(this);
		Scene scenePickResources = new Scene(ResourcesView);
		popUpPickResources.setScene(scenePickResources);
		popUpPickResources.setResizable(false);
		popUpPickResources.initStyle(StageStyle.UNDECORATED);
	}
	
	public void showResources() {
		popUpPickResources.show();
	}
	public void hideResources() {
		popUpPickResources.hide();
	}
}
=======
package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.PickResourceView;

public class StealResourceController {
	private PickResourceView ResourcesView;
	private Stage popUpPickResources;

	public StealResourceController() {
		
		popUpPickResources = new Stage();
		ResourcesView = new PickResourceView(this);
		Scene scenePickResources = new Scene(ResourcesView);
		popUpPickResources.setScene(scenePickResources);
		popUpPickResources.setResizable(false);
		popUpPickResources.initStyle(StageStyle.UNDECORATED);
	}
	
	public void showResources() {
		popUpPickResources.show();
	}
	public void hideResources() {
		popUpPickResources.hide();
	}
}
>>>>>>> 612cf58feefd92651da25faad3191335256141a9
