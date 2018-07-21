package models;

import java.util.ArrayList;

public class Game {

	private int maximumInitiative = 0, battleCardPositionX, battleCardPositionY;
	
	private Board board;
	private GameStatus gameStatus;

	public Game(Board board) {
		gameStatus = new GameStatus();
		this.board = board;
	}
	
	public Field[][] battle() {
		Pair<Integer,Integer> coordsCardWithMaxInitiative;
		Card cardOnField;
		Card cardToAttack;
		
		ArrayList<Pair<Integer, Integer>> attacksCoords = new ArrayList<>();
		Field[][] copyField = new Field[board.getHeight()][board.getWidth()];
		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
				copyField[i][j] = board.getFieldOnBoard()[i][j];
			}
		}
		copyField[battleCardPositionX][battleCardPositionY].setAvailable(true);
		
		coordsCardWithMaxInitiative = board.findMaximum();
		while(board.getMaximumInitiative() > 0) {
			cardOnField = board.getFieldOnBoard()[coordsCardWithMaxInitiative.getLeft()][coordsCardWithMaxInitiative.getRight()].getCardOnField();
			attacksCoords = cardOnField.getAttacks();
			
			//attack cards 
			
			for(Pair<Integer, Integer> pair:attacksCoords) {
				cardToAttack = board.getFieldOnBoard()[pair.getRight()][pair.getLeft()].getCardOnField();
				if(cardToAttack!=null) {
					if(cardOnField.getOwner() != cardToAttack.getOwner()) {
						cardToAttack.decreaseHealth(cardOnField.getDamage());
						System.out.print("");
					}
				}
			}
			
			//set card was used
			cardOnField.setAttackedInThisTour(true);
			
			for(Pair<Integer,Integer> coords : attacksCoords) {
				System.out.print(coords.toString() + "\n");
			}
			board.removeDeathCards(copyField);
			coordsCardWithMaxInitiative = board.findMaximum();
		}
		board.removeDeathCards(copyField);
		
		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
				board.getFieldOnBoard()[i][j] = copyField[i][j];
			}
		}
		return copyField;
//		repaintMainBoard(copyField);
	}
	
	public void setCoordinateBattleCard(int X, int Y) {
		this.battleCardPositionX = X;
		this.battleCardPositionY = Y;
	}
	
	public int getBattleX() {
		return battleCardPositionX;
	}

	public void setBattleX(int battleX) {
		this.battleCardPositionX = battleX;
	}

	public int getBattleY() {
		return battleCardPositionY;
	}

	public void setBattleY(int battleY) {
		this.battleCardPositionY = battleY;
	}
	
}
