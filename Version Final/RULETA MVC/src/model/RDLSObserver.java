package model;

import model.record.Records;


//Estos son los métodos de notificación del patrón MVC
public interface RDLSObserver {
	void onGameStart(Game game, Player jugTurno);
	void onGameEnd(Game game, Player ganador);
	
	void onPlayStart(Game game); //Al empezar una jugada (Desde introducir comando hasta que sea procesado y produzca un resultado)
	void onTurnChanged(Game game);
	void onRouletteThrown(String infoCasilla, int angulo, int desplazamiento, boolean skip);
	void onAttemptMade(Game game, char letra, int veces, int points);
	
	void onHelpRequested();
	void onReset(Game game, Player jugTurno);
	void onExit();
	
	void onMenuOpened();
	void onRecordsOpened(Records _records);
	
	void onRegister(ModelStatus status);
	void notify(ModelStatus status, String info);
	void onNewRecord();
	
	// IA
	void onActionEnd();
	
	// RED
	// Cuando se actualiza el modelo debido a la acción de otro jugador en una partida en red
	void onServerAction(Game game, boolean isYourTurn); 
}
