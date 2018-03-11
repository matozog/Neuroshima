package gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import models.Board;
import models.Card;
import models.Deck;
import models.Field;
import models.User;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

public class NeuroshimaApp implements ActionListener, MouseListener {

	private JFrame frame;
	private LogWindow logWindow;
	private JMenuItem mntmExit;
	private JMenu mnAbout;
	private JMenu mnOther;
	private JPanel panelGameMain;
	private JButton btnNextTurn;
	private ArrayList<JPanel> playersPanels = new ArrayList<JPanel>();
	private ArrayList<JLabel> playersLabel = new ArrayList<JLabel>();
	private ArrayList<JLabel> boardCells = new ArrayList<JLabel>();
	private ArrayList<JLabel> currentPlayerCards = new ArrayList<JLabel>();
	private JLabel lblNextTurn;
	private JPanel panelNextTurn;
	private JPanel panelYourCards;
	private JLabel playerCard1, playerCard2, playerCard3, selectedPlayerCard = null;
	private User currentPlayerTurn;
	private int currentPlayerTurnId = 0;
	private Board board;
	private boolean cardDropped = false;
	private boolean battleStart = false;
	private int selectedPlayerCardId = -1;
	private int widthBoard = 4, heightBoard = 4;
	private Deck deck;
	private JMenuItem mnItemAbout, mnItemHelp,mnNewGame,mnReset;

