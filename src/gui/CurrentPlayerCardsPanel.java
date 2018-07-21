package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import models.Board;
import models.Card;
import models.CardsStatusInGame;
import models.Field;
import models.Game;
import models.GameStatus;
import models.ImageCardLoader;
import models.PictureTools;
import models.Player;
import models.PlayersManager;

public class CurrentPlayerCardsPanel extends JPanel implements ActionListener, MouseListener{
	
	private ArrayList<JLabel> currentPlayerCards = new ArrayList<JLabel>();
	private JButton btnNextTurn, btnShowCards;
	private JLabel playerCard;
	private PlayersManager playersManager = new PlayersManager();
	private ImageCardLoader imageCardLoader;
	private PictureTools pictureTools;
	private NextTurnPanel panelNextTurn;
	private CardsStatusInGame cardsStatusInGame;
	private GameStatus gameStatus = new GameStatus();
	
	public CurrentPlayerCardsPanel(NextTurnPanel panelNextTurn) {
		this.setOpaque(false);
		this.setBorder(new TitledBorder(null, "<html><font color=white>Your cards</font></html>",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setBounds(305, 519, 424, 204);
		this.setLayout(null);

		this.panelNextTurn = panelNextTurn;
		imageCardLoader = new ImageCardLoader();
		pictureTools = new PictureTools();
		cardsStatusInGame = new CardsStatusInGame();
		
		btnNextTurn = new JButton("Next Turn");
		btnNextTurn.addActionListener(this);
		btnNextTurn.setBounds(263, 158, 149, 33);
		btnNextTurn.setEnabled(false);
		this.add(btnNextTurn);

		btnShowCards = new JButton("Show cards");
		btnShowCards.setBounds(12, 158, 149, 33);
		btnShowCards.addActionListener(this);
		this.add(btnShowCards);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == btnNextTurn) {
			gameStatus.changePlayerTurn();
			panelNextTurn.setNextTurnPlayerNumber(gameStatus.getCurrentPlayerTurnID());
			clearPanelCurrentPlayerCards();
			btnNextTurn.setEnabled(false);
			cardsStatusInGame.setCardDropped(false);
			btnShowCards.setEnabled(true);
		} else if (source == btnShowCards) {
			generateCurrentPlayerCards();
			btnShowCards.setEnabled(false);
			btnNextTurn.setEnabled(true);
		}
	}
	
	private void clearPanelCurrentPlayerCards() {
		for (int i = this.getComponentCount() - 1; i >= 0; i--) {
			if (this.getComponent(i) instanceof JLabel) {
				this.remove(i);
			}
		}
		this.repaint();
	}
	
	public void generateCurrentPlayerCards() {
		Image currentCard = null;
		int startXPosition = 22;
		Player currentPlayer = playersManager.getSelectedPlayers().get(gameStatus.getCurrentPlayerTurnID());
		currentPlayerCards.clear();
		clearPanelCurrentPlayerCards();
		
		for (Card card : currentPlayer.GetUserCards()) {	
			currentCard = getImageFromCard(card);
			setPropertiesOnCardLabel(currentCard, startXPosition);
			setDescribeOnCardLabel(card);
			
			this.setBackground(new Color(0, 0, 0, 125));
			currentPlayerCards.add(playerCard);
			this.add(playerCard);
			startXPosition += 140;
		}
		panelNextTurn.setNextTurnPlayerNumber(gameStatus.getCurrentPlayerTurnID());
		this.repaint();
	}
	
	public Image getImageFromCard(Card card) {
		Image currentCard = null;
		if (card.getCardType().equals("Berserker")) {
			currentCard = imageCardLoader.getImageBerserker();
		} else if (card.getCardType().equals("MachineGun")) {
			currentCard = imageCardLoader.getImageMachineGun();
		} else if (card.getCardType().equals("Soldier")) {
			currentCard = imageCardLoader.getImageSoldier();
		} else if (card.getCardType().equals("Attack")) {
			currentCard = imageCardLoader.getImageBattle();
		}
		return currentCard;
	}
	
	public void setPropertiesOnCardLabel(Image currentCard, int startXPosition) {
		playerCard = new JLabel();
		playerCard.setIcon(pictureTools.scaleImageAndConvertToIcon(currentCard, 80, 130));
		playerCard.setBounds(startXPosition, 20, 110, 130);
		playerCard.addMouseListener(this);
	}
	
	public void setDescribeOnCardLabel(Card card) {
		JLabel describe = new JLabel(card.getDescribe());
		describe.setBounds(85, 5, 50, 60);
		describe.setLayout(new FlowLayout());
		playerCard.add(describe);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Object source = e.getSource();
		Player currentPlayer = playersManager.getSelectedPlayers().get(gameStatus.getCurrentPlayerTurnID());
		boolean isCard1 = ((currentPlayerCards.size() >= 1) ? true : false);
		boolean isCard2 = ((currentPlayerCards.size() >= 2) ? true : false);
		boolean isCard3 = ((currentPlayerCards.size() >= 3) ? true : false);

		if (isCard1 && source == currentPlayerCards.get(0)) {
			if (isCard1)
				markBorderCardInPlayerColor(currentPlayerCards.get(0), currentPlayer);
			if (isCard2)
				markBorderCardInRed(currentPlayerCards.get(1));
			if (isCard3)
				markBorderCardInRed(currentPlayerCards.get(2));
			changeCardsStatus(0);
		} else if (isCard2 && source == currentPlayerCards.get(1)) {
			if (isCard1)
				markBorderCardInRed(currentPlayerCards.get(0));
			if (isCard2)
				markBorderCardInPlayerColor(currentPlayerCards.get(1), currentPlayer);
			if (isCard3)
				markBorderCardInRed(currentPlayerCards.get(2));
			changeCardsStatus(1);
		} else if (isCard3 && source == currentPlayerCards.get(2)) {
			if (isCard1)
				markBorderCardInRed(currentPlayerCards.get(0));
			if (isCard2)
				markBorderCardInRed(currentPlayerCards.get(1));
			if (isCard3)
				markBorderCardInPlayerColor(currentPlayerCards.get(2), currentPlayer);
			changeCardsStatus(2);
		}
	}
	
	public void markBorderCardInRed(JLabel card) {
		card.setBorder(BorderFactory.createLineBorder(Color.RED, 0));
	}
	
	public void markBorderCardInPlayerColor(JLabel card, Player currentPlayer) {
		card.setBorder(BorderFactory.createLineBorder(currentPlayer.getColor(), 3));
	}
	
	public void changeCardsStatus(int selectedCard) {
		cardsStatusInGame.setSelectedPlayerCard(currentPlayerCards.get(selectedCard));
		cardsStatusInGame.setSelectedPlayerCardId(selectedCard);
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
