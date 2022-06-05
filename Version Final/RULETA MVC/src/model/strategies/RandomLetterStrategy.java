package model.strategies;

import java.util.Random;

import misc.CharUtilities;
import model.Game;

public class RandomLetterStrategy implements ChooseLetterStrategy {

	// Elige una letra aleatoria.
	
	@Override
	public Character chooseNextVowel(Game game) {
		Random rand = new Random();
		return CharUtilities.nth_vowel(rand.nextInt(5));
	}

	@Override
	public Character chooseNextConsonant(Game game) {
		Random rand = new Random();
		return CharUtilities.nth_consonant(rand.nextInt(22));
	}

	@Override
	public boolean isIA() {
		return true;
	}

}
