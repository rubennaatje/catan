package view;
import controller.StealResourceController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.javaFXTemplates.PaneTemplate;

public class PickResourceView extends PaneTemplate{

	@FXML private ImageView erts; 
	@FXML private ImageView graan; 
	@FXML private ImageView hout; 
	@FXML private ImageView steen; 
	@FXML private ImageView wol; 

	private StealResourceController controller;
	
	public PickResourceView(StealResourceController controller) {
			super(PickResourceView.class.getResource("fxml/PickResourceView.fxml"));
		this.controller = controller; 
		
		erts.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				resourceSelected("erts");
			}
		});
		
		graan.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				resourceSelected("graan");
				
			}
		});
		hout.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				resourceSelected("hout");
			}
		});
		wol.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				resourceSelected("wol");
			}
		});
		
		steen.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				resourceSelected("baksteen");
			}
		});
	}
	
	private void resourceSelected(String resource)
	{
		controller.setResource(resource);
		controller.hideResources();
		controller.monopoliePlayed();
	}
}
