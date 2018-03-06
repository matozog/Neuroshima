package models;

/**
 * Abstract class of a card.
 */
public abstract class Card {
	/**
	 * A type of a card. Must be one of: "Soldier", "MachineGun", "Berserker", "Sniper" or "Battle".
	 */
	private String cardType;
	/**
	 * Vertical position of the card on a board.
	 */
	private int posX;
	/**
	 * Horizontal position of the card on a board.
	 */
	private int posY;
	/**
	 * Direction that the card is facing. Must be one of: "N" - north (up), "W" - west (right), "S" - south (down), "E" - east (left)
	 */
	private char faces;
	/**
	 * Health points of a card;
	 */
	private int health;
	/**
	 * Damage points of a card;
	 */
	private int damage;
	/**
	 * Initiative points of a card;
	 */
	private int initiative;
	// TODO: Uncomment after User class is implemented.
	/*
	private User owner;

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	*/

	public Card(){}

	public Card(String cardType){
		this.cardType = cardType;
	}

	public Card(String cardType, int posX, int posY, char faces, int health, int damage, int initiative){
		this.cardType = cardType;
		this.posX = posX;
		this.posY = posY;
		this.faces = faces;
		this.health = health;
		this.damage = damage;
		this.initiative = initiative;
	}

	public String getCardType() {
		return cardType;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public char getFaces() {
		return faces;
	}

	public void setFaces(char faces) {
		this.faces = faces;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getInitiative() {
		return initiative;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public void decreaseHealth(int amount){
		health -= amount;
	}
}
