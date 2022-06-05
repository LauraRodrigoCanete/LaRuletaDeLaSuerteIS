package view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import control.GUIController;
import model.Game;
import model.ModelStatus;
import model.Player;
import model.RDLSObserver;
import model.record.Records;

public class StatusBar extends JPanel implements RDLSObserver {
	
	private static final long serialVersionUID = 1L;
	
	private static final String PLAYER_TURN_MSG = "Turno de: ";
	
	private static final String THROW_ROULETTE_MSG = "Tirando ruleta...";
	private static final String CASILLA_OBTENIDA_MSG = "Has caído en ";
	
	private static final String TIMES_MSG = "La '%s' seleccionada aparece ";
	private static final String FAIL_MSG = "La '%s' no tiene apariciones en el panel. Pierdes el turno.";
	private static final String WON_POINTS_MSG = "Has obtenido ";
	private static final String LOST_POINTS_MSG = "Has perdido ";
	
	private static final String RESET_MSG = "Se ha reseteado el juego.";
	private static final String EXIT_MSG = "Has salido de la partida.";
	private static final String WINNING_MSG = "¡Enhorabuena, %s! Has ganado la partida. Has obtenido un total de %s puntos.";
	
	private JLabel infoLabel, turnoLabel;

	public StatusBar(GUIController _ctrl) {
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		JPanel info = new JPanel();
		info.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel turno = new JPanel();
		turno.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		infoLabel = new JLabel();
		info.add(infoLabel);
		
		turnoLabel = new JLabel();
		turno.add(turnoLabel);
		
		this.add(turno);
		JSeparator sep = new JSeparator(JSeparator.VERTICAL);
		sep.setPreferredSize(new Dimension(10,20));
		this.add(sep);
		this.add(info);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setVisible(true);
	}

	@Override
	public void onGameStart(Game game, Player jugTurno) {
		turnoLabel.setText(PLAYER_TURN_MSG + jugTurno);
		if(game.getStatus() == ModelStatus.RULETA_TIRADA)
			turnoLabel.setText(CASILLA_OBTENIDA_MSG + game.getCasilla());
		else
			turnoLabel.setText("Tira de la ruleta!");
	}

	@Override
	public void onGameEnd(Game game, Player ganador) {
		infoLabel.setText(String.format(WINNING_MSG, ganador, ganador.getPoints()));
	}

	@Override
	public void onPlayStart(Game game) {}

	@Override
	public void onTurnChanged(Game game) {
		turnoLabel.setText(PLAYER_TURN_MSG + game.getCurrentPlayer().getName());
		infoLabel.setText(infoLabel.getText() + " El turno cambia a " + game.getCurrentPlayer().getName());
	}

	@Override
	public void onRouletteThrown(String infoCasilla, int angulo, int desplazamiento, boolean skip) {
		infoLabel.setText(String.format("%s. %s",THROW_ROULETTE_MSG, CASILLA_OBTENIDA_MSG + infoCasilla));
		
	}

	@Override
	public void onAttemptMade(Game game, char letra, int veces, int points) {
		if(veces == 0) infoLabel.setText(String.format(FAIL_MSG, letra));
		else {
			infoLabel.setText(String.format(TIMES_MSG, letra));
			infoLabel.setText(infoLabel.getText() + veces);
			infoLabel.setText(infoLabel.getText() + (veces == 1 ? " vez. " : " veces. "));
			if(points > 0)
				infoLabel.setText(infoLabel.getText() + WON_POINTS_MSG + points + " puntos");
			else if (points < 0)
				infoLabel.setText(infoLabel.getText() + LOST_POINTS_MSG + points + " puntos");
		}
	}

	@Override
	public void onHelpRequested() {}

	@Override
	public void onReset(Game game, Player jugTurno) {
		infoLabel.setText(RESET_MSG);
		turnoLabel.setText(PLAYER_TURN_MSG + jugTurno);
	}

	@Override
	public void onExit() {
		infoLabel.setText(EXIT_MSG);
	}

	@Override
	public void onMenuOpened() {}

	@Override
	public void onRecordsOpened(Records _records) {}

	@Override
	public void onRegister(ModelStatus status) {
		infoLabel.setText("¡Bienvenido!");
	}

	@Override
	public void notify(ModelStatus status, String info) {}

	@Override
	public void onNewRecord() {
		infoLabel.setText("¡Nuevo récord!");
	}

	@Override
	public void onActionEnd() {}

	@Override
	public void onServerAction(Game game, boolean isYourTurn) {
		turnoLabel.setText(PLAYER_TURN_MSG + game.getCurrentPlayer().getName());
	}

}
