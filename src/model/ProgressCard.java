package model;

public class ProgressCard extends DevelopmentCard {
	private String kaartnaam;
	private String uitleg;
	public ProgressCard(String spelid, String kaartid, String username, String kaarttype, String kaartnaam, String uitleg) {
		super(spelid, kaartid, username, kaarttype);
		this.kaartnaam = kaartnaam;
		this.uitleg = uitleg;
		// TODO Auto-generated constructor stub
	}
	
	public String getKaartnaam() {
		return kaartnaam;
	}
	
	public String getUitleg() {
		return uitleg;
	}
}
