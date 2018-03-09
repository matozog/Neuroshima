package gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import sun.rmi.runtime.Log;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color; 
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.SwingConstants;

public class NeuroshimaApp implements ActionListener, MenuListener {

	private JFrame frame;
	private LogWindow logWindow;
	private JMenuItem mntmExit;
	private JMenu mnAbout;
	private JMenu mnHelp;
	private JPanel panelGameMain;
	private ArrayList<JPanel> playersPanels = new ArrayList<JPanel>();
	private ArrayList<JLabel> playersLabel = new ArrayList<JLabel>();
	private ArrayList<JLabel> boardCells = new ArrayList<JLabel>();
	private JLabel lblNextTurn;
	private JPanel panelNextTurn;
	

	/**
	 * Create the application.
	 */
	public NeuroshimaApp() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1036, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Neuroshima");
		frame.getContentPane().setLayout(null);
		frame.setContentPane(new JLabel(new ImageIcon(getClass().getResource("/gui/images/grass_texture.jpg"))));

		panelGameMain = new JPanel();
		panelGameMain.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelGameMain.setBounds(305, 62, 424, 412);
		frame.getContentPane().add(panelGameMain);
		panelGameMain.setLayout(null);
		
		panelNextTurn = new JPanel();
		panelNextTurn.setBounds(305, 485, 424, 46);
		panelNextTurn.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelNextTurn.setLayout(null);
		frame.getContentPane().add(panelNextTurn);
		
		lblNextTurn = new JLabel("<html>Next turn: <b>Player1</b>");
		lblNextTurn.setHorizontalAlignment(SwingConstants.CENTER);
		lblNextTurn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNextTurn.setBounds(10, 11, 404, 24);
		panelNextTurn.add(lblNextTurn); 

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);

		JMenuItem mntmNewGame = new JMenuItem("New game");
		mnGame.add(mntmNewGame);

		JMenuItem mntmReset = new JMenuItem("Reset");
		mnGame.add(mntmReset);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(this);
		mnGame.add(mntmExit);

		mnHelp = new JMenu("Help");
		mnHelp.addMenuListener(this);
		menuBar.add(mnHelp);

		mnAbout = new JMenu("About...");
		mnAbout.addMenuListener(this);
		menuBar.add(mnAbout);

		logWindow = new LogWindow();
		logWindow.setVisible(true);

		GeneratePlayersPanels(4);
		GenerateBoard();
		SetPlayerScore(3, 100);

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
			JOptionPane.showMessageDialog(null, "Niepoprawna liczba graczy");
			return;
		}

		for (int i = 0; i < playersCount; i++) {
			JPanel playerPanel = new JPanel();
			JLabel scoreLabel = new JLabel("Score: 0");

			scoreLabel.setBounds(new Rectangle(8, 15, 100, 20));

			playerPanel.setLayout(null);
			playerPanel.add(scoreLabel);
			playerPanel.setBounds(bounds[i]);
			playerPanel.setBorder(BorderFactory.createTitledBorder("Player #" + (i + 1)));

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
	
	public void SetNextTurn(int player) { 
		lblNextTurn.setText("<html>Next turn: <b>Player"+player+"</b>");
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
				JLabel cell = new JLabel();
				cell.setBounds(i*cellWidth,j*cellHeight,cellWidth,cellHeight);
				cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				boardCells.add(cell);
				panelGameMain.add(cell);
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
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NeuroshimaApp window = new NeuroshimaApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void menuSelected(MenuEvent e) {
		Object source = e.getSource();

		if (source == mnHelp) {
			JOptionPane.showMessageDialog(null, "Tu bêdzie pomoc, zasady itp");
			mnHelp.setSelected(false);
		} else if (source == mnAbout) {
			JOptionPane.showMessageDialog(null, "Program wykonali:\n\n £ukasz x2 Miki Mati Krzychu");
			mnAbout.setSelected(false);
		}
	}

	@Override
	public void menuDeselected(MenuEvent e) {

	}

	@Override
	public void menuCanceled(MenuEvent e) {

	}
}
