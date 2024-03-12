package at.jku.ssw.queens;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueensTest {

	private static Queens queens;
	private static final int N = 4;

	@BeforeAll
	static void setUp() {
		queens = Queens.create(N);
	}

	@BeforeEach
	void clear() {
		queens.clear();
	}

	@Test
	void testSize() {
		assertEquals(N, queens.getSize());
	}

	@Test
	void testNQueens() throws QueensException {
		assertEquals(0, queens.getNQueens());
		queens.setQueen(1, 'B');
		assertEquals(1, queens.getNQueens());
		queens.setQueen(2, 'D');
		assertEquals(2, queens.getNQueens());
		queens.removeQueen(1, 'B');
		assertEquals(1, queens.getNQueens());
		queens.setQueen(3, 'A');
		assertEquals(2, queens.getNQueens());
		queens.setQueen(4, 'C');
		assertEquals(3, queens.getNQueens());
		queens.clear();
		assertEquals(0, queens.getNQueens());
	}

	@Test
	void testSetQueen() throws QueensException {
		queens.setQueen(1, 'A');
		assertTrue(queens.hasQueen(1, 'A'));
		queens.setQueen(2, 'C');
		assertTrue(queens.hasQueen(1, 'A'));
		assertTrue(queens.hasQueen(2, 'C'));
	}

	@Test
	void testRemoveQueen() throws QueensException {
		queens.setQueen(1, 'A');
		queens.setQueen(2, 'C');
		queens.removeQueen(2, 'C');
		assertFalse(queens.hasQueen(2, 'C'));
		queens.removeQueen(1, 'A');
		assertFalse(queens.hasQueen(1, 'A'));
		assertFalse(queens.hasQueen(2, 'C'));
	}

	@Test
	void testGameState() throws QueensException {
		assertEquals(State.INCOMPLETE, queens.setQueen(1, 'A'));
		assertEquals(State.INCOMPLETE, queens.getGameState());
		assertEquals(State.INCOMPLETE, queens.setQueen(2, 'D'));
		assertEquals(State.INCOMPLETE, queens.getGameState());
		assertEquals(State.INCOMPLETE, queens.setQueen(3, 'B'));
		assertEquals(State.INCOMPLETE, queens.getGameState());
		assertEquals(State.INVALID, queens.setQueen(4, 'C'));
		assertEquals(State.INVALID, queens.getGameState());
		assertEquals(State.INCOMPLETE, queens.removeQueen(3, 'B'));
		assertEquals(State.INCOMPLETE, queens.getGameState());
		assertEquals(State.INVALID, queens.setQueen(3, 'A'));
		assertEquals(State.INVALID, queens.getGameState());
		assertEquals(State.INCOMPLETE, queens.removeQueen(1, 'A'));
		assertEquals(State.INCOMPLETE, queens.getGameState());
		assertEquals(State.COMPLETE, queens.setQueen(1, 'B'));
		assertEquals(State.COMPLETE, queens.getGameState());
		assertThrows(UnsupportedOperationException.class, () -> queens.removeQueen(4, 'C'));
	}

	@Test
	void testClear() throws InvalidPositionException {
		for (int r = 1; r <= N; r++) {
			for (int c = 0; c < N; c++) {
				assertFalse(queens.hasQueen(r, (char)('A' + c)));
			}
		}
		assertEquals(0, queens.getNQueens());
		assertEquals(State.INCOMPLETE, queens.getGameState());
	}

	@Test
	void testInvalidSize() {
		assertThrows(IllegalArgumentException.class, () -> Queens.create(0));
	}

	@Test
	void testInvalidPositions() {
		assertThrows(InvalidPositionException.class, () -> queens.setQueen(0, 'A'));
		assertThrows(InvalidPositionException.class, () -> queens.setQueen(N + 1, 'A'));
		assertThrows(InvalidPositionException.class, () -> queens.setQueen(1, (char)('A' - 1)));
		assertThrows(InvalidPositionException.class, () -> queens.setQueen(1, (char)('A' + N)));
		assertThrows(InvalidPositionException.class, () -> queens.removeQueen(0, 'A'));
		assertThrows(InvalidPositionException.class, () -> queens.removeQueen(N + 1, 'A'));
		assertThrows(InvalidPositionException.class, () -> queens.removeQueen(1, (char)('A' - 1)));
		assertThrows(InvalidPositionException.class, () -> queens.removeQueen(1, (char)('A' + N)));
		assertThrows(InvalidPositionException.class, () -> queens.hasQueen(0, 'A'));
		assertThrows(InvalidPositionException.class, () -> queens.hasQueen(N + 1, 'A'));
		assertThrows(InvalidPositionException.class, () -> queens.hasQueen(1, (char)('A' - 1)));
		assertThrows(InvalidPositionException.class, () -> queens.hasQueen(1, (char)('A' + N)));
	}

	@Test
	void testInvalidSetQueen() throws QueensException {
		queens.setQueen(1, 'A');
		assertThrows(InvalidMoveException.class, () -> queens.setQueen(1, 'A'));
	}

	@Test
	void testInvalidRemoveQueen() {
		assertThrows(InvalidMoveException.class, () -> queens.removeQueen(1, 'A'));
	}

	@Test
	void testInvalidGameStateSameCol() throws QueensException {
		assertEquals(State.INCOMPLETE, queens.setQueen(1, 'A'));
		assertEquals(State.INCOMPLETE, queens.getGameState());
		assertEquals(State.INVALID, queens.setQueen(2, 'A'));
		assertEquals(State.INVALID, queens.getGameState());
		assertThrows(UnsupportedOperationException.class, () -> queens.setQueen(3, 'D'));
	}

	@Test
	void testInvalidGameStateSameRow() throws QueensException {
		assertEquals(State.INCOMPLETE, queens.setQueen(1, 'A'));
		assertEquals(State.INCOMPLETE, queens.getGameState());
		assertEquals(State.INVALID, queens.setQueen(1, 'B'));
		assertEquals(State.INVALID, queens.getGameState());
		assertThrows(UnsupportedOperationException.class, () -> queens.setQueen(4, 'C'));
	}

	@Test
	void testInvalidGameStateSameDiag() throws QueensException {
		assertEquals(State.INCOMPLETE, queens.setQueen(1, 'A'));
		assertEquals(State.INCOMPLETE, queens.getGameState());
		assertEquals(State.INVALID, queens.setQueen(2, 'B'));
		assertEquals(State.INVALID, queens.getGameState());
		assertThrows(UnsupportedOperationException.class, () -> queens.setQueen(3, 'D'));
	}

}
