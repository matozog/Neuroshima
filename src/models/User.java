package models;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Abstract class of a player.
 */
@XmlRootElement(name = "Player")
@XmlAccessorType(XmlAccessType.FIELD)
final public class User {
	/**
	 * An id of Player.
	 */
	@XmlElement(name = "ID")
	private int id;
	/**
	 * A nickname of Player.
	 */
	@XmlElement(name = "Name")
	private String name;
	/**
	 * Player ranking
	 */
	@XmlElement(name = "Rank")
	private int rank;
	/**
	 * Sum of Player's points.
	 */
	@XmlElement(name = "Score")
	private int score;
	/**
	 * Player's color on the board.
	 */
	private String color;

	/**
	 * Player's deck of Cards.
	 */
	private ArrayList<Card> userCards = new ArrayList<Card>();

	public User() {
	};

	/**
	 * @param id
	 * @param name
	 * @param score
	 */
	public User(int id, String name, int score) {
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
	 * @param id
	 *            the id to set
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
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Removes card from deck
	 * 
	 * @param cardId
	 */
	public void RemoveCardFromDeck(int cardId) {
		if (userCards.get(cardId) != null) {
			userCards.remove(cardId);
		}
	}

	public void GenerateRandomCard() {
		if (userCards.size() < 3) {
			Card card;
			int rand = (int) (Math.random() * 10) % 3;
			//0 - Berseker, 1 - MachineGun, 2-Soldier
			switch (rand) {
			case 0:
				card = new Berserker();
				userCards.add(card);
				break;
			case 1:
				card = new MachineGun();
				userCards.add(card);
				break;
			case 2:
				card = new Soldier();
				userCards.add(card);
				break;
			}
		}
	}
	
	public ArrayList<Card> GetUserCards(){
		return userCards;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Play card on board.
	 */
	public void attack(Card card, int posX, int posY) {
		// TODO Add missing attributes
		card.setPosX(posX);
		card.setPosY(posY);

	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", score=" + score + ", color=" + color + "]";
	}

}