package models;

import javax.swing.JLabel;

public class CardsStatusInGame {

	private static boolean cardDropped = false;
	private static int selectedPlayerCardId = -1;
	private static JLabel selectedPlayerCard = null;
	
	public static boolean isCardDropped() {
		return cardDropped;
	}
	public static void setCardDropped(boolean cardDropped) {
		CardsStatusInGame.cardDropped = cardDropped;
	}
	public static int getSelectedPlayerCardId() {
		return selectedPlayerCardId;
	}
	public static void setSelectedPlayerCardId(int selectedPlayerCardId) {
		CardsStatusInGame.selectedPlayerCardId = selectedPlayerCardId;
	}
	public static JLabel getSelectedPlayerCard() {
		return selectedPlayerCard;
	}
	public static void setSelectedPlayerCard(JLabel selectedPlayerCard) {
		CardsStatusInGame.selectedPlayerCard = selectedPlayerCard;
	}
	
	
}
