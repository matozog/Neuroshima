package models;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {

	private int battleCardPositionX, battleCardPositionY;
	private Board board;
	private GameStatus gameStatus;
	private Pair<Integer, Integer> coordsCardWithMaxInitiative;
	private Card cardOnField;
	private Card cardToAttack;
	private ArrayList<Pair<Integer, Integer>> attacksCoords = new ArrayList<>();
	private Field[][] copyField;

	public Game(Board board) {
		gameStatus = new GameStatus();
		this.board = board;
		copyField = new Field[board.getHeight()][board.getWidth()];
	}

	public Field[][] battle() {

		board.setAvailableFieldOnTrue(battleCardPositionX, battleCardPositionY);

		coordsCardWithMaxInitiative = board.findMaximum();
		while (board.getMaximumInitiative() > 0) {
			cardOnField = board.getCardOnField(coordsCardWithMaxInitiative.getRight(), coordsCardWithMaxInitiative
					.getLeft());
			attacksCoords = cardOnField.getAttacks();

			// attack cards

			for (Pair<Integer, Integer> pair : attacksCoords) {
				cardToAttack = board.getCardOnField(pair.getRight(), pair.getLeft());
				if (cardToAttackIsNotNullAndItIsEnemy()) {
					if (!cardToAttack.isDeath()) {
						cardToAttack.decreaseHealth(cardOnField.getDamage());
						if (checkEnemyIsDeath()) {
							addPointToPlayer(cardOnField.getOwner());
						}
					}
				}
			}

			// set card was used
			cardOnField.setAttackedInThisTour(true);

			board.removeDeathCards(copyField);
			coordsCardWithMaxInitiative = board.findMaximum();
		}
		board.removeDeathCards(copyField);
		board.setAllCardsAttackedOn(false);

		return board.getFieldOnBoard();
	}

	public void addPointToPlayer(Player player) {
		player.addPoints(1);
		refreshScoreLabelFor(player);
	}
	
	public void refreshScoreLabelFor(Player player) {
		player.getPlayerPanel().setTextOnScoreLabel(Integer.toString(player.getScore()));
	}

	public boolean checkEnemyIsDeath() {
		if (cardToAttack.getHealth() <= 0 && !cardToAttack.cardType.equals("Attack")) {
			return true;
		}
		return false;
	}

	public boolean cardToAttackIsNotNullAndItIsEnemy() {
		if (cardToAttack != null) {
			if (cardOnField.getOwner() != cardToAttack.getOwner()) {
				return true;
			}
		}
		return false;
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
