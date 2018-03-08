package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class LogWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel panelAction,panelPlayer,panelListPlayers;
	private JButton btnPlay,btnShowRankPlayers,btnExit;
	private JTextField textField;
	private JList listOfPlayers;
	private JButton btnAdd;

	/**
	 * Create the frame.
	 */
	public LogWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 290);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelAction = new JPanel();
		panelAction.setBounds(12, 13, 171, 219);
		contentPane.add(panelAction);
		panelAction.setLayout(null);
		panelAction.setBorder(BorderFactory.createTitledBorder("Menu"));
		
		btnPlay = new JButton("Play");
		btnPlay.setBounds(12, 23, 147, 53);
		panelAction.add(btnPlay);
		
		btnShowRankPlayers = new JButton("Show rank players");
		btnShowRankPlayers.setBounds(12, 89, 147, 53);
		panelAction.add(btnShowRankPlayers);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(12, 155, 147, 51);
		panelAction.add(btnExit);
		
		panelPlayer = new JPanel();
		panelPlayer.setBounds(208, 13, 267, 75);
		contentPane.add(panelPlayer);
		panelPlayer.setBorder(BorderFactory.createTitledBorder("Player"));
		panelPlayer.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(92, 27, 116, 22);
		panelPlayer.add(textField);
		textField.setColumns(10);
		
		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(12, 30, 68, 16);
		panelPlayer.add(lblNickname);
		
		btnAdd = new JButton("");
		btnAdd.setBounds(220, 26, 35, 25);
		btnAdd.setBorder(BorderFactory.createEmptyBorder());
		btnAdd.setContentAreaFilled(false);
		try {
			Image img = ImageIO.read(getClass().getResource("/gui/images/add1.png"));
			btnAdd.setIcon(scaleImage(img, btnAdd.getWidth(), btnAdd.getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		btnAdd.addActionListener(this);
		panelPlayer.add(btnAdd);
		
		panelListPlayers = new JPanel();
		panelListPlayers.setBounds(208, 101, 267, 131);
		contentPane.add(panelListPlayers);
		panelListPlayers.setBorder(BorderFactory.createTitledBorder("List of players"));
		panelListPlayers.setLayout(null);
		
		listOfPlayers = new JList();
		listOfPlayers.setBounds(12, 23, 243, 95);
		panelListPlayers.add(listOfPlayers);
		this.setLocationRelativeTo(null);
		this.setTitle("NeuroshimaDemo");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();
		
		if(z == btnAdd)
		{
			System.out.print("Works");
		}
		else if(z == btnExit)
		{
			
		}
		else if(z == btnPlay)
		{
			
		}
		else if( z== btnShowRankPlayers)
		{
			
		}
	}
	
	
	/**
	 * 
	 * @param img - image to scale
	 * @param width - new img width
	 * @param height - new img height
	 * @return imgIcon
	 */
	public ImageIcon scaleImage(Image img, int width, int height)
	{
		Image scaleImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon imgIcon= new ImageIcon(scaleImg);
		return imgIcon;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogWindow frame = new LogWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
