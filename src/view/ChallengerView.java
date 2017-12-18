package view;

import com.sun.javafx.tk.Toolkit;

import controller.AlertManager;
import controller.CatanController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class ChallengerView extends PaneTemplate {
		
	private CatanController controller;
	
	@FXML private Button btnChallenge; 
	@FXML private Button btnBack;
	@FXML private CheckBox random;
	
	@FXML private TableView<String> uitdager;
	@FXML private TableColumn<String, String> playerName;
	
	public ChallengerView(Stage stage, CatanController controller) {
		super(ChallengerView.class.getResource("fxml/ChallengerView.fxml"), stage);
		this.controller = controller;
		
		EventHandler<MouseEvent> eventHandler = ( event ) ->
		{
		    if ( !event.isShortcutDown() )
		    {
		        Event.fireEvent( event.getTarget(), cloneMouseEvent( event ) );
		        event.consume();
		    }
		};

		uitdager.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
		uitdager.addEventFilter( MouseEvent.MOUSE_PRESSED, eventHandler );
		uitdager.addEventFilter( MouseEvent.MOUSE_RELEASED, eventHandler );
		
		new Thread(() -> {
			addBoard(controller.getPlayers());
		}).start();
		
		btnChallenge.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				//checkbox
				if (random.isSelected()) {
					controller.setRandom(true);
		            
		        } else {
		        	controller.setRandom(false);
		        }
				
				 if (uitdager.getSelectionModel().getSelectedItems().size() == 3) {
					controller.createGame(uitdager.getSelectionModel().getSelectedItems());
				 } else {
					 new AlertManager(AlertType.ERROR, "Challenge error!", "Please challenge 3 players");
				 }
			}
			
		});

		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				controller.openMenuScreen();
			}
		});
		
	}
	

	public void addBoard(ObservableList<String> observableList) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	playerName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
				uitdager.setItems(observableList);
			}
		});
	}
	
	private MouseEvent cloneMouseEvent( MouseEvent event )
	{
	    switch (Toolkit.getToolkit().getPlatformShortcutKey())
	    {
	        case SHIFT:
	            return new MouseEvent(
	                    event.getSource(),
	                    event.getTarget(),
	                    event.getEventType(),
	                    event.getX(),
	                    event.getY(),
	                    event.getScreenX(),
	                    event.getScreenY(),
	                    event.getButton(),
	                    event.getClickCount(),
	                    true,
	                    event.isControlDown(),
	                    event.isAltDown(),
	                    event.isMetaDown(),
	                    event.isPrimaryButtonDown(),
	                    event.isMiddleButtonDown(),
	                    event.isSecondaryButtonDown(),
	                    event.isSynthesized(),
	                    event.isPopupTrigger(),
	                    event.isStillSincePress(),
	                    event.getPickResult()
	            );

	        case CONTROL:
	            return new MouseEvent(
	                    event.getSource(),
	                    event.getTarget(),
	                    event.getEventType(),
	                    event.getX(),
	                    event.getY(),
	                    event.getScreenX(),
	                    event.getScreenY(),
	                    event.getButton(),
	                    event.getClickCount(),
	                    event.isShiftDown(),
	                    true,
	                    event.isAltDown(),
	                    event.isMetaDown(),
	                    event.isPrimaryButtonDown(),
	                    event.isMiddleButtonDown(),
	                    event.isSecondaryButtonDown(),
	                    event.isSynthesized(),
	                    event.isPopupTrigger(),
	                    event.isStillSincePress(),
	                    event.getPickResult()
	            );

	        case ALT:
	            return new MouseEvent(
	                    event.getSource(),
	                    event.getTarget(),
	                    event.getEventType(),
	                    event.getX(),
	                    event.getY(),
	                    event.getScreenX(),
	                    event.getScreenY(),
	                    event.getButton(),
	                    event.getClickCount(),
	                    event.isShiftDown(),
	                    event.isControlDown(),
	                    true,
	                    event.isMetaDown(),
	                    event.isPrimaryButtonDown(),
	                    event.isMiddleButtonDown(),
	                    event.isSecondaryButtonDown(),
	                    event.isSynthesized(),
	                    event.isPopupTrigger(),
	                    event.isStillSincePress(),
	                    event.getPickResult()
	            );

	        case META:
	            return new MouseEvent(
	                    event.getSource(),
	                    event.getTarget(),
	                    event.getEventType(),
	                    event.getX(),
	                    event.getY(),
	                    event.getScreenX(),
	                    event.getScreenY(),
	                    event.getButton(),
	                    event.getClickCount(),
	                    event.isShiftDown(),
	                    event.isControlDown(),
	                    event.isAltDown(),
	                    true,
	                    event.isPrimaryButtonDown(),
	                    event.isMiddleButtonDown(),
	                    event.isSecondaryButtonDown(),
	                    event.isSynthesized(),
	                    event.isPopupTrigger(),
	                    event.isStillSincePress(),
	                    event.getPickResult()
	            );

	        default: // well return itself then
	            return event;

	    }
	}
}
