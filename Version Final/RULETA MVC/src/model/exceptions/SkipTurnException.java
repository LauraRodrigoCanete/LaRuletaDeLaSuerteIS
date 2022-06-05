package model.exceptions;

public class SkipTurnException extends GameException {
	
	private static final long serialVersionUID = 1L;

	public SkipTurnException(String str) {
		super(str);
	}
}
