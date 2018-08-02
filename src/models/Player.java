package models;

import java.awt.Color;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import gui.PlayerPanel;

/**
 * Abstract class of a player.
 */
@XmlRootElement(name = "Player")
@XmlAccessorType(XmlAccessType.FIELD)
final public class Player {
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
	private Color color;
	/**
	 * Player's panel with score and reverse cards
	 */
	@XmlTransient
	private PlayerPanel panelWithScore;
	/**
	 * Player's deck of Cards.
	 */
	private ArrayList<Card> userCards = new ArrayList<Card>();
	
	

	public Player() {
	};

	/**
	 * @param id
	 * @param name
	 * @param score
	 */
	public Player(int id, String name, int score) {
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
	public void RemoveCardFromUser(int cardId) {
		if (userCards.get(cardId) != null) { 
			userCards.remove(cardId);
		}
	}

	public void GenerateRandomCard() {
		if (userCards.size() < 3) {
			Card c = Deck.GetRandomCard();
			// Deck is empty
			if(c != null) {
				userCards.add(c);
				c.setOwner(this);
				Deck.RemoveCardFromDeck(c); 
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
	public Color getColor() {
		return color;
	}

	/**
	 * @param orange
	 *            the color to set
	 */
	public void setColor(Color orange) {
		this.color = orange;
	}

	/**
	 * Play card on board.
	 */
	public void attack(Card card, int posX, int posY) {
		// TODO Add missing attributes
		card.setPosX(posX);
		card.setPosY(posY);

	}
	
	public void addPoints(int points) {
		this.score += points;
	}
	
	
	public void createPlayerPanel(int numberOfPlayers) {
		this.panelWithScore = new PlayerPanel(numberOfPlayers);
		this.panelWithScore.generateThreeCardReverse();
	}
	
	public PlayerPanel getPlayerPanel() {
		return this.panelWithScore;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", score=" + score + ", color=" + color + "]";
	}

}