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
	
	private DevelopCardController control;
	
	
	

	
	public CardView(Stage primaryStage) throws Exception
	{
		super(CardView.class.getResource("fxml/CardView.fxml"), primaryStage);
		
		DatabaseManager.connect();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DatabaseManager.disconnect();
			System.out.println("databasemanager shut down");
		}));
		
		PlayerUser player = new PlayerUser("bart", "770");
		control = new DevelopCardController("770");
		
		control.refreshDevCards("bart");
	
		
		
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
		cards.add(""); //kaart nummer 0
		for(int x = 0; x < control.getPlayerCards().size(); x++) {
			cards.add(control.getPlayerCards().get(x).getCardname());
		}
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
		if (cards.get(selectedCard).equals("ridder"))
		{
			imageview.setImage(ridder);
		} else if (cards.get(selectedCard).equals("bibliotheek"))
		{
			imageview.setImage(bibliotheek);
		
		} else if (cards.get(selectedCard).equals("kathedraal"))
		{
			imageview.setImage(kathedraal);
		} else if (cards.get(selectedCard).equals("markt"))
		{
			imageview.setImage(markt);
		} else if (cards.get(selectedCard).equals("monopolie"))
		{
			imageview.setImage(monopolie);
		} else if (cards.get(selectedCard).equals("stratenbouw"))
		{
			imageview.setImage(stratenbouw);
		} else if (cards.get(selectedCard).equals("uitvinding"))
		{
			imageview.setImage(uitvinding);
		} else if (cards.get(selectedCard).equals("universiteit"))
		{
			imageview.setImage(universiteit);
		}
		else if (cards.get(selectedCard).equals("parlement"))
		{
			imageview.setImage(parlement);
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
