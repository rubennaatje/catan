package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.PlayerModel;
import view.PickResourceView;

public class StealResourceController {
	private PickResourceView ResourcesView;
	private Stage popUpPickResources;
	private String resourceType = "";
	private DevelopCardController devCon;
	private int index = 0;
	private PlayerModel currentPlayer;

	public StealResourceController(DevelopCardController controller) {
		
		popUpPickResources = new Stage();
		ResourcesView = new PickResourceView(this);
		Scene scenePickResources = new Scene(ResourcesView);
		popUpPickResources.setScene(scenePickResources);
		popUpPickResources.setResizable(false);
		popUpPickResources.initStyle(StageStyle.UNDECORATED);
		this.devCon = controller;
	}
	
	public void showResources() {
		popUpPickResources.show();
	}
	
	public void showResources(int i,PlayerModel player) {
		popUpPickResources.show();
		index = i;
		this.currentPlayer = player;
	}
	
	public void hideResources() {
		popUpPickResources.hide();
		
	}
	
	public String getResource()
	{
		return resourceType;
	}
	
	public void setResource(String resourceType)
	{
		this.resourceType = resourceType;
	}
	
	public void monopoliePlayed()
	{
		devCon.setResourceType(index, resourceType);
		devCon.playCard(index);
		this.currentPlayer.refresh();

	}
}
