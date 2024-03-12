package at.jku.ssw.queens;

@SuppressWarnings("serial")
public class QueensException extends Exception {
	
	private final Queens game;

	public QueensException(String message, Queens game) {
		super();
		this.game = game;
	} 
	
	public Queens getGame() {
		return game;
	}
	
}
