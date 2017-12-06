package model;

public class VictoryPointCard extends DevelopmentCard {
	private String kaartnaam;
	public VictoryPointCard(String spelid, String kaartid, String username, String kaarttype, String kaartnaam) {
		super(spelid, kaartid, username, kaarttype);
		this.kaartnaam = kaartnaam;
		// TODO Auto-generated constructor stub
	}
	
	public String getKaartnaam() {
		return kaartnaam;
		
	}
}
