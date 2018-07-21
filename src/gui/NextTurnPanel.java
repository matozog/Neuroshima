package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import models.PlayersManager;

public class NextTurnPanel extends JPanel {

	private JLabel lblNextTurn;
	private PlayersManager playersManager;
	
	public NextTurnPanel() {
		this.setBounds(305, 485, 424, 29);
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setLayout(null);
		playersManager = new PlayersManager();

		lblNextTurn = new JLabel();
		lblNextTurn.setHorizontalAlignment(SwingConstants.CENTER);
		lblNextTurn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNextTurn.setBounds(12, 0, 404, 24);
		this.add(lblNextTurn);
	}
	
	public void setNextTurnPlayerNumber(int player) {
		lblNextTurn.setText("<html>Actual turn: <b>" + playersManager.getSelectedPlayers().get(player).getName() + "</b>");
	}
	
}
