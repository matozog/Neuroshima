package models;

import javax.swing.JOptionPane;

public class InterrupterMessage {

	public void showMessage_ToManyPlayers() {
		JOptionPane.showMessageDialog(null, "Too many players! (max. 4 players)", "Waring", JOptionPane.WARNING_MESSAGE);
	}
	
	public void showMessage_EmptyNicknameField() {
		JOptionPane.showMessageDialog(null, "Field with nickname is empty!");
	}
	
	public void showMessage_NicknameIsOnListAllPlayers() {
		JOptionPane.showMessageDialog(null, "This user is on list all players!");
	}
	
	public void showMessage_NicknameIsOnListSelectedPlayers() {
		JOptionPane.showMessageDialog(null, "This user is on list selected players!");
	}
	
	public void showMessage_BadChoosePlayerWhenRemove() {
		JOptionPane.showMessageDialog(null,"Empty list or do not choose player!" );
	}
	
	public void showMessage_NotChoosenCard() {
		JOptionPane.showMessageDialog(null, "Select one card from \"Your cards\" panel");
	}
}
