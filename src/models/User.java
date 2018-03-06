package models;

import java.util.ArrayList;

/**
 * Abstract class of a player.
 */
public abstract class User {
	/**
	 * An id of Player.
	 */
	private int id;
	/**
	 * A nickname of Player.
	 */
	private String name;
	/**
	 * Sum of Player's points.
	 */
	private String score;	
	/**
	 * Player's color on the board.
	 */
	private String color;	
	/**
	 * Player's deck of Cards.
	 */
	private ArrayList<Card> cardsDeck;

	public User() {};

	/**
	 * @param id
	 * @param name
	 * @param score
	 */
	public User(int id, String name, String score) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}
	/**
	 * @return color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the cardsDeck
	 */
	public ArrayList<Card> getCardsDeck() {
		return cardsDeck;
	}

	/**
	 * @param cardsDeck the cardsDeck to set
	 */
	public void setCardsDeck(ArrayList<Card> cardsDeck) {
		this.cardsDeck = cardsDeck;
	}
	/**
	 * Play card on board.
	 */
	public void attack(Card card, int posX, int posY) {
		//TODO Add missing attributes 
		card.setPosX(posX);
		card.setPosY(posY);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardsDeck == null) ? 0 : cardsDeck.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (cardsDeck == null) {
			if (other.cardsDeck != null)
				return false;
		} else if (!cardsDeck.equals(other.cardsDeck))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", score=" + score + ", cardsDeck=" + cardsDeck + "]";
	}

}