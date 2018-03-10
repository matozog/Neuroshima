package models;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Deck { 
	static private ArrayList<Card> cardDeck = new ArrayList<Card>();
	private int countBerserkerCards = 10;
	private int countMachineGunCards = 10;
	private int countSoldierCards = 10;
 
	private int maxHPRange = 20;
	private int maxDamageRange = 10;
	private int maxInitiativeRange = 5;
	
	public Deck(){
		GenerateBerserkerDeck();
		GenerateMachineGunDeck();
		GenerateSoldierCardsDeck(); 
	}

	/**
	 * Generate random hp. Range from 1 to maxHPRange
	 */
	private int GetRandomHP() {
		return ((int)(Math.random() * 100) % maxHPRange) + 1;
	}

	/**
	 * Generate random damage. Range from 1 to maxDamageRange
	 */
	private int GetRandomDamage() {
		return ((int)(Math.random() * 100) % maxDamageRange) + 1;
	}

	/**
	 * Generate random initiative. Range from 1 to maxInitiativeRange
	 */
	private int GetRandomInitiative() {
		return ((int)(Math.random() * 100) % maxInitiativeRange) + 1;
	}

	/**
	 * Generate direction that the card is facing. Must be one of: "N" - north (up), "W" - west (right), "S" - south (down), "E" - east (left)
	 */
	private char GetRanedomFace() {
		char faces[] = {'N', 'W' ,'S' ,'E'};
		int randFace = (((int)Math.random() * 10) % 4);
		return faces[randFace];
	}

	/**
	 * Generate deck for Berserker card. It will generate 'countBerserkerCards' cards
	 */
	private void GenerateBerserkerDeck() { 
		for(int i=0;i<countBerserkerCards;i++) {
			cardDeck.add(new Berserker(-1, -1, GetRanedomFace(), GetRandomHP(), GetRandomDamage(), GetRandomInitiative())); 
		}
	}

	/**
	 * Generate deck for MachineGun card. It will generate 'countMachineGunCards' cards
	 */
	private void GenerateMachineGunDeck() { 
		for(int i=0;i<countMachineGunCards;i++) {
			cardDeck.add(new MachineGun(-1, -1, GetRanedomFace(), GetRandomHP(), GetRandomDamage(), GetRandomInitiative())); 
		}
	}

	/**
	 * Generate deck for Soldier card. It will generate 'countSoldierCards' cards
	 */
	private void GenerateSoldierCardsDeck() { 
		for(int i=0;i<countSoldierCards;i++) {
			cardDeck.add(new Soldier(-1, -1, GetRanedomFace(), GetRandomHP(), GetRandomDamage(), GetRandomInitiative())); 
		}
	}

	/**
	 * @return cardDeck
	 */
	public static ArrayList<Card> GetDeck(){
		return cardDeck;
	}
	
	/**
	 * @return random card from deck
	 */
	public static Card GetRandomCard() {		
		if(cardDeck.size() == 0) {
			JOptionPane.showMessageDialog(null, "Deck is empty!");
			return null;
		}
		int randCardId = ((int)(Math.random() * 100) % cardDeck.size()); 
		String tmp="";
		for(Card c:cardDeck) {
			tmp+= c.getCardType() + "\n";
		}
		JOptionPane.showMessageDialog(null,tmp);
		if(cardDeck.get(randCardId) != null) {
 
			return cardDeck.get(randCardId);
		}
		return null;
	}
	
	public static void RemoveCardFromDeck(Card card) {
		cardDeck.remove(card);
	}
}
