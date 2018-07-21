package models;

public class GameStatus {

	private static boolean battleStart = false;
	private static int currentPlayerTurnId = 0;
	private PlayersManager playersManager;
	
	public GameStatus() {
		playersManager = new PlayersManager();
	}
	
	public void setCurrentPlayerTurnID(int playerID) {
		this.currentPlayerTurnId = playerID;
	}
	
	public int getCurrentPlayerTurnID() {
		return currentPlayerTurnId;
	}
	
	public void changePlayerTurn() {
		currentPlayerTurnId++;
		if (currentPlayerTurnId == playersManager.getSelectedPlayers().size())
			currentPlayerTurnId = 0;
	}
	
	public boolean isBattleStart() {
		return battleStart;
	}
	
	public void setBattleStart(boolean battleStatus) {
		this.battleStart = battleStatus;
	}
	
}
