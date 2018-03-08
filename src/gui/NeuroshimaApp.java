package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import sun.rmi.runtime.Log;

public class NeuroshimaApp {

	private JFrame frame;
	private LogWindow logWindow;

	/**
	 * Create the application.
	 */
	public NeuroshimaApp() {
		frame = new JFrame();
		frame.setBounds(100, 100, 698, 463);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Neuroshima");
		
		logWindow = new LogWindow();
		logWindow.setVisible(true);
		
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
}
