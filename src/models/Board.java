package models;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.BorderFactory;

public class Board{
	
	private static Field[][] fieldOnBoard = new Field[4][4];
	private int width, height;
	private int maximumInitiative = 0;

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
		for(int n=0; n<height;n++){
			for(int m=0;m<width;m++){
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
	
//	public void setCardsOnField(Field[][] copyField) {
//		for(int i=0; i<height;i++) {
//			for(int j=0; j<width;j++) {
//				fieldOnBoard[i][j] = copyField[i][j];
//			}
//		}
//	}

	public Field[][] removeDeathCards(Field[][] copyField) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (fieldOnBoard[i][j] != null) {
					if (!fieldOnBoard[i][j].isAvailable()) {
						if (this.getFieldOnBoard()[i][j].getCardOnField().getHealth() <= 0) {
							fieldOnBoard[i][j].setAvailable(true);
							fieldOnBoard[i][j].getCardOnField().setDeath(true);
						}
					}
				}
			}
		}
		return copyField;
	}
	
//	public void copyArrayField(Field[][] sourceArray, Field[][] destinationArray) {
//		for (int i = 0; i < sourceArray.length; i++) {
//				destinationArray[i] = Arrays.copyOf(sourceArray[i], sourceArray[i].length);
//		}
//	}
	
	public void setAllCardsAttackedOn(boolean attacked) {
		for(int i=0; i<height; i++) {
			for(int j=0; j<width;j++) {
				if(fieldOnBoard[i][j].getCardOnField()!=null) {
					fieldOnBoard[i][j].getCardOnField().setAttackedInThisTour(false);
				}
			}
		}
	}
	
	public void setAvailableFieldOnTrue(int posX, int posY) {
		fieldOnBoard[posX][posY].setAvailable(true);
	}
	
	public Card getCardOnField(int posX, int posY) {
		return fieldOnBoard[posX][posY].getCardOnField();
	}
	
	// find maximumInitiative of cards on board + remember i/j indexes of this card
	public Pair<Integer,Integer> findMaximum() {
		int coord_x=-1;
		int coord_y=-1;
		maximumInitiative = 0;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (fieldOnBoard[i][j] != null) {
					if(isCardOnFieldAndCanAttacked(fieldOnBoard[i][j]))
						if (maximumInitiative < fieldOnBoard[i][j].getCardOnField().getInitiative()) {
							maximumInitiative = fieldOnBoard[i][j].getCardOnField().getInitiative();
							coord_x=j;
							coord_y=i;
						}
				}
			}
		}
		return new Pair<Integer,Integer>(coord_x,coord_y);
	}
	
	public boolean isCardOnFieldAndCanAttacked(Field field) {
		if (field.isAvailable() == false && field.getCardOnField().isAttackedInThisTour()==false) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
