package models;

import java.util.ArrayList;

public class Deck { 
	private ArrayList<Card> cardDeck = new ArrayList<Card>();
	private int countBerserkerCards = 5;
	private int countMachineGunCards = 5;
	private int countSoldierCards = 5;
	
	Deck(){
		GenerateBerserkerDeck();
		GenerateMachineGunDeck();
		GenerateSoldierCardsDeck(); 
	}
	
	private void GenerateBerserkerDeck() { 
		for(int i=0;i<countBerserkerCards;i++) {
			cardDeck.add(new Berserker(-1, -1, 'N', 100, 10, 5)); 
		}
	}
	
	private void GenerateMachineGunDeck() { 
		for(int i=0;i<countMachineGunCards;i++) {
			cardDeck.add(new MachineGun(-1, -1, 'N', 100, 10, 5)); 
		}
	}
	
	private void GenerateSoldierCardsDeck() { 
		for(int i=0;i<countSoldierCards;i++) {
			cardDeck.add(new Soldier(-1, -1, 'N', 100, 10, 5)); 
		}
	}
}
