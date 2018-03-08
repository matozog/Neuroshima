package models;

import java.util.ArrayList;

/**
 * Abstract class of a player.
 */
final public class User {
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

	public User() {};

	/**
	 * @param id
	 * @param name
	 * @param score
	 */
	public User(int id, String name, String score) {
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
	 * Play card on board.
	 */
	public void attack(Card card, int posX, int posY) {
		//TODO Add missing attributes 
		card.setPosX(posX);
		card.setPosY(posY);

	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", score=" + score + ", color=" + color + "]";
	}


}