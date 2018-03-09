package models;

public class Board {
	
	private Field[][] fieldOnBoard = new Field[4][4];
	private int width, height;

	public Board(int width, int height)
	{
		this.width=width;
		this.height=height;
	}
	
	public Field[][] getFieldOnBoard() {
		return fieldOnBoard;
	}

	public int getWidth() {
		return width;
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
}
