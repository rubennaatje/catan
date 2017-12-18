package view;

import controller.GameController;
import controller.StealFromPlayerController;
import model.PlayerModel;
import view.javaFXTemplates.PaneTemplate;

public class PickPlayerView extends PaneTemplate {
	
	private StealFromPlayerController Stealcontroller;
	private GameController GameController; 
	private int i; 
	private int paneWidth = 700; 
	
	public PickPlayerView(StealFromPlayerController Stealcontroller,  GameController GameController, PlayerModel[] PlayerModel) {
		super(PickPlayerView.class.getResource("fxml/ChooseTradePlayerView.fxml"));
		
		this.Stealcontroller = Stealcontroller; 
		this.GameController = GameController; 
		showPlayers(PlayerModel);
	}
	
	public void showPlayers(PlayerModel[] playerModel) {
		i = paneWidth/(playerModel.length + 1);  
		int boxAmount = 0;
		
		while(boxAmount < playerModel.length) {
			int breedte = i * (boxAmount + 1); 
			
			PickPlayerviewComponent pickPlayer = new PickPlayerviewComponent(Stealcontroller, playerModel[boxAmount]); 
			pickPlayer.setLayoutY(0); 
			pickPlayer.setLayoutX(breedte - (pickPlayer.getPrefWidth() / 2));
			getChildren().add(pickPlayer); 
		
			boxAmount++;
		}
	}
}
