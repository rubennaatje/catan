package view;

import java.util.ArrayList;
import com.jfoenix.controls.JFXButton;

import controller.DatabaseManager;
import controller.DevelopCardController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.PlayerUser;
import view.javaFXTemplates.PaneTemplate;

public class CardView extends PaneTemplate {

	private ArrayList<String> cards = new ArrayList<>();
	private int selectedCard = 0;

	@FXML
	public ImageView imageview;

	@FXML
	public JFXButton nextbutton, previousbutton, selectbutton;

	@FXML
	public Label amount, selected;

	private Image notfound = new Image("/view/images/notfound.png");
	private Image nocards = new Image("/view/images/nocards.png");

	private Image ridder = new Image("/view/images/Ridderkaart.png");
	private Image bibliotheek = new Image("/view/images/BibliotheekKaart.png");
	private Image kathedraal = new Image("/view/images/KathedraalKaart.png");
	private Image markt = new Image("/view/images/Marktkaart.png");
	private Image monopolie = new Image("/view/images/MonopolieKaart.png");
	private Image stratenbouw = new Image("/view/images/StratenbouwKaart.png");
	private Image uitvinding = new Image("/view/images/Uitvindingkaart.png");
	private Image universiteit = new Image("/view/images/UniversiteitKaart.png");
	private Image parlement = new Image("/view/images/ParlementKaart.png");

	private String amountnumber;

	private DevelopCardController control;

	public CardView(Stage primaryStage) throws Exception {
		super(CardView.class.getResource("fxml/CardView.fxml"), primaryStage);
		cards.add(""); // adding card number 0
		
		addCards();
		loadWindow();
	}

	public CardView() {
		super(CardView.class.getResource("fxml/CardView.fxml"));
		cards.add(""); // adding card number 0
		
		addCards();
		loadWindow();
	}

	private void loadWindow() {

		if (cards.size() > 0) {
			selectedCard++;
			reloadAmount();
			showCard();
		} else {
			amount.setText("0");
			nextbutton.setDisable(true);
			previousbutton.setDisable(true);
			noCards();
		}
	}

	private void reloadAmount() {

		amountnumber = "" + (cards.size() - 1);
		amount.setText(amountnumber);
		String selectednumber = "" + selectedCard;
		selected.setText(selectednumber);

	}

	public void addCards() {
		

		

		selectbutton.setDisable(false);
		
		cards.add("ridder");
		cards.add("markt");
		cards.add("bibliotheek");
		cards.add("parlement");

		/*
		 * for(int x = 0; x < control.getPlayerCards().size(); x++) {
		 * cards.add(control.getPlayerCards().get(x).getCardname()); }
		 */
		
		reloadAmount();
	}

	public void nextCard() {

		// amount labels
		if (selectedCard < (cards.size() - 1)) {
			previousbutton.setDisable(false);
			selectedCard++;
			String selectednumber = "" + selectedCard;
			selected.setText(selectednumber);
		}
		if (selectedCard == (cards.size() - 1)) {
			nextbutton.setDisable(true);
		}

		showCard();

	}

	public void selectCard() {
		// use card function

		// remove card funcion
		System.out.println("kaart " + cards.get(selectedCard) + " is gebruikt");
		removeSelected();

	}

	public void previousCard() {

		if (selectedCard > 1) {
			nextbutton.setDisable(false);
			selectedCard--;
			String selectednumber = "" + selectedCard;
			selected.setText(selectednumber);
		}
		if (selectedCard == 1) {
			previousbutton.setDisable(true);
		}
			showCard();

	}

	private void showCard() {
		if (cards.get(selectedCard).equals("ridder")) {
			imageview.setImage(ridder);
		} else if (cards.get(selectedCard).equals("bibliotheek")) {
			imageview.setImage(bibliotheek);

		} else if (cards.get(selectedCard).equals("kathedraal")) {
			imageview.setImage(kathedraal);
		} else if (cards.get(selectedCard).equals("markt")) {
			imageview.setImage(markt);
		} else if (cards.get(selectedCard).equals("monopolie")) {
			imageview.setImage(monopolie);
		} else if (cards.get(selectedCard).equals("stratenbouw")) {
			imageview.setImage(stratenbouw);
		} else if (cards.get(selectedCard).equals("uitvinding")) {
			imageview.setImage(uitvinding);
		} else if (cards.get(selectedCard).equals("universiteit")) {
			imageview.setImage(universiteit);
		} else if (cards.get(selectedCard).equals("parlement")) {
			imageview.setImage(parlement);
		} else {
			imageview.setImage(notfound);
		}
	}

	private void noCards() {
		
		nextbutton.setDisable(true);
		selectbutton.setDisable(true);
		previousbutton.setDisable(true);
		imageview.setImage(nocards);

	}

	private void removeSelected() {

		cards.remove(selectedCard);
		if (selectedCard == 1) {
			// do nothing
		} else {
			selectedCard--;
		}
		
		if (cards.size() != 1) {
			showCard();
			reloadAmount();
		}
		else {
			
			selectbutton.setDisable(true);
			selectbutton.setDisable(true);
			noCards();
			selected.setText("0");
		}

	}

}
