package view;

import java.sql.SQLException;

import controller.ChatController;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import view.javaFXTemplates.PaneTemplate;

public class ChatView extends PaneTemplate {
	@FXML
	TextField tfMessage;
	@FXML
	ListView<Text> listView;
	@FXML
	Button sendButton;

	ChatController controller;

	public ChatView(ObservableList<Text> observableList, ChatController controller) throws SQLException {
		super(ChatView.class.getResource("fxml/ChatView.fxml"));
		this.controller = controller;
		listView.setItems(observableList);

		listView.getItems().addListener((Change<? extends Text> arg0) -> listView.scrollTo(arg0.getList().size() - 1));
	}

	public void sendMessage(MouseEvent arg) {
		if (!tfMessage.getText().equals("")) {
			controller.sendMessage(tfMessage.getText());
			tfMessage.clear();
		}
	}

	public ObservableValue<Number> getListWidth() {
		return listView.widthProperty().subtract(30);
	}

}
