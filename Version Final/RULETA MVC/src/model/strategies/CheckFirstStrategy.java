package model.strategies;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import misc.CharUtilities;
import model.Game;

public class CheckFirstStrategy implements ChooseLetterStrategy {

	// Elige siempre una letra que esté en el panel y no se haya dicho aún
	
	@Override
	public Character chooseNextVowel(Game game) {
		Map<Character, Integer> apariciones = game.getApariciones();//letra, cuantas veces quedan en el panel
		Set<Character> vocalesUsadas = game.getVocalesUsadas();
		boolean hayLetraPosible = false;
		for (int i = 0; i < 5 && !hayLetraPosible; ++i) {
			Character letra = CharUtilities.toUpper(CharUtilities.nth_vowel(i));
			if(apariciones.containsKey(letra) && apariciones.get(letra) > 0)
				hayLetraPosible = true;
		}
		Random rand = new Random();
		Character c;
		do {
			c = CharUtilities.toUpper(CharUtilities.nth_vowel(rand.nextInt(5)));
			if (!hayLetraPosible && !vocalesUsadas.contains(c)) return c;
		} while (!apariciones.containsKey(c) || apariciones.get(c) == 0);
		return c;
	}

	@Override
	public Character chooseNextConsonant(Game game) {
		Map<Character, Integer> apariciones = game.getApariciones();
		Set<Character> consonantesUsadas = game.getConsonantesUsadas();
		boolean hayLetraPosible = false;
		for (int i = 0; i < 22 && !hayLetraPosible; ++i) {
			Character letra = CharUtilities.toUpper(CharUtilities.nth_consonant(i));
			if(apariciones.containsKey(letra) && apariciones.get(letra) > 0)
				hayLetraPosible = true;
		}
		Random rand = new Random();
		Character c;
		do {
			c = CharUtilities.toUpper(CharUtilities.nth_consonant(rand.nextInt(22)));
			if (!hayLetraPosible && !consonantesUsadas.contains(c)) return c;
		} while (!apariciones.containsKey(c) || apariciones.get(c) == 0);
		return c;
	}

	@Override
	public boolean isIA() {
		return true;
	}

}
