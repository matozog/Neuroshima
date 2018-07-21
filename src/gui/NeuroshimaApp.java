package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import models.Board;
import models.Deck;
import models.Field;
import models.Game;
import models.GameStatus;
import models.PictureTools;
import models.Player;
import models.PlayersManager;

public class NeuroshimaApp implements ActionListener {

	private static String PATH_TO_BACKGROUND_IMAGE = "/gui/images/board.png";
	
	private JFrame frame;
	private LogWindow logWindow;
	private JMenuItem mntmExit;
	private JMenu mnOther;
	private NextTurnPanel panelNextTurn;
	private ArrayList<PlayerPanel> playersPanels = new ArrayList<PlayerPanel>();
	private Deck deck;
	private JMenuItem mnItemAbout, mnItemHelp, mnNewGame;
	private PlayersManager playersManager = new PlayersManager();
	private PictureTools pictureTools;
	private CurrentPlayerCardsPanel currentPlayerCardsPanel;
	private GameStatus gameStatus;
	private GameBoardPanel gameBoardPanel;

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
		
		gameStatus = new GameStatus();
		pictureTools = new PictureTools();
		gameBoardPanel  = new GameBoardPanel();
		panelNextTurn = new NextTurnPanel();
		currentPlayerCardsPanel = new CurrentPlayerCardsPanel(panelNextTurn);
		
		loadBackgroundPicture();
			
		frame.getContentPane().add(gameBoardPanel);
		

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);

		mnOther = new JMenu("Other");
		menuBar.add(mnOther);

		mnNewGame = new JMenuItem("New game");
		mnNewGame.addActionListener(this);
		mnGame.add(mnNewGame);

		mnItemHelp = new JMenuItem("Help");
		mnItemHelp.addActionListener(this);
		mnOther.add(mnItemHelp);

		mnItemAbout = new JMenuItem("About");
		mnItemAbout.addActionListener(this);
		mnOther.add(mnItemAbout);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(this);
		mnGame.add(mntmExit);

		frame.getContentPane().add(currentPlayerCardsPanel);
		frame.getContentPane().add(panelNextTurn);
		
		frame.setVisible(false);
		logWindow.setVisible(true);
	}

	public void loadBackgroundPicture() {
		try {
			Image backgorund = ImageIO.read(getClass().getResource(PATH_TO_BACKGROUND_IMAGE));
			frame.setContentPane(new JLabel(pictureTools.scaleImageAndConvertToIcon(backgorund, 1042, 796)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMainFrame() {
		frame.setVisible(true);
	}
	
	public void prepareBoardAndDeckAndCardsForGame() {
		deck = new Deck();
		generatePlayersPanels();
		GeneratePlayerCards();
		setColorForEachPlayer();
		panelNextTurn.setNextTurnPlayerNumber(0);
	}
	
	public void setColorForEachPlayer() {
		for (int i = 0; i < playersManager.getSelectedPlayers().size(); i++) {
			switch (i) {
			case 0:
				playersManager.getSelectedPlayers().get(i).setColor(Color.ORANGE);
				break;
			case 1:
				playersManager.getSelectedPlayers().get(i).setColor(Color.PINK);
				break;
			case 2:
				playersManager.getSelectedPlayers().get(i).setColor(Color.GREEN);
				break;
			case 3:
				playersManager.getSelectedPlayers().get(i).setColor(Color.BLUE);
				break;
			}
		}
	}
	
	public void generatePlayersPanels() {
		for(int i=0; i<playersManager.getSelectedPlayers().size();i++) {
			PlayerPanel playerPanel = new PlayerPanel(i);
			playerPanel.generateThreeCardReverse();
			frame.getContentPane().add(playerPanel);
		}
	}
	
	public void GeneratePlayerCards() {
//		currentPlayerTurn = playersManager.getSelectedPlayers().get(0); // default first player
		gameStatus.setCurrentPlayerTurnID(0);

		for (int i = 0; i < playersManager.getSelectedPlayers().size(); i++) {
			playersManager.getSelectedPlayers().get(i).GenerateRandomCard();
			playersManager.getSelectedPlayers().get(i).GenerateRandomCard();
			playersManager.getSelectedPlayers().get(i).GenerateRandomCard();
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
		playersPanels.get(playerId).setTextOnScoreLabel("Score: " + score);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == mntmExit) {
			System.exit(0);
		}  else if (source == mnItemAbout) {
			JOptionPane.showMessageDialog(null, "Game rules\r\n" + "\r\n" + "\r\n"
					+ "Game board has 16 squares (4x4).\r\n"
					+ "Every player in their turn draw a card a must place it on the board.\r\n"
					+ "There are 4 types of cards:\r\n"
					+ "1. MachineGuy - this card attacks everybody in a straight line.\r\n"
					+ "2. Soldier - this card can attack only field in front of a card.\r\n"
					+ "3. Berserker - this card attacks everybody around.\r\n"
					+ "4. Battle - this card starts fight.\r\n" + "If they draw a battle card then:\r\n" + "Battle:\r\n"
					+ "Every card has its initiative (the higher the number the earlier card will attack)\r\n"
					+ "card that is being attacked will have decreased its HP points by a DMG points of an attacking card\r\n"
					+ "at the end of a initiative every card that has less than 0 HP points is removed from the board\r\n"
					+ "player gets points for every card that their card will remove from the board (the amount of removed card's HP points)"
					+ "\r\n" + "\r\n"
					+ "The player that reaches the best score (the player who eliminated the most cards) win the game\r\n");
		} else if (source == mnItemHelp) {
			JOptionPane.showMessageDialog(null, "Help\r\n" + "\r\n" + "\r\n" + "Useful tips!\r\n"
					+ "- place your Soldier cards only in front of some other card, not in front of empty field, because this card attack target thats in front of him!\r\n"
					+ "- when you want to place a Berserker card, try to hit as many players arround as possible!\r\n"
					+ "- place your Machine Guy card in a straight line that contains as many enemy cards as possible\r\n"
					+ "- if the field is full of cards it will automacilly call for the Battle\r\n"
					+ "- when the game starts you've got 3 cards on your hand\r\n"
					+ "- you can only have 3 cards on your hand\r\n"
					+ "- you have to put on field one card in your turn\r\n"
					+ "- the player who eliminated the most cards int every battle win the game\r\n");

		} else if (source == mnNewGame) {
			if (JOptionPane.showConfirmDialog(null, "Do you really want to start a new game? Changes will be lost?",
					"Question", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
				for (Player user : playersManager.getSelectedPlayers()) {
					user.setScore(0);
				}
				logWindow = null;
				frame.setVisible(false);
				frame = null;
				NeuroshimaApp window = new NeuroshimaApp();
			}

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
}
