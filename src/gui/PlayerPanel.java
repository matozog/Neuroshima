package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.PictureTools;
import models.PlayersManager;

public class PlayerPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int playerPanelWidth  = 295;
	private static final int playerPanelHeight = 180;
	private static final Rectangle arrayBoundsFourPanel[] = new Rectangle[] { new Rectangle(3, 3, playerPanelWidth, playerPanelHeight),
			new Rectangle(733, 3, playerPanelWidth, playerPanelHeight),
			new Rectangle(3, 353, playerPanelWidth, playerPanelHeight),
			new Rectangle(733, 353, playerPanelWidth, playerPanelHeight) };
	
	private PictureTools pictureTools;
	private PlayersManager playersManager;
	private JLabel scoreLabel;
	private int numberOfPlayer;
	private String title;
	
	
	public PlayerPanel(int numberOfPlayer){
		this.numberOfPlayer = numberOfPlayer;
		pictureTools = new PictureTools();
		playersManager = new PlayersManager();
		scoreLabel = new JLabel("Score: 0");
		this.setLayout(null);
		this.add(scoreLabel);
		this.setBackground(new Color(0, 0, 0, 200));
		scoreLabel.setForeground(Color.white);
		scoreLabel.setBounds(new Rectangle(8, 15, 100, 20));

		this.setBounds(arrayBoundsFourPanel[numberOfPlayer]);
		
		title = "<html><font color=white>" + playersManager.getSelectedPlayers().get(numberOfPlayer).getName() + "</font></html>";
		this.setBorder(BorderFactory.createTitledBorder(title));
	}
	
	public void setTextOnScoreLabel(String text) {
		this.scoreLabel.setText(text);
	}
	
	public void generateThreeCardReverse() {
		try {
			Image img1 = ImageIO.read(getClass().getResource("/gui/images/card" + (numberOfPlayer + 1) + ".png"));
			JLabel card1 = new JLabel();
			card1.setIcon(pictureTools.scaleImageAndConvertToIcon(img1, 90, 130));
			card1.setBounds(8, 40, 90, 130);
			this.add(card1);

			JLabel card2 = new JLabel();
			card2.setIcon(pictureTools.scaleImageAndConvertToIcon(img1, 90, 130));
			card2.setBounds(103, 40, 90, 130);
			this.add(card2);

			JLabel card3 = new JLabel();
			card3.setIcon(pictureTools.scaleImageAndConvertToIcon(img1, 90, 130));
			card3.setBounds(198, 40, 90, 130);
			this.add(card3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
