package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import sun.rmi.runtime.Log;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class NeuroshimaApp implements ActionListener, MenuListener{

	private JFrame frame;
	private LogWindow logWindow;
	private JMenuItem mntmExit; 
	private JMenu mnAbout;
	private JMenu mnHelp;

	/**
	 * Create the application.
	 */
	public NeuroshimaApp() {
		frame = new JFrame();
		frame.setBounds(100, 100, 698, 463);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Neuroshima");
		frame.getContentPane().setLayout(null);
		
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
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == mntmExit) {
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

		if(source == mnHelp) {
			JOptionPane.showMessageDialog(null, "Tu bêdzie pomoc, zasady itp");
			mnHelp.setSelected(false);
		}
		else if(source == mnAbout) {
			JOptionPane.showMessageDialog(null, "Program wykonali:\n\n £ukasz x2 Miki Mati Krzychu");
			mnAbout.setSelected(false);
		}	
	}


	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}
}
