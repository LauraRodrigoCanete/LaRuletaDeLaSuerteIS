package model.strategies;

import java.util.Random;

import model.Game;
import model.ModelStatus;

public class SometimesBuyVowelStrategy implements ChooseNextActionStrategy {

	// Elige aleatoriamente si comprar vocal o elegir consonante, dependiendo de las vocales y consonantes que ya se hayan probado
	
	@Override
	public boolean chooseNextAction(Game game, int points) {
		ModelStatus s = game.getStatus();
		if (s == ModelStatus.COMIENZO_DE_JUGADA) {
			int vocales = 5 - game.getVocalesUsadas().size(), consonantes = 22 - game.getConsonantesUsadas().size();
			Random rand = new Random();
			int n = rand.nextInt(consonantes + vocales);
			if (n < vocales && points >= game.getCosteVocal()) {
				game.comprarVocal(game.elegirVocal());
			} else {
				game.tirarRuleta();
			}
		} else if (s == ModelStatus.RULETA_TIRADA) {
			game.elegirConsonante();
		}
		return true;
	}

}
