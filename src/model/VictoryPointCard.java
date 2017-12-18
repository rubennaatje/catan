package model;

public class VictoryPointCard extends DevelopmentCard { //deze kaart geeft 1 victory point
	
	public VictoryPointCard(PlayerUser player, String kaartid, String kaarttype, String kaartnaam) {
		super(player, kaartid, kaarttype, kaartnaam);
		// TODO Auto-generated constructor stub
	}
	
	public void playCard() {
		System.out.println("ontwikkeling");
		super.updateDatabase(); //ontwikkelingspunten worden geteld zodra deze kaart gespeeld is
	}
	
}