	/**
	 * Create the application.
	 */
	public NeuroshimaApp() {
		frame = new JFrame();
		logWindow = new LogWindow(this);
		frame.setResizable(false);
		frame.setBounds(100, 100, 1042, 795);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Neuroshima");
		frame.getContentPane().setLayout(null);
		Image backgorund;
		try {
			backgorund = ImageIO.read(getClass().getResource("/gui/images/neuroshima-hex-board.png"));
			logWindow.scaleImage(backgorund, 1042, 796);
			frame.setContentPane(new JLabel(logWindow.scaleImage(backgorund, 1042, 796)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		board = new Board(widthBoard, heightBoard);

		panelGameMain = new JPanel();
		panelGameMain.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelGameMain.setBounds(305, 62, 424, 412);
		panelGameMain.setBackground(new Color(0, 0, 0, 125));
		frame.getContentPane().add(panelGameMain);
		panelGameMain.setLayout(null);

		panelNextTurn = new JPanel();
		panelNextTurn.setBounds(305, 485, 424, 29);
		panelNextTurn.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelNextTurn.setLayout(null);
		frame.getContentPane().add(panelNextTurn);

		lblNextTurn = new JLabel("<html>Next turn: <b>Player1</b>");
		lblNextTurn.setHorizontalAlignment(SwingConstants.CENTER);
		lblNextTurn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNextTurn.setBounds(12, 0, 404, 24);
		panelNextTurn.add(lblNextTurn);

		panelYourCards = new JPanel();
		panelYourCards.setBorder(new TitledBorder(null, "Your cards", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelYourCards.setBounds(305, 519, 424, 204);
		frame.getContentPane().add(panelYourCards);
		panelYourCards.setLayout(null);

		btnNextTurn = new JButton("Next Turn");
		btnNextTurn.addActionListener(this);
		btnNextTurn.setBounds(263, 158, 149, 33);
		btnNextTurn.setEnabled(false);
		panelYourCards.add(btnNextTurn);
		
		JButton btnShowCards = new JButton("Show cards");
		btnShowCards.setBounds(12, 158, 149, 33);
		panelYourCards.add(btnShowCards);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);

		mnOther = new JMenu("Other");
		menuBar.add(mnOther);
		
		mnNewGame = new JMenuItem("New game");
		mnNewGame.addActionListener(this);
		mnGame.add(mnNewGame);

		mnReset = new JMenuItem("Reset");
		mnReset.addActionListener(this);
		mnGame.add(mnReset);

		mnItemHelp = new JMenuItem("Help");
		mnItemHelp.addActionListener(this);
		mnOther.add(mnItemHelp);
		
		mnItemAbout = new JMenuItem("About");
		mnItemAbout.addActionListener(this);
		mnOther.add(mnItemAbout);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(this);
		mnGame.add(mntmExit);

		frame.setVisible(false);

		logWindow.setVisible(true);

	}

	public void ShowMainFrame() {

		frame.setVisible(true);
		deck = new Deck();
		GeneratePlayersPanels(logWindow.getUsersList().size());
		GenerateBoard();
		GeneratePlayerCards();
		GenerateCurrentPlayerCards();
	}

	/**
	 * Generate player panels
	 * 
	 * @param playersCount
	 */
	public void GeneratePlayersPanels(int playersCount) {
		int playerPanelWidth = 295;
		int playerPanelHeight = 180;
		Rectangle bounds[] = new Rectangle[] { new Rectangle(3, 3, playerPanelWidth, playerPanelHeight),
				new Rectangle(733, 3, playerPanelWidth, playerPanelHeight),
				new Rectangle(3, 353, playerPanelWidth, playerPanelHeight),
				new Rectangle(733, 353, playerPanelWidth, playerPanelHeight) };
		if (playersCount < 2 || playersCount > 4) {
			JOptionPane.showMessageDialog(null, "Invalid number of players!");
			return;
		}

		for (int i = 0; i < playersCount; i++) {
			JPanel playerPanel = new JPanel();
			JLabel scoreLabel = new JLabel("Score: 0");
			scoreLabel.setForeground(Color.white);
			scoreLabel.setBounds(new Rectangle(8, 15, 100, 20));

			playerPanel.setLayout(null);

			playerPanel.add(scoreLabel);
			playerPanel.setBounds(bounds[i]);
			playerPanel.setBorder(BorderFactory.createTitledBorder(logWindow.getUsersList().get(i).getName()));
			playerPanel.setBackground(new Color(0, 0, 0, 125));
			try {
				Image img1 = ImageIO.read(getClass().getResource("/gui/images/card" + (i + 1) + ".png"));
				JLabel card1 = new JLabel();
				card1.setIcon(logWindow.scaleImage(img1, 90, 130));
				card1.setBounds(8, 40, 90, 130);
				playerPanel.add(card1);

				JLabel card2 = new JLabel();
				card2.setIcon(logWindow.scaleImage(img1, 90, 130));
				card2.setBounds(103, 40, 90, 130);
				playerPanel.add(card2);

				JLabel card3 = new JLabel();
				card3.setIcon(logWindow.scaleImage(img1, 90, 130));
				card3.setBounds(198, 40, 90, 130);
				playerPanel.add(card3);
			} catch (IOException e) {
				e.printStackTrace();
			}

			playersLabel.add(scoreLabel);
			playersPanels.add(playerPanel);
			frame.getContentPane().add(playerPanel);
		}
	}

	public void GenerateCurrentPlayerCards() {
		Image imgBerserker = null;
		Image imgMachineGun = null;
		Image imgSoldier = null;
		Image imgBattle = null;
		Image currentCard = null;
		try {
			imgBerserker = ImageIO.read(getClass().getResource("/gui/images/cardBerserker2.png"));
			imgMachineGun = ImageIO.read(getClass().getResource("/gui/images/cardMachineGun2.png"));
			imgSoldier = ImageIO.read(getClass().getResource("/gui/images/cardSoldier2.png"));
			imgBattle = ImageIO.read(getClass().getResource("/gui/images/cardBattle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		currentPlayerCards.clear();
		for (Component component : panelYourCards.getComponents()) {
			if (component instanceof JLabel) {
				panelYourCards.remove(component);
			}
		}
		int widthpos = 22;
		for (Card card : logWindow.getUsersList().get(currentPlayerTurnId).GetUserCards()) {
			playerCard1 = new JLabel();
			if (card.getCardType().equals("Berserker")) {
				currentCard = imgBerserker;
			} else if (card.getCardType().equals("MachineGun")) {
				currentCard = imgMachineGun;
			} else if (card.getCardType().equals("Soldier")) {
				currentCard = imgSoldier;
			} else if (card.getCardType().equals("Attack")) {
				currentCard = imgBattle;
			}
			playerCard1.setIcon(logWindow.scaleImage(currentCard, 80, 130));
			playerCard1.setBounds(widthpos, 20, 80, 130);
			playerCard1.addMouseListener(this);
			currentPlayerCards.add(playerCard1);
			panelYourCards.setBackground(new Color(0, 0, 0, 125));
			panelYourCards.add(playerCard1);
			widthpos += 150;
		}
		SetNextTurn(currentPlayerTurnId);
		panelYourCards.repaint();
	}

	public void GeneratePlayerCards() {
		currentPlayerTurn = logWindow.getUsersList().get(0); // default first player
		currentPlayerTurnId = 0;

		for (int i = 0; i < logWindow.getUsersList().size(); i++) {
			logWindow.getUsersList().get(i).GenerateRandomCard();
			logWindow.getUsersList().get(i).GenerateRandomCard();
			logWindow.getUsersList().get(i).GenerateRandomCard();
		}
	}

	public void SetNextTurn(int player) {
		lblNextTurn.setText("<html>Next turn: <b>" + logWindow.getUsersList().get(player).getName() + "</b>");
	}

	/**
	 * Generate game board
	 */
	public void GenerateBoard() {
		int width = panelGameMain.getWidth();
		int height = panelGameMain.getHeight();

		int cellWidth = width / 4;
		int cellHeight = height / 4;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Field field = new Field(cellWidth, cellHeight);
				field.setBounds(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
				field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				field.addMouseListener(this);
				board.getFieldOnBoard()[i][j] = field;
				panelGameMain.add(field);
			}
		}
	}

	/**
	 * Set player score in label
	 * 
	 * @param playerId
	 *            - player id indexing from 0 to 3
	 * @param score
	 *            - player overall score
	 */
	public void SetPlayerScore(int playerId, int score) {
		playersLabel.get(playerId).setText("Score: " + score);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == mntmExit) {
			System.exit(0);
		}
		else if (source == btnNextTurn) {
			currentPlayerTurnId++;
			if (currentPlayerTurnId == logWindow.getUsersList().size())
				currentPlayerTurnId = 0;
			GenerateCurrentPlayerCards();
			btnNextTurn.setEnabled(false);
			cardDropped = false;
		}
		else if(source == mnItemAbout)
		{
			JOptionPane.showMessageDialog(null, "Game rules\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"Game board has 16 squares (4x4).\r\n" + 
					"Every player in their turn draw a card a must place it on the board.\r\n" +
					"There are 4 types of cards:\r\n" +
					"1. MachineGuy - this card attacks everybody in a straight line.\r\n"+
					"2. Soldier - this card can attack only field in front of a card.\r\n"+
					"3. Berserker - this card attacks everybody around.\r\n" +
					"4. Battle - this card starts fight.\r\n" +
					"If they draw a battle card then:\r\n" + 
					"Battle:\r\n" + 
					"Every card has its initiative (the higher the number the earlier card will attack)\r\n" + 
					"card that is being attacked will have decreased its HP points by a DMG points of an attacking card\r\n" + 
					"at the end of a initiative every card that has less than 0 HP points is removed from the board\r\n" + 
					"player gets points for every card that their card will remove from the board (the amount of removed card's HP points)" +
					"\r\n" +
					"\r\n"+
					"The player that reaches the best score (the player who eliminated the most cards) win the game\r\n");
		}
		else if( source ==  mnItemHelp)
		{
			JOptionPane.showMessageDialog(null, "Help\r\n" + 
		"\r\n" +
		"\r\n" +
		"Useful tips!\r\n" +
		"- place your Soldier cards only in front of some other card, not in front of empty field, because this card attack target thats in front of him!\r\n" +
		"- when you want to place a Berserker card, try to hit as many players arround as possible!\r\n" +
		"- place your Machine Guy card in a straight line that contains as many enemy cards as possible\r\n" +
		"- if the field is full of cards it will automacilly call for the Battle\r\n"+
		"- when the game starts you've got 3 cards on your hand\r\n"+
		"- you can only have 3 cards on your hand\r\n"+
		"- you have to put on field one card in your turn\r\n"+
		"- the player who eliminated the most cards int every battle win the game\r\n");

		}
		else if(source == mnNewGame)
		{
			

		}
		else if(source == mnReset)
		{
			

		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NeuroshimaApp window = new NeuroshimaApp();
					// window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		boolean isCard1 = ((currentPlayerCards.size() >= 1) ? true : false);
		boolean isCard2 = ((currentPlayerCards.size() >= 2) ? true : false);
		boolean isCard3 = ((currentPlayerCards.size() >= 3) ? true : false);
				if (isCard1 && source == currentPlayerCards.get(0)) {
			if (isCard1)
				currentPlayerCards.get(0).setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			if (isCard2)
				currentPlayerCards.get(1).setBorder(BorderFactory.createLineBorder(Color.RED, 0));
			if (isCard3)
				currentPlayerCards.get(2).setBorder(BorderFactory.createLineBorder(Color.RED, 0));
			selectedPlayerCard = currentPlayerCards.get(0);
			selectedPlayerCardId = 0;
		} else if (isCard2 && source == currentPlayerCards.get(1)) {
			if (isCard1)
				currentPlayerCards.get(0).setBorder(BorderFactory.createLineBorder(Color.RED, 0));
			if (isCard2)
				currentPlayerCards.get(1).setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			if (isCard3)
				currentPlayerCards.get(2).setBorder(BorderFactory.createLineBorder(Color.RED, 0));
			selectedPlayerCard = currentPlayerCards.get(1);
			selectedPlayerCardId = 1;
		} else if (isCard3 && source == currentPlayerCards.get(2)) {
			if (isCard1)
				currentPlayerCards.get(0).setBorder(BorderFactory.createLineBorder(Color.RED, 0));
			if (isCard2)
				currentPlayerCards.get(1).setBorder(BorderFactory.createLineBorder(Color.RED, 0));
			if (isCard3)
				currentPlayerCards.get(2).setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			selectedPlayerCard = currentPlayerCards.get(2);
			selectedPlayerCardId = 2;
		} else {
			if (selectedPlayerCard == null) {
				JOptionPane.showMessageDialog(null, "Select one card from \"Your cards\" panel");
				return;
			}
			if (!cardDropped) {
				for (int i = 0; i < board.getHeight(); i++) {
					for (int j = 0; j < board.getWidth(); j++) {
						if (source == board.getFieldOnBoard()[i][j] && board.getFieldOnBoard()[i][j].isAvailable()) {
							board.getFieldOnBoard()[i][j].setIcon(selectedPlayerCard.getIcon());
							board.getFieldOnBoard()[i][j].setAvailable(false);
							board.getFieldOnBoard()[i][j].setIcon(selectedPlayerCard.getIcon());
							board.getFieldOnBoard()[i][j].setCardOnField(logWindow.getUsersList()
									.get(currentPlayerTurnId).GetUserCards().get(selectedPlayerCardId));
							selectedPlayerCard.setBorder(BorderFactory.createLineBorder(Color.RED, 0));
							selectedPlayerCard.setVisible(false);

							logWindow.getUsersList().get(currentPlayerTurnId).RemoveCardFromUser(selectedPlayerCardId);
							logWindow.getUsersList().get(currentPlayerTurnId).GenerateRandomCard();

							selectedPlayerCard = null;

							btnNextTurn.setEnabled(true);
							cardDropped = true;
						String attackString = "Attack";
						if (attackString.equals(board.getFieldOnBoard()[i][j].getCardOnField().getCardType()))
							battleStart = true;
						if (battleStart) {
							// shows message and calls battle start
							JOptionPane.showMessageDialog(null, "The battle starts now");
							battle();
							battleStart = false;
						}
						}
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Your turn passed!");
			}
		}

	}

	private void findMaximum() {
		int maximumInitiative = board.getFieldOnBoard()[0][0].getCardOnField().getInitiative();
		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {

					maximumInitiative = board.getFieldOnBoard()[i][j].getCardOnField().getInitiative();
				}
			
			}
		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
					if( maximumInitiative < board.getFieldOnBoard()[i][j].getCardOnField().getInitiative()) {
						maximumInitiative = board.getFieldOnBoard()[i][j].getCardOnField().getInitiative();
					}
			}
		}
	}

	private void battle() {
		// TODO Auto-generated method stub
		Field[][] copyField = board.getFieldOnBoard().clone();
		//copyField.
		
		
		
		
		
		//copyField.sort(Comparator.comparing(Card::getInitiative()));

		
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
