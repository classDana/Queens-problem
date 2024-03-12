package at.jku.ssw.queens;

@SuppressWarnings("serial")
public class InvalidPositionException extends QueensException {
	
	private final int row; 
	private final int col; 

	public InvalidPositionException(Queens game, int row, char col) {
		super("Invalid position", game);
		this.row = row; 
		this.col = col; 
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public String toString() {
		return "InvalidPositionException [row=" + row + ", col=" + col + "]";
	}

}
