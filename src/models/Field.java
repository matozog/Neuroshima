package models;

import javax.swing.JLabel;

public class Field extends JLabel {

	private int width, height;
	private boolean available=true;
	private Card cardOnField=null;
	
	
	public Field(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	/**
	 * 
	 * @return available field
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * 
	 * @param available - set available field
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	
	public Card getCardOnField() {
		return cardOnField;
	}


	public void setCardOnField(Card cardOnField) {
		this.cardOnField = cardOnField;
	}
}
