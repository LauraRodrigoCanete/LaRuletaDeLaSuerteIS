package model.strategies;

import java.util.Random;
import java.util.Set;

import misc.CharUtilities;
import model.Game;

public class RandomNotUsedLetterStrategy implements ChooseLetterStrategy {

	// Elige una letra aleatoria de las que todavía no se hayan dicho
	
	@Override
	public Character chooseNextVowel(Game game) {
		Set<Character> vocales = game.getVocalesUsadas();
		Random rand = new Random();
		Character c;
		do {
			c = CharUtilities.nth_vowel(rand.nextInt(5));
		} while (vocales.contains(CharUtilities.toUpper(c)) && vocales.size() != 5);
		return c;
	}

	@Override
	public Character chooseNextConsonant(Game game) {
		Set<Character> consonantes = game.getConsonantesUsadas();
		Random rand = new Random();
		Character c;
		do {
			c = CharUtilities.nth_consonant(rand.nextInt(22));
		} while (consonantes.contains(CharUtilities.toUpper(c)) && consonantes.size() != 22);
		return c;
	}

	@Override
	public boolean isIA() {
		return true;
	}

}
