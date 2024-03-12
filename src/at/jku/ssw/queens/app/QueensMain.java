package at.jku.ssw.queens.app;

import at.jku.ssw.queens.InvalidMoveException;
import at.jku.ssw.queens.InvalidPositionException;
import at.jku.ssw.queens.Queens;
import at.jku.ssw.queens.State;
import inout.In;
import inout.Out;

public class QueensMain {

	public static void main(String[] args) throws Exception {
		
		Out.print("Waehle Groesse: ");
		int n = In.readInt(); 
		Queens game = Queens.create(n); 

		Out.println(); 
		printBoard(game);
		
		boolean terminate = false; 
		while (game.getGameState() != State.COMPLETE && !terminate) {
			Out.println(); 
			Out.print("Waehle Setzen (S) | Entfernen (E) | Clear (C) | Quit (Q): ");
			char cmd = Character.toUpperCase(In.readChar()); 
			if (cmd == 'S') {
				Out.print("Setze Queen auf Zeile: "); 
				int row = In.readInt(); 
				Out.print("                Spalte: "); 
				char col = Character.toUpperCase(In.readChar()); 
				try {
					game.setQueen(row, col); 
					Out.println(); 
					printBoard(game);
				} catch (InvalidPositionException ipe) {
					Out.println(String.format("Position %d-%c ist ungueltig!",ipe.getRow(), ipe.getCol()));
				} catch (InvalidMoveException ime) {
					Out.println(String.format("Position %d-%c ist besetzt!", ime.getRow(), ime.getCol()));
				} catch (UnsupportedOperationException uoe) {
					Out.println("Setzen bei ungueltiger Stellung nicht möglich!");
				}
			} else if (cmd == 'E') {
				Out.print("Entferne Queen von Zeile: "); 
				int row = In.readInt();
				Out.print("                   Spalte: "); 
				char col = Character.toUpperCase(In.readChar()); 
				try {
					game.removeQueen(row, col); 
					Out.println(); 
					printBoard(game);
				} catch (InvalidPositionException ipe) {
					Out.println(String.format("Position %d-%c ist ungueltig!", ipe.getRow(), ipe.getCol()));
				} catch (InvalidMoveException ime) {
					Out.println(String.format("Position %d-%c ist nicht besetzt!", ime.getRow(), ime.getCol()));
				}
			} else if (cmd == 'C') {
				game.clear();
				Out.println();
				printBoard(game);
			} else if (cmd == 'Q') {
				terminate = true;
			}	else {
				Out.println("Ungueltiges Kommando!");
			}
		}
	}
	
	private static void printBoard(Queens game) throws Exception {
		Out.print("   |");
		for (int col = 0; col < game.getSize(); col++) {
			Out.print(String.format(" %c ", (char)('A' + col)));
		}
		Out.println("|"); 
		Out.print("---|");
		for (int col = 0; col < game.getSize(); col++) {
			Out.print("---"); 
		}
		Out.println("|");

		for (int row = 1; row <= game.getSize(); row++) {
			Out.print(String.format(" %d |", row));
			for (int col = 0; col < game.getSize(); col++) {
				Out.print(game.hasQueen(row, (char)('A' + col)) ? " Q " : " . ");
			}
			Out.println("|"); 
		}
		Out.print("---|");
		for (int col = 0; col < game.getSize(); col++) {
			Out.print("---"); 
		}
		Out.println("|");  
		Out.println(); 
		Out.println("Stand: " + 
				switch (game.getGameState()) {
					case INCOMPLETE -> "unvollstaendig"; 
					case COMPLETE -> "vollstaendig";
					case INVALID -> "ungueltig";  
				}); 

	}

}
