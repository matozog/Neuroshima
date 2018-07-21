package models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Players")
public class PlayersManager {
	
	@XmlElement (name = "Player")
	private static List<Player> allPlayers = new ArrayList<Player>();
	
	private static ArrayList<Player> selectedPlayersToGame = new ArrayList<Player>();
	
	private static PlayersManager playersManager;
	private InterrupterMessage interrupter;
	private boolean createPlayer = true;
	private static int currentPlayerTurnId = 0;
	
	public int getCurrentPlayerTurnId() {
		return currentPlayerTurnId;
	}

	public void setCurrentPlayerTurnId(int currentPlayerTurnId) {
		PlayersManager.currentPlayerTurnId = currentPlayerTurnId;
	}

	public List<Player> getAllPlayers() {
		return allPlayers;
	}
	
	public ArrayList<Player> getSelectedPlayers() {
		return selectedPlayersToGame;
	}
	
	public PlayersManager() {
		interrupter = new InterrupterMessage();
	}
	
	public void tryAddPlayerWithNickToList(String nickname) {
		if(isUniqueNickname(nickname)){
			addNewUserToDatabase(nickname);
			addUserToGame(nickname);
		}
		else {
			interrupter.showMessage_NicknameIsOnListAllPlayers();
		}
	}
	
	public void tryAddPlayerFromDatabaseToList(int indexPlayerOnList) {
		String nickname = allPlayers.get(indexPlayerOnList).getName();
		if(!wasPlayerSelectedEarlier(nickname)) {
			addUserToGame(nickname);
		}
		else {
			interrupter.showMessage_NicknameIsOnListSelectedPlayers();
		}
	}
	
	public boolean isUniqueNickname(String nickname) {
		for (Player player : allPlayers) {
			if (nickname.equals(player.getName())) {
				return false;
			}
		}
		return true;
	}
	
	public boolean wasPlayerSelectedEarlier(String nickname) {
		for (Player player : selectedPlayersToGame) {
			if (player.getName().equals(nickname)) {
				return true;
			}
		}
		return false;
	}
	
	public void addNewUserToDatabase(String nickname) {
		Player newPlayer = new Player(allPlayers.size()+1,nickname,0);
		allPlayers.add(newPlayer);
		marshall();
	}
	
	public void addUserToGame(String nickname) {
		selectedPlayersToGame.add(findPlayerByNickname(nickname));
	}
	
	public Player findPlayerByNickname(String nickname) {
		for (Player player : allPlayers) {
			if (player.getName().equals(nickname)) {
				return player;
			}
		}
		return null;
	}
	
	public boolean checkMaxAmountsPlayersToStartGame() {
		return (selectedPlayersToGame.size()<=4)? true:false;
	}
	
	public boolean checkMinAmountsPlayersToStartGame() {
		if(selectedPlayersToGame.size()>1) return true;	
		else return false;
	}
	
	/**
	 * Save allPlayers to xml file
	 * 
	 */
	public void marshall()
	{
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(PlayersManager.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(this, new File("DataPlayers.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read from xml file to allPlayers
	 */
	public void unmarshal()
	{
		try {			
			JAXBContext jabx = JAXBContext.newInstance(PlayersManager.class);
			Unmarshaller unmarsh = jabx.createUnmarshaller();
			File fileToRead = new File("DataPlayers.xml");
			if(fileToRead.canExecute())
				playersManager = (PlayersManager) unmarsh.unmarshal(fileToRead);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
