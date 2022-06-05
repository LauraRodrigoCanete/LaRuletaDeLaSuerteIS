package model.strategies;

import model.Game;
import model.ModelStatus;

public class AlwaysBuyVowelStrategy implements ChooseNextActionStrategy {
	
	// Elige comprar vocal siempre que todavía queden vocales por probar
	
	@Override
	public boolean chooseNextAction(Game game, int points) {
		ModelStatus s = game.getStatus();
		if (s == ModelStatus.COMIENZO_DE_JUGADA) {
			if (points >= game.getCosteVocal() && game.getVocalesUsadas().size() < 5) {
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
