package view;

import control.ConsoleController;
import model.Game;
import model.ModelStatus;
import model.Player;
import model.RDLSObserver;
import model.record.Records;

public class GamePrinter implements RDLSObserver {
	//Se ocupa de toda la información que tenga que ver con jugar una partida, desde que empieza
	//hasta que termina
	
	private static final String CATEGORY_MSG = "CATEGOR�A: ";
	private static final String CLUE_MSG = "PISTA: ";
	private static final String PLAYER_POINTS_MSG = "PUNTUACIONES:";
	private static final String PLAYER_TURN_MSG = "TURNO DE ";
	private static final String BOTE_MSG = "BOTE ACTUAL: ";
	
	private static final String THROW_ROULETTE_MSG = "Tirando ruleta...";
	private static final String CASILLA_OBTENIDA_MSG = "Has caído en ";
	
	private static final String TIMES_MSG = "La '%s' seleccionada aparece ";
	private static final String FAIL_MSG = "La '%s' no tiene apariciones en el panel. Pierdes el turno.";
	private static final String WON_POINTS_MSG = "Has obtenido ";
	private static final String LOST_POINTS_MSG = "Has perdido ";
	
	private static final String RESET_MSG = "Se ha reseteado el juego.";
	private static final String EXIT_MSG = "Has salido de la partida.";
	private static final String WINNING_MSG = "¡Enhorabuena, %s! Has ganado la partida. Has obtenido un total de %s puntos.";
	
	
	public GamePrinter(ConsoleController ctrl) {
		ctrl.addObserver(this);
	}

	@Override
	public void onGameStart(Game game, Player jugTurno) {
		printGame(game);
		System.out.print(ConsoleView.PROMPT);
	}

	@Override
	public void onGameEnd(Game game, Player ganador) {
		printGame(game);
		System.out.println(String.format(WINNING_MSG, ganador, ganador.getPoints()));
		System.out.print(ConsoleView.PROMPT);
	}

	@Override
	public void onPlayStart(Game game) {
		printGame(game);
		System.out.print(ConsoleView.PROMPT);
	}
	
	@Override
	public void onTurnChanged(Game game) {
		System.out.println("El turno cambia a " + game.getCurrentPlayer().getName());
	}

	@Override
	public void onRouletteThrown(String infoCasilla, int angulo, int desplazamiento, boolean skip) {
		System.out.println(THROW_ROULETTE_MSG);
		System.out.println(CASILLA_OBTENIDA_MSG + infoCasilla);
		if(!skip) System.out.print(ConsoleView.PROMPT);
	}

	@Override
	public void onAttemptMade(Game game, char letra, int veces, int points) {
		if(veces == 0) System.out.println(String.format(FAIL_MSG, letra));
		else {
			System.out.print(String.format(TIMES_MSG, letra));
			System.out.print(veces);
			System.out.println(veces == 1 ? " vez" : " veces");
			if(points > 0)
				System.out.println(WON_POINTS_MSG + points + " puntos");
			else if (points < 0)
				System.out.println(LOST_POINTS_MSG + points + " puntos");
		}
		if(game.getStatus() != ModelStatus.JUEGO_ACABADO && veces != 0)
			System.out.print(ConsoleView.PROMPT);
	}

	@Override
	public void onReset(Game game, Player jugTurno) {
		System.out.println(RESET_MSG);
		printGame(game);
		System.out.print(ConsoleView.PROMPT);
	}

	@Override
	public void onExit() {
		System.out.println(EXIT_MSG);
	}

	@Override
	public void onMenuOpened() {}

	@Override
	public void onRecordsOpened(Records _records) {}

	@Override
	public void onRegister(ModelStatus status) {}

	@Override
	public void notify(ModelStatus status, String info) {}
	
	private void printGame(Game game) {
		StringBuilder str = new StringBuilder();
		
		str.append(ConsoleView.LINE_SEPARATOR)
		.append(PLAYER_TURN_MSG).append(game.getCurrentPlayer()).append(ConsoleView.LINE_SEPARATOR)
		.append(game.getPanel()).append(ConsoleView.LINE_SEPARATOR)
		.append(showClue(game)).append(ConsoleView.LINE_SEPARATOR)
		.append(PLAYER_POINTS_MSG).append(ConsoleView.LINE_SEPARATOR)
		.append(game.getMarcador()).append(ConsoleView.LINE_SEPARATOR);
		if(game.hayBote())
			str.append(BOTE_MSG).append(game.getBote()).append(ConsoleView.LINE_SEPARATOR);
		
		if(game.getStatus() == ModelStatus.RULETA_TIRADA) {
			str.append(CASILLA_OBTENIDA_MSG + game.getCasilla()).append(ConsoleView.LINE_SEPARATOR);
		}
		
		System.out.print(str.toString());
	}
	
	private String showClue(Game game) {
		StringBuilder str = new StringBuilder();
		
		str.append(ConsoleView.LINE_SEPARATOR)
		.append(String.format("%s%s", CATEGORY_MSG, game.getCategoria())).append(ConsoleView.LINE_SEPARATOR)
		.append(String.format("%s%s", CLUE_MSG, game.getPista()));
		
		return str.toString();
	}

	@Override
	public void onNewRecord() {
		System.out.println("¡Nuevo record!");
	}

	@Override
	public void onHelpRequested() {}

	@Override
	public void onActionEnd() {}

	@Override
	public void onServerAction(Game game, boolean isYourTurn) {
		//La funcionalidad de red no está disponible en modo CONSOLE
	}

}
