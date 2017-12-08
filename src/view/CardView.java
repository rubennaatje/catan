package view;

import java.util.ArrayList;
import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import view.javaFXTemplates.PaneTemplate;

public class CardView extends PaneTemplate
{

	private ArrayList<String> cards = new ArrayList<>();
	private int selectedCard = 0;

	@FXML
	public ImageView imageview;

	@FXML
	public JFXButton nextbutton, previousbutton;
	
	@FXML
	public Label amount, selected;

	
	private Image notfound = new Image("/cards/notfound.png");
	private Image nocards = new Image("/cards/nocards.png");
	
	private Image ridder = new Image("/cards/Ridderkaart.png");
	private Image bibliotheek = new Image("/cards/BibliotheekKaart.png");
	private Image kathedraal = new Image("/cards/KathedraalKaart.png");
	private Image markt = new Image("/cards/Marktkaart.png");
	private Image monopolie = new Image("/cards/MonopolieKaart.png");
	private Image stratenbouw = new Image("/cards/StratenbouwKaart.png");
	private Image uitvinding = new Image("/cards/Uitvindingkaart.png");
	private Image universiteit = new Image("/cards/UniversiteitKaart.png");
	
	public CardView()
	{
		super(CardView.class.getResource("CardView.fxml"));
		loadWindow();
		
	}
	

	
	public CardView(Stage primaryStage)
	{
		super(CardView.class.getResource("fxml/CardView.fxml"), primaryStage);
		loadWindow();
	}



	public void loadWindow()
	{
		addCards();
		String amountnumber = "" + (cards.size() - 1);
		amount.setText(amountnumber);

		if (cards.size() > 0)
		{
			previousbutton.setDisable(true);
			selectedCard++;
			String selectednumber = "" + selectedCard;
			selected.setText(selectednumber);
			showCard();
		}
		else {
			amount.setText("0");
			nextbutton.setDisable(true);
			previousbutton.setDisable(true);
			noCards();
		}
	}
	
	
	
	public void addCards()
	{
		cards.add("");
		cards.add("ridder");
		cards.add("bibliotheek");
		cards.add("kathedraal");
		cards.add("markt");
		cards.add("monopolie");
		cards.add("stratenbouw");
		cards.add("uitvinding");
		cards.add("universiteit");
	}

	public void nextCard()
	{

		// amount labels
		if (selectedCard < (cards.size() - 1))
		{
			previousbutton.setDisable(false);
			selectedCard++;
			String selectednumber = "" + selectedCard;
			selected.setText(selectednumber);
		}
		if(selectedCard == (cards.size() - 1)) {
			nextbutton.setDisable(true);
		}

		showCard();

	}

	public void previousCard()
	{

		if (selectedCard > 1)
		{
			nextbutton.setDisable(false);
			selectedCard--;
			String selectednumber = "" + selectedCard;
			selected.setText(selectednumber);
		} 
		if(selectedCard == 1) {
			previousbutton.setDisable(true);
		}
		
		showCard();

	}

	private void showCard()
	{
		if (cards.get(selectedCard) == "ridder")
		{
			imageview.setImage(ridder);
		} else if (cards.get(selectedCard) == "bibliotheek")
		{
			imageview.setImage(bibliotheek);
		
		} else if (cards.get(selectedCard) == "kathedraal")
		{
			imageview.setImage(kathedraal);
		} else if (cards.get(selectedCard) == "markt")
		{
			imageview.setImage(markt);
		} else if (cards.get(selectedCard) == "monopolie")
		{
			imageview.setImage(monopolie);
		} else if (cards.get(selectedCard) == "stratenbouw")
		{
			imageview.setImage(stratenbouw);
		} else if (cards.get(selectedCard) == "uitvinding")
		{
			imageview.setImage(uitvinding);
		} else if (cards.get(selectedCard) == "universiteit")
		{
			imageview.setImage(universiteit);
		} else
		{
			imageview.setImage(notfound);
		}
	}
	
	
	private void noCards()
	{
		imageview.setImage(nocards);
		
	}

	




}
