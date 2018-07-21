package gui;

import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.InterrupterMessage;
import models.PictureTools;
import models.Player;
import models.PlayersManager;

/*
 * Class to choice players and start game
 */
public class LogWindow extends JFrame implements ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private static final String PATH_TO_REMOVE_PICTURE = "/gui/images/remove.png";
	private static final String PATH_TO_ADD_PICTURE = "/gui/images/add.png";
	
	private JPanel contentPane;
	private JPanel panelMenu,panelPlayer,panelListPlayers,panelAllPlayers;
	private JButton btnPlayGame,btnShowRankPlayers,btnExitGame,btnRemovePlayerFromList;
	private JTextField txtPlayerName;
	private JList listOfPlayers,listAllPlayers;
	private DefaultListModel modelSelectedPlayers, modelAllPlayers;
	private JButton btnAddPlayerToList;
	private NeuroshimaApp mainFrame;
	private PlayersManager playersManager;
	private PictureTools pictureTools;
	private InterrupterMessage interrupter;
	
	/**
	 * Create the frame.
	 */
	public LogWindow(NeuroshimaApp app) {
		mainFrame = app;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 290);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		playersManager = new PlayersManager();
		pictureTools = new PictureTools();
		interrupter = new InterrupterMessage();

		panelMenu = new JPanel();
		panelMenu.setBounds(12, 13, 171, 219);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		panelMenu.setBorder(BorderFactory.createTitledBorder("Menu"));

		btnPlayGame = new JButton("Play");
		btnPlayGame.setBounds(12, 23, 147, 53);
		btnPlayGame.addActionListener(this);
		btnPlayGame.setEnabled(false);
		panelMenu.add(btnPlayGame);

		btnShowRankPlayers = new JButton("Show rank players");
		btnShowRankPlayers.setBounds(12, 89, 147, 53);
		btnShowRankPlayers.addActionListener(this);
		panelMenu.add(btnShowRankPlayers);

		btnExitGame = new JButton("Exit");
		btnExitGame.setBounds(12, 155, 147, 51);
		btnExitGame.addActionListener(this);
		panelMenu.add(btnExitGame);

		panelPlayer = new JPanel();
		panelPlayer.setBounds(208, 13, 226, 75);
		contentPane.add(panelPlayer);
		panelPlayer.setBorder(BorderFactory.createTitledBorder("Player"));
		panelPlayer.setLayout(null);

		txtPlayerName = new JTextField();
		txtPlayerName.setBounds(92, 27, 116, 22);
		txtPlayerName.addActionListener(this);
		panelPlayer.add(txtPlayerName);
		txtPlayerName.setColumns(10);

		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(12, 30, 68, 16);
		panelPlayer.add(lblNickname);

		btnAddPlayerToList = new JButton("");
		btnAddPlayerToList.setBounds(440, 38, 35, 25);
		btnAddPlayerToList.setBorder(BorderFactory.createEmptyBorder());
		btnAddPlayerToList.setContentAreaFilled(false);
		loadImageForAddButton(PATH_TO_ADD_PICTURE);
		btnAddPlayerToList.addActionListener(this);
		contentPane.add(btnAddPlayerToList);

		panelListPlayers = new JPanel();
		panelListPlayers.setBounds(208, 101, 267, 131);
		contentPane.add(panelListPlayers);
		panelListPlayers.setBorder(BorderFactory.createTitledBorder("List of players"));
		panelListPlayers.setLayout(null);

		modelSelectedPlayers = new DefaultListModel();
		listOfPlayers = new JList(modelSelectedPlayers);
		listOfPlayers.setBackground(SystemColor.menu);
		listOfPlayers.setBounds(12, 23, 197, 95);
		listOfPlayers.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listOfPlayers.setLayoutOrientation(JList.VERTICAL);
		listOfPlayers.setVisibleRowCount(-1);
		panelListPlayers.add(listOfPlayers);

		btnRemovePlayerFromList = new JButton("");
		btnRemovePlayerFromList.setContentAreaFilled(false);
		btnRemovePlayerFromList.setBorder(BorderFactory.createEmptyBorder());
		btnRemovePlayerFromList.setBounds(221, 23, 35, 25);
		loadImageForRemoveButton(PATH_TO_REMOVE_PICTURE);
		btnRemovePlayerFromList.addActionListener(this);
		panelListPlayers.add(btnRemovePlayerFromList);
		
		panelAllPlayers = new JPanel();
		panelAllPlayers.setBounds(487, 13, 160, 219);
		panelAllPlayers.setBorder(BorderFactory.createTitledBorder("Available players"));
		contentPane.add(panelAllPlayers);
		
		modelAllPlayers = new DefaultListModel();
		listAllPlayers = new JList(modelAllPlayers);
		listAllPlayers.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Object z = e.getSource();
				if(z==listAllPlayers){
					txtPlayerName.setText("");
				}
			}
		});
		panelAllPlayers.setLayout(null);
		listAllPlayers.setBackground(SystemColor.menu);
		listAllPlayers.setBounds(12, 24, 136, 182);
		listAllPlayers.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listAllPlayers.setLayoutOrientation(JList.VERTICAL);
		listAllPlayers.setVisibleRowCount(-1);
		panelAllPlayers.add(listAllPlayers);
		this.setLocationRelativeTo(null);
		this.setTitle("Neuroshima");
		
		playersManager.unmarshal();
		savePlayersWithIndexToList();
	}

	public void loadImageForAddButton(String pathToPicture) {
		try {
			Image img = ImageIO.read(getClass().getResource(pathToPicture));
			btnAddPlayerToList.setIcon(pictureTools.scaleImageAndConvertToIcon(img, btnAddPlayerToList.getWidth(), btnAddPlayerToList.getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadImageForRemoveButton(String pathToPicture){
		try {
			Image img_1 = ImageIO.read(getClass().getResource(pathToPicture));
			btnRemovePlayerFromList.setIcon(pictureTools.scaleImageAndConvertToIcon(img_1, btnAddPlayerToList.getWidth(), btnAddPlayerToList.getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();

		if(z == btnAddPlayerToList){
			if(playersManager.checkMaxAmountsPlayersToStartGame()) {
				addPlayerToList();
			}
			else {
				interrupter.showMessage_ToManyPlayers();
				
			}
			txtPlayerName.setText("");
		}
		else if(z == btnExitGame){
			System.exit(0);
		}
		else if(z == btnPlayGame){
			startGame();
		}
		else if( z== btnShowRankPlayers){

		}
		else if(z==btnRemovePlayerFromList){
			removeUserFromList();
			refreshSelectedPlayersList();
		}
		else if(z==txtPlayerName){
			listAllPlayers.clearSelection();
		}
		btnPlayGame.setEnabled(playersManager.checkMinAmountsPlayersToStartGame());
	}
	
	public void addPlayerToList() {
		if(txtPlayerName.getText().length()!=0) {
			playersManager.tryAddPlayerWithNickToList(txtPlayerName.getText());
		}
		else if(listAllPlayers.getSelectedIndex()!=-1) {
			playersManager.tryAddPlayerFromDatabaseToList(listAllPlayers.getSelectedIndex());
		}
		else {
			interrupter.showMessage_EmptyNicknameField();
		}
		refreshSelectedPlayersList();
		savePlayersWithIndexToList();
	}
	
	/*
	 * Index means number before nickname, it use rather to systematize than as functionality
	 */
	public void savePlayersWithIndexToList() {
		modelAllPlayers.removeAllElements();
		for(int i=0; i<playersManager.getAllPlayers().size(); i++){
			modelAllPlayers.addElement(i + "." + playersManager.getAllPlayers().get(i).getName());
		}
	}
	
	public void removeUserFromList() {
		if(modelSelectedPlayers.size()!=0 && listOfPlayers.getSelectedIndex()!=-1){
			playersManager.getSelectedPlayers().remove(listOfPlayers.getSelectedIndex());
		}
		else {
			interrupter.showMessage_BadChoosePlayerWhenRemove();
		}
	}

	public void refreshSelectedPlayersList() {
		modelSelectedPlayers.removeAllElements();
		for(Player player: playersManager.getSelectedPlayers()) {
			modelSelectedPlayers.addElement(player.getName());
		}
	}
	
	public void startGame() {
		setVisible(false);
		mainFrame.prepareBoardAndDeckAndCardsForGame();
		mainFrame.showMainFrame();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
