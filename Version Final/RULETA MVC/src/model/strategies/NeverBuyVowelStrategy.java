package model.strategies;

import model.Game;
import model.ModelStatus;

public class NeverBuyVowelStrategy implements ChooseNextActionStrategy {

	// Solo compra vocales si son las únicas letras que quedan
	
	@Override
	public boolean chooseNextAction(Game game, int points) {
		ModelStatus s = game.getStatus();
		if (s == ModelStatus.COMIENZO_DE_JUGADA) {
			if (game.getConsonantesUsadas().size() == 22 && points >= game.getCosteVocal()) {
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
