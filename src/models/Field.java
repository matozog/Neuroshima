package models;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Field extends JLabel {

	private int width, height;
	private int X, Y;
	private boolean available=true;
	private Card cardOnField=null;
	protected JLabel lblAttribute;
	
	public Field(int width, int height, int X, int Y)
	{
		this.X=X;
		this.Y=Y;
		this.width = width;
		this.height = height;
		lblAttribute = new JLabel();
		this.add(lblAttribute);
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

	public void setLblAttribute(String text)
	{
		lblAttribute.setText(text);
	}
	
	public JLabel getLblAttribute()
	{
		return lblAttribute;
	}
	
	public Card getCardOnField() {
		return cardOnField;
	}


	public void setCardOnField(Card cardOnField) {
		this.cardOnField = cardOnField;
	}
	
	public int get_X() {
		return X;
	}
	
	public void set_X(int x) {
		X = x;
	}
	
	public int get_Y() {
		return Y;
	}
	
	public void set_Y(int y) {
		Y = y;
	}
	
	public void setColorField(Color color)
	{
		this.setBorder(BorderFactory.createLineBorder(color, 3));
	}
	
	@Override
	public String toString() {
		return "Field [available=" + available + ", cardOnField=" + cardOnField + "]";
	}
	
}
