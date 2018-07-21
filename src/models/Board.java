package models;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;

public class Board{
	
	private static Field[][] fieldOnBoard = new Field[4][4];
	private int width, height;
	private int maximumInitiative = 0, battleX, battleY;

	public Board(int width, int height){
		this.width=width;
		this.height=height;
	}
	
	public Field[][] getFieldOnBoard() {
		return fieldOnBoard;
	}

	public int getWidth() {
		return width;
	}
	
	public int getMaximumInitiative() {
		return maximumInitiative;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean isFull(){
		for( int n=0; n<getHeight();n++){
			for(int m=0;m<getWidth();m++){
				if(fieldOnBoard[n][m].isAvailable()){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Generate game board
	 */
	public void generateFieldOnBoard(int width, int height) {
		int cellWidth = width / 4;
		int cellHeight = height / 4;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Field field = new Field(cellWidth, cellHeight, j, i);
				field.setBounds(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
				field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				fieldOnBoard[i][j] = field;
			}
		}
	}
	

	public Field[][] removeDeathCards(Field[][] copyField) {
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				if (this.getFieldOnBoard()[i][j] != null) {
					if (!this.getFieldOnBoard()[i][j].isAvailable()) {
						if (this.getFieldOnBoard()[i][j].getCardOnField().getHealth() <= 0) {
							copyField[i][j].setAvailable(true);
//							copyField[i][j] = null;
						}
					}
				}
			}
		}
		return copyField;
	}
	
	public Pair<Integer,Integer> findMaximum() {
		maximumInitiative = 0;
		int coord_x=-1;
		int coord_y=-1;
		// fin maximumInitiative of cards on board + remeber i/j indexes of this card
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				if (this.getFieldOnBoard()[i][j] != null) {
					if (this.getFieldOnBoard()[i][j].isAvailable() == false && this.getFieldOnBoard()[i][j].getCardOnField().isAttackedInThisTour()==false) {
						if (maximumInitiative < fieldOnBoard[i][j].getCardOnField().getInitiative()) {
							maximumInitiative = fieldOnBoard[i][j].getCardOnField().getInitiative();
							coord_x=i;
							coord_y=j;
						}
					}
				}
			}
		}
		return new Pair<Integer,Integer>(coord_x,coord_y);
	}
	
}
