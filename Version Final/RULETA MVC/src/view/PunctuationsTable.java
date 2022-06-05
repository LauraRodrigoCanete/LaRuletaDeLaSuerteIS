package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import control.GUIController;
import model.Game;
import model.ModelStatus;
import model.Player;
import model.RDLSObserver;
import model.record.Records;

public class PunctuationsTable extends AbstractTableModel implements RDLSObserver {
	
	private static final long serialVersionUID = 1L;
	
	private String[] _colNames = { "Nombre", "Puntuación" };
	
	private List<Player> _players;

	public PunctuationsTable(GUIController ctrl) {
		ctrl.addObserver(this);
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return _players == null ? 0 : _players.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		Object obj = null;
		switch (colIndex) {
		case 0:
			obj = _players.get(rowIndex).getName();
			break;
		case 1:
			obj = _players.get(rowIndex).getPoints();
			break;
		}
		return obj;
	}

	@Override
	public void onGameStart(Game game, Player jugTurno) {
		update(game);
	}

	@Override
	public void onGameEnd(Game game, Player ganador) {
		update(game);
	}

	@Override
	public void onPlayStart(Game game) {
		update(game);
	}

	@Override
	public void onTurnChanged(Game game) {}

	@Override
	public void onRouletteThrown(String infoCasilla, int angulo, int desplazamiento, boolean skip) {}

	@Override
	public void onAttemptMade(Game game, char letra, int veces, int points) {
		update(game);
	}

	@Override
	public void onHelpRequested() {}

	@Override
	public void onReset(Game game, Player jugTurno) {
		update(game);
	}

	@Override
	public void onExit() {}

	@Override
	public void onMenuOpened() {}

	@Override
	public void onRecordsOpened(Records _records) {}

	@Override
	public void onRegister(ModelStatus status) {}

	@Override
	public void notify(ModelStatus status, String info) {}

	@Override
	public void onNewRecord() {}

	@Override
	public void onActionEnd() {}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	
	@Override
	public void onServerAction(Game game, boolean isYourTurn) {
		update(game);
	}
	
	private void update(Game game) {
		_players = game.getPlayers();
		fireTableDataChanged();
	}

}
