package model;

public class ProgressCard extends DevelopmentCard {
	private String uitleg;
	public ProgressCard(String spelid, String kaartid, String username, String kaarttype, String kaartnaam, String uitleg) {
		super(spelid, kaartid, username, kaarttype, kaartnaam);
		this.uitleg = uitleg;
		// TODO Auto-generated constructor stub
	}
	
	public String getUitleg() {
		return uitleg;
	}
}
