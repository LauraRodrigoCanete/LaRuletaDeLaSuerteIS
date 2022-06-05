package model.strategies;

import model.Game;

public class ManualStrategy implements ChooseLetterStrategy, ChooseNextActionStrategy {

	// Manual
	
	@Override
	public boolean chooseNextAction(Game game, int points) {
		return false;
	}

	@Override
	public Character chooseNextVowel(Game game) {
		return null;
	}

	@Override
	public Character chooseNextConsonant(Game game) {
		return null;
	}

	@Override
	public boolean isIA() {
		return false;
	}

}
