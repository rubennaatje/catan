package model;

/*Responsibility:
	-Store card
*/
public class Card {
	int id;
	String naam;
	String type;
	String uitleg;
	boolean gespeeld;

	public Card(int id, String naam, String type, String uitleg, boolean gespeeld) {
		this.id = id;
		this.naam = naam;
		this.type = type;
		this.uitleg = uitleg;
		this.gespeeld = gespeeld;
	}

	public int getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public String getType() {
		return type;
	}

	public String getUitleg() {
		return uitleg;
	}

	public boolean isGespeeld() {
		return gespeeld;
	}
}
