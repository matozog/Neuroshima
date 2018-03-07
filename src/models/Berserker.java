package models;

import java.util.ArrayList;

public class Berserker extends Card {

	public Berserker(){
		super();
	}

	public Berserker(String cardType, int posX, int posY, char faces, int health, int damage, int initiative){
		super("Berserker", posX, posY, faces, health, damage, initiative);
	}

	@Override
	public ArrayList<Pair<Integer, Integer>> getAttacks() {
		// attacks everybody around
		ArrayList<Pair<Integer, Integer>> attacks = new ArrayList<>();

		attacks.add(new Pair<Integer, Integer>(posX, posY-1));
		attacks.add(new Pair<Integer, Integer>(posX+1, posY-1));
		attacks.add(new Pair<Integer, Integer>(posX+1, posY));
		attacks.add(new Pair<Integer, Integer>(posX+1, posY+1));
		attacks.add(new Pair<Integer, Integer>(posX, posY+1));
		attacks.add(new Pair<Integer, Integer>(posX-1, posY+1));
		attacks.add(new Pair<Integer, Integer>(posX-1, posY));
		attacks.add(new Pair<Integer, Integer>(posX-1, posY-1));

		return attacks;
	}

}
