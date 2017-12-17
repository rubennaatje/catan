package model;

import controller.CatanController;

public class PickPlayer {
	
	private CatanController controller; 

	public PickPlayer(CatanController controller) {
		this.controller = controller; 
	}
	
	public void playersArroundTile() {
		//BoardHelper.getPlayersSurroundingTile(controller.getPlayer().getSpelId(),); 
		// je krijgt terug een lijst met spelers die een stuk om een tegel hebben liggen. 
		//dus je moet de locatie van de robber in voeren. 
		//als je de locatie van de robber invoert en t spel ide
		//krijg je een lijst van spelers terug die iets om deze tegel hebben liggen. 	
	}
	
	public String getPlayerNames() {
		//PlayerName.setText(controller.getUsername());
		return "playername"; 
	}
	
	
}
