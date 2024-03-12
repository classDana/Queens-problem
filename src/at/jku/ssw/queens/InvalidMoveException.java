package at.jku.ssw.queens;

@SuppressWarnings("serial")
public class InvalidMoveException extends QueensException {
	
	private final int row; 
	private final int col; 

	public InvalidMoveException(Queens game, int row, char col) {
		super("Position occupied", game);
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
		return "InvalidMoveException: Position occupied [row=" + row + ", col=" + col + "]";
	}

}
