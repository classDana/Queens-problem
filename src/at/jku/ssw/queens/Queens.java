package at.jku.ssw.queens;

import java.util.*;

public class Queens{
	
	public static Queens create(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Size of board must be positive");
		}
		return new Queens(n);
	}
	
	private final int n; 
	private boolean[][] board;
	private int nQueens; 
	private State state;

	private Queens(int n) {
		this.n = n;
		board = new boolean[n][n];
		nQueens = 0; 
		state = State.INCOMPLETE;
	}

	public int getSize() {
		return n;
	}

	public int getNQueens() {
		return nQueens;
	}

	public State setQueen(int row, char col) throws QueensException {
		if (state != State.INCOMPLETE) {
			throw new QueensException(String.format("Cannot set queen in state %s", state), this);
		}
		int rowIdx = row - 1; 
		int colIdx = col - 'A'; 
		if (!isValidPos(rowIdx, colIdx)) {
			throw new InvalidPositionException(this, row, col);
		}
		if (board[rowIdx][colIdx]) {
			throw new InvalidMoveException(this, row, col);
		}
		board[rowIdx][colIdx] = true; 
		nQueens += 1;
		State result = computeGameState(rowIdx, colIdx);
		fireQueensModelEvent(state, row, col);
		return result;
	}

	public State removeQueen(int row, char col) throws QueensException {
		if (state != State.INCOMPLETE && state != State.INVALID) {
			throw new UnsupportedOperationException("Cannot remove queen in state " + state);
		}
		int rowIdx = row - 1; 
		int colIdx = col - 'A'; 
		if (!isValidPos(rowIdx, colIdx)) {
			throw new InvalidPositionException(this, row, col);
		}
		if (!board[rowIdx][colIdx]) {
			throw new InvalidMoveException(this, row, col);
		}
		board[rowIdx][colIdx] = false; 
		nQueens -= 1;

		State result = computeGameState(rowIdx, colIdx);
		fireQueensModelEvent(state, row, col);
		return result;
	}

	public boolean hasQueen(int row, char col) throws InvalidPositionException {
		int rowIdx = row - 1;
		int colIdx = col - 'A';
		if (!isValidPos(rowIdx, colIdx)) {
			throw new InvalidPositionException(this, row, col);
		}
		
		return board[rowIdx][colIdx];
	}

	public State getGameState() {
		return state; 
	}

	public void clear() {
		board = new boolean[n][n];
		nQueens = 0;
		state = State.INCOMPLETE;
		fireQueensModelEvent(state, -1,'x');
	}

	private State computeGameState(int r, int c) {
		if (state == State.INVALID) {
			if (nQueens == n) {
				state = State.COMPLETE;
			} else {
				state = State.INCOMPLETE;
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (board[i][j] && (inSameCol(i, j) || inSameRow(i, j) || inSameDiag(i, j))) {
						state = State.INVALID;
					}
				}
			}
		} else if (inSameCol(r, c) || inSameRow(r, c) || inSameDiag(r, c)) {
			state = State.INVALID; 
		} else if (nQueens == n) {
			state = State.COMPLETE;
		} else {
			state = State.INCOMPLETE; 
		}
		return state;
	}

	private boolean inSameCol(int r, int c) {
		for (int i = 0; i < n; i++) {
			if (i != r && board[i][c]) {
				return true; 
			}
		}
		return false; 
	}
	
	private boolean inSameRow(int r, int c) {
		for (int j = 0; j < n; j++) {
			if (j != c && board[r][j]) {
				return true; 
			}
		}
		return false; 
	}
	
	private boolean inSameDiag(int r, int c) {
		for (int x = 1; x < n; x++) {
			int rU = r - x; 
			int rL = r + x; 
			int cL = c - x; 
			int cR = c + x; 
			if (isValidPos(rU, cL) && board[rU][cL]) {
				return true; 
			}
			if (isValidPos(rU, cR)  && board[rU][cR]) {
				return true; 
			}
			if (isValidPos(rL, cL) && board[rL][cL]) {
				return true; 
			}
			if (isValidPos(rL, cR) && board[rL][cR]) {
				return true; 
			}
		}
		return false; 
	}
	
	private boolean isValidPos(int r, int c) {
		return r >= 0 && r < n && c >= 0 && c < n; 
	}
	
	// TODO implement model according MVC pattern
	private final List<QueensListener> listeners = new ArrayList<>();

	public void addQueensModelListener(QueensListener listener){
		listeners.add(listener);
	}
	public void removeQueensModelListener(QueensListener listener){
		listeners.remove(listener);
	}
	protected void fireQueensModelEvent(State state, int row, char col){
		QueensEvent event = new QueensEvent(this, state, row, col);
		for(QueensListener listener: listeners){
			listener.queensEvent(event);
		}
	}

}
