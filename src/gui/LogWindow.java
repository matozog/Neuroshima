package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import models.*;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

/*
 * Class to choice players and start game
 */
public class LogWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel panelAction,panelPlayer,panelListPlayers;
	private JButton btnPlay,btnShowRankPlayers,btnExit;
	private JTextField txtPlayerName;
	private JList listOfPlayers;
	private JButton btnAdd;
	private Players players;
	private boolean createUser = true;

	/**
	 * 
	 * Inner class for players 
	 *
	 */
	@XmlRootElement(name = "Players")
	static class Players
	{
		@XmlElement (name = "Player")
		List<User> playersList=null;

		public List<User> getPlayersList() {
			return playersList;
		}
		
	}
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
		
		players = new Players();
		unmarshal();
		
		panelAction = new JPanel();
		panelAction.setBounds(12, 13, 171, 219);
		contentPane.add(panelAction);
		panelAction.setLayout(null);
		panelAction.setBorder(BorderFactory.createTitledBorder("Menu"));
		
		btnPlay = new JButton("Play");
		btnPlay.setBounds(12, 23, 147, 53);
		btnPlay.addActionListener(this);
		panelAction.add(btnPlay);
		
		btnShowRankPlayers = new JButton("Show rank players");
		btnShowRankPlayers.setBounds(12, 89, 147, 53);
		btnShowRankPlayers.addActionListener(this);
		panelAction.add(btnShowRankPlayers);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(12, 155, 147, 51);
		btnExit.addActionListener(this);
		panelAction.add(btnExit);
		
		panelPlayer = new JPanel();
		panelPlayer.setBounds(208, 13, 267, 75);
		contentPane.add(panelPlayer);
		panelPlayer.setBorder(BorderFactory.createTitledBorder("Player"));
		panelPlayer.setLayout(null);
		
		txtPlayerName = new JTextField();
		txtPlayerName.setBounds(92, 27, 116, 22);
		panelPlayer.add(txtPlayerName);
		txtPlayerName.setColumns(10);
		
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
		this.setTitle("Neuroshima");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();
		
		if(z == btnAdd)
		{
			if(txtPlayerName.getText().length()!=0)
			{
				for(User user: players.playersList)
				{
					if(txtPlayerName.getText().equals(user.getName()))
					{
						if(JOptionPane.showConfirmDialog(this, "This nick is used, would you like to play as this player?","Question",
								JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)==0)
						{
							
							//dodanie do listy graczy gotowych do gry
							txtPlayerName.setText("");
							createUser= false;
							break;
						}
						createUser= false;
						txtPlayerName.setText("");
						break;
					}
				}
				if(createUser)
				{
					User user = new User(players.playersList.size()+1,txtPlayerName.getText(),0);
					players.playersList.add(user);
					marshall();
					txtPlayerName.setText("");
				}
				createUser = true;
			}
			else {
				JOptionPane.showMessageDialog(this, "Field with nickname is empty!");
			}
		}
		else if(z == btnExit)
		{
			System.exit(0);
		}
		else if(z == btnPlay)
		{
			this.setVisible(false);
		}
		else if( z== btnShowRankPlayers)
		{
			
		}
	}
	
	/**
	 * Read from xml file to playersList
	 */
	public void unmarshal()
	{
		try {			
			JAXBContext jabx = JAXBContext.newInstance(Players.class);

			Unmarshaller unmarsh = jabx.createUnmarshaller();
			players = (Players) unmarsh.unmarshal(new File("DataPlayers.xml"));
			if(players.playersList.size()!=0)
			for(User u: players.playersList)
			{
				System.out.print(u.getName()+ " " + u.getId() + " " + u.getScore() + "\n");
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Save playersList to xml file
	 * 
	 */
	public void marshall()
	{
	    try {
	    	JAXBContext jaxbContext = JAXBContext.newInstance(Players.class);
		    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(players, new File("DataPlayers.xml"));
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
