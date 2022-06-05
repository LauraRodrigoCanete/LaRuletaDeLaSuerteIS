package model.strategies;

import model.Game;

public interface ChooseLetterStrategy {
	// Estrategia para decidir qué letra escoger
	public Character chooseNextVowel(Game game);
	public Character chooseNextConsonant(Game game);
	public boolean isIA();
}
