package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import models.Board;
import models.CardsStatusInGame;
import models.Field;
import models.Game;
import models.GameStatus;
import models.InterrupterMessage;
import models.PictureTools;
import models.Player;
import models.PlayersManager;

public class GameBoardPanel extends JPanel implements MouseListener{

	private Board board;
	private int widthBoard = 4, heightBoard = 4;
	private PictureTools pictureTools;
	private PlayersManager playersManager = new PlayersManager();
	private Game game;
	private Field choosenField;
	private InterrupterMessage interrupterMessage;
	private GameStatus gameStatus;
	
	
	public GameBoardPanel() {

		
		this.setOpaque(false);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.setBounds(305, 62, 424, 412);
		this.setBackground(new Color(0, 0, 0, 125));
		this.setLayout(null);
		
		gameStatus = new GameStatus();
		interrupterMessage = new InterrupterMessage();
		pictureTools = new PictureTools();
		board = new Board(widthBoard, heightBoard);
		
		game = new Game(board);
		
		GenerateBoard();
		addMouseListenerToBoardField();
	}
	
	private void GenerateBoard() {
		board.generateFieldOnBoard(this.getWidth(), this.getHeight());
		drawBoard();
	}
	
	private void drawBoard() {
		for(int i=0; i<board.getHeight(); i++) {
			for(int j=0; j<board.getWidth();j++) {
				this.add(board.getFieldOnBoard()[i][j]);
			}
		}
	}
	
	private void addMouseListenerToBoardField() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				board.getFieldOnBoard()[i][j].addMouseListener(this);
			}
		}
	}
	
	public void repaintMainBoard(Field[][] copyField)
	{
		clearGameBoardPanel();
		
		this.repaint();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {

				if (copyField[i][j] != null) {

					if (copyField[i][j].getCardOnField() != null) {
						copyField[i][j].setLblAttribute(copyField[i][j].getCardOnField().getDescribe());
					}
					if (copyField[i][j].isAvailable() == true) {
						if (copyField[i][j].getCardOnField() != null) {
							copyField[i][j].setIcon(null);
							copyField[i][j].setBorder(BorderFactory.createEmptyBorder());
							copyField[i][j].getLblAttribute().setText("");
						}
					}
					this.add(copyField[i][j]);
				}
			}
		}
		this.repaint();
	}
	
	public void clearGameBoardPanel() {
		for(Component component:this.getComponents())
		{
			if(component instanceof Field)
			{
				this.remove(component);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object source = e.getSource();
		
		if(isPressedFieldOnBoard(source)) {
			if(choosenField.isAvailable()) {
				fieldOnBoardIsPressed();
			}
		}
	}
	
	public boolean isPressedFieldOnBoard(Object source) {
		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
				if (source == board.getFieldOnBoard()[i][j]) {
					choosenField = board.getFieldOnBoard()[i][j];
					return true;
				}
			}
		}
		return false;
	}
	
	public void fieldOnBoardIsPressed() {
		Player currentPlayer = playersManager.getSelectedPlayers().get(gameStatus.getCurrentPlayerTurnID());
		
		if(checkChoosenCardAndAvailableDrop()) {
			setCardOnChoosenField();
			
			CardsStatusInGame.getSelectedPlayerCard().setBorder(BorderFactory.createLineBorder(Color.RED, 0));
			CardsStatusInGame.getSelectedPlayerCard().setVisible(false);

			currentPlayer.RemoveCardFromUser(CardsStatusInGame.getSelectedPlayerCardId());
			currentPlayer.GenerateRandomCard();

			CardsStatusInGame.setSelectedPlayerCard(null);

//			btnNextTurn.setEnabled(true);
			CardsStatusInGame.setCardDropped(true);
			if (isAttackCard()) {
				gameStatus.setBattleStart(true);
				game.setCoordinateBattleCard(choosenField.get_X(), choosenField.get_Y());
			}
			if(board.isFull()) {
				gameStatus.setBattleStart(true);
			}
			if (gameStatus.isBattleStart()) {
				JOptionPane.showMessageDialog(null, "The battle starts now!");
				repaintMainBoard(game.battle());
				gameStatus.setBattleStart(false);	
			}
		}

		/*} 
		else if (!btnNextTurn.isEnabled())
		{
			JOptionPane.showMessageDialog(null, "The battle starts now!");
			game.battle();
			game.setBattleStart(false);
		}
		else 	JOptionPane.showMessageDialog(null, "Your turn passed!");*/
	}
	
	public boolean checkChoosenCardAndAvailableDrop() {
		if (CardsStatusInGame.getSelectedPlayerCard() == null) {
			interrupterMessage.showMessage_NotChoosenCard();
			return false;
		}
		else if (CardsStatusInGame.isCardDropped()) {
			return false;
		}
		else return true;
	}
	
	public void setCardOnChoosenField() {
		Player currentPlayer = playersManager.getSelectedPlayers().get(gameStatus.getCurrentPlayerTurnID());
		choosenField.setIcon(pictureTools.scaleImageAndConvertToIcon(pictureTools.iconToImage(CardsStatusInGame.getSelectedPlayerCard().getIcon()), 70, 100));
		choosenField.setAvailable(false);
		choosenField.setCardOnField(currentPlayer.GetUserCards()
				.get(CardsStatusInGame.getSelectedPlayerCardId()));
		choosenField.setColorField(currentPlayer.getColor());
		choosenField.getLblAttribute().setBounds(75, 5, 50, 60);
		choosenField.getLblAttribute().setLayout(new FlowLayout());
		choosenField.setLblAttribute(choosenField.getCardOnField().getDescribe());
		setPositionCard();
	}
	
	public void setPositionCard() {
		choosenField.getCardOnField().setPosX(choosenField.get_X());
		choosenField.getCardOnField().setPosY(choosenField.get_Y());
	}
	
	public boolean isAttackCard() {
		String attackString = "Attack";
		if (attackString.equals(choosenField.getCardOnField().getCardType())){
			return true;
		}
		return false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
