package view;

import control.ConsoleController;
import model.Game;
import model.ModelStatus;
import model.Player;
import model.RDLSObserver;
import model.record.Records;

public class ConsoleView implements RDLSObserver {
	//ConsoleView es la clase central de la vista por consola.
	
	static final String PROMPT = " > ";
	static final String LINE_SEPARATOR = System.lineSeparator();
	
	//Se encarga de mantener las instancias del resto de componentes de la vista
	private GamePrinter _gamePrinter; 
	private MenuPrinter _menuPrinter;
	private RecordPrinter _recordPrinter;
	
	private String _help;

	public ConsoleView(ConsoleController controller) {
		_help = controller.getHelp();
		_gamePrinter = new GamePrinter(controller);
		_menuPrinter = new MenuPrinter(controller);
		_recordPrinter = new RecordPrinter(controller);
		
		controller.addObserver(this);
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
	public void onRecordsOpened(Records _records) {}

	@Override
	public void onRegister(ModelStatus status) {}

	@Override
	public void notify(ModelStatus status, String info) {
		System.out.println(info);
		System.out.print(PROMPT);
	}
	
	@Override
	public void onPlayStart(Game game) {}

	@Override
	public void onNewRecord() {}

	@Override
	public void onHelpRequested() {
		System.out.println(_help);
		System.out.print(PROMPT);
	}

	@Override
	public void onActionEnd() {}

	@Override
	public void onServerAction(Game game, boolean isYourTurn) {
		//La funcionalidad de red no está disponible en modo CONSOLE
	}
}
