package view;

import controller.StealResourceController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import view.javaFXTemplates.PaneTemplate;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PickResourceView extends PaneTemplate{

	@FXML private ImageView erts; 
	@FXML private ImageView graan; 
	@FXML private ImageView hout; 
	@FXML private ImageView steen; 
	@FXML private ImageView wol; 
	DropShadow ds = new DropShadow( 20, Color.RED );
	private StealResourceController controller;
	
	public PickResourceView(StealResourceController controller) {
			super(PickResourceView.class.getResource("fxml/PickResourceView.fxml"));
		this.controller = controller; 
		
		erts.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				erts();
				controller.hideResources();
			}
		});
		
		graan.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				graan();  
				controller.hideResources();
			}
		});
		hout.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				hout();   
				controller.hideResources();
			}
		});
		wol.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				wol();  
				controller.hideResources();
			}
		});
		
		steen.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				steen();  
				controller.hideResources();
			}
		});
	}

	public String erts(){
		System.out.println("erts");
		return ""; 
	}
	
	public String graan(){
		System.out.println("gaan");
		return ""; 
	}
	
	public String hout(){
		System.out.println("hout");
		return ""; 
	}
	
	public String wol(){
		System.out.println("wol");
		return ""; 
	}
	
	public String steen(){
		System.out.println("steen");
		return ""; 
	}
}

	 

