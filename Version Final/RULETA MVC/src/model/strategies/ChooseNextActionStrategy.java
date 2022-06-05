package model.strategies;

import model.Game;

public interface ChooseNextActionStrategy {
	// Estrategia para decidir qué acción realizar a continuación
	public boolean chooseNextAction(Game game, int points);
}
