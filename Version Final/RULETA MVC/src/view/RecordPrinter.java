package view;

import control.ConsoleController;
import model.Game;
import model.ModelStatus;
import model.Player;
import model.RDLSObserver;
import model.record.Record;
import model.record.Records;

public class RecordPrinter implements RDLSObserver {
	
	public RecordPrinter(ConsoleController ctrl) {
		ctrl.addObserver(this);
	}

	@Override
	public void onGameStart(Game game, Player jugTurno) {}

	@Override
	public void onGameEnd(Game game, Player ganador) {}

	@Override
	public void onTurnChanged(Game game) {}

	@Override
	public void onRouletteThrown(String infoCasilla, int angulo, int desplazamiento, boolean skip) {}

	@Override
	public void onAttemptMade(Game game, char letra, int veces, int points) {}

	@Override
	public void onReset(Game game, Player jugTurno) {}

	@Override
	public void onExit() {}

	@Override
	public void onMenuOpened() {}

	@Override
	public void onRecordsOpened(Records _records) {
		System.out.println("RECORDS :");
		int i = 1;
		for(Record r : _records.getRecords()) {
			System.out.print("    " + i + "- "); 
			System.out.print(r.getPlayerName());
			System.out.print(" : ");
			System.out.print(r.getScore());
			System.out.println(" puntos.");
			++i;
		}
		System.out.print(ConsoleView.PROMPT);
	}

	@Override
	public void onRegister(ModelStatus status) {}

	@Override
	public void notify(ModelStatus status, String info) {}

	@Override
	public void onPlayStart(Game game) {}

	@Override
	public void onNewRecord() {}

	@Override
	public void onHelpRequested() {}

	@Override
	public void onActionEnd() {}
	
	@Override
	public void onServerAction(Game game, boolean isYourTurn) {
		//La funcionalidad de red no está disponible en modo CONSOLE
	}

}
