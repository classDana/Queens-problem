package at.jku.ssw.queens;

import java.util.EventObject;

public class QueensEvent extends EventObject {
	private final State state; 
	private final int row; 
	private final char col;
	
	public QueensEvent(Object source, State state, int row, char col) {
		super(source);
		this.state = state; 
		this.row = row; 
		this.col = col;
	}

	public State getState() {
		return state;
	}

	public int getRow() {
		return row;
	}

	public char getCol() {
		return col;
	}
	
}
