package view;

import control.ConsoleController;
import model.Game;
import model.ModelStatus;
import model.Player;
import model.RDLSObserver;
import model.record.Records;

public class MenuPrinter implements RDLSObserver {

	private ConsoleController _ctrl;
	
	public MenuPrinter(ConsoleController ctrl) {
		_ctrl = ctrl;
		_ctrl.addObserver(this);
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
	public void onMenuOpened() {
		System.out.println("¿Qué desea hacer?");
		System.out.println("	- Nueva Partida -> [j]ugar <nºjugadores> (bote?)<SI/NO> [nombres]");
		System.out.println("	- Cargar Partida -> cargar | [l]");
		System.out.println("	- Consultar Records -> [rec]ords");
		System.out.print(ConsoleView.PROMPT);
	}

	@Override
	public void onRecordsOpened(Records _records) {}

	@Override
	public void onRegister(ModelStatus status) {
		// Por si al registrarse el estado del juego no es MENU. El caso no se da pero podría 
		// darse si quisiésemos poder registrar nuevos observadores en medio de la ejecución.
		if(status == ModelStatus.MENU) { 
			System.out.println("Bienvenido");
			this.onMenuOpened();
		}
	}

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
