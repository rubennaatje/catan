package model;

public class VictoryPointCard extends DevelopmentCard { //deze kaart geeft 1 victory point
	
	public VictoryPointCard(PlayerUser player, String kaartid, String kaarttype, String kaartnaam) {
		super(player, kaartid, kaarttype, kaartnaam);
	}
	
	public void playCard() {
		super.updateDatabase(); //ontwikkelingspunten worden geteld zodra deze kaart gespeeld is
		player.refresh();
	}
	
}
