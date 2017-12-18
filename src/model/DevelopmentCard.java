package model;

import java.sql.SQLException;

import controller.DatabaseManager;
import controller.GameController;

public class DevelopmentCard {

	protected String spelid;
	protected String cardId;
	protected String username;
	protected String kaarttype;
	protected String kaartnaam;
	protected PlayerUser player;

	public DevelopmentCard(PlayerUser player, String cardId, String kaarttype, String kaartnaam) {
		this.spelid = player.getSpelId();
		this.cardId = cardId;
		this.username = player.getUsername();
		this.kaarttype = kaarttype;
		this.kaartnaam = kaartnaam;
		this.player = player;
		try {
			DatabaseManager.createStatement().executeUpdate("UPDATE spelerontwikkelingskaart SET username = '"
					+ this.username + "' WHERE idspel = '" + spelid + "'AND idontwikkelingskaart = '" + cardId + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getCardname() {
		return kaartnaam;

	}

	protected void updateDatabase() {
		try {
			DatabaseManager.createStatement()
					.executeUpdate("UPDATE `spelerontwikkelingskaart` SET gespeeld = 1 "
							+ "WHERE idontwikkelingskaart = '" + this.cardId + "' AND `idspel` ='" + player.getSpelId()
							+ "' AND username = '" + player.getUsername() + "';");
			System.out.println("query");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getCardId() {
		return cardId;
	}
}
