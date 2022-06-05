package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Net.Client;
import Net.Server;
import model.record.Record;
import model.record.Records;

public class Model implements Observable<RDLSObserver> {

	private static final String NOT_IN_GAME_MSG = "[ERROR] Solo se puede hacer eso si se está jugando una partida";
	private static final String NOT_IN_MENU_MSG = "[ERROR] Solo se puede hacer eso desde el menu";
	private static final String LOAD_ERROR_MSG = "[ERROR] No se pudo cargar la partida";
	private static final String GAME_ENDED_MSG = "[ERROR] El juego ya se acabó";

	private List<RDLSObserver> _observers;
	private Status _status;
	private Game _game;
	private Records _records;
	private Server server;
	
	private boolean _wait = false;
	
	public Model() {
		_observers = new ArrayList<RDLSObserver>();
		_status = new Status(ModelStatus.MENU);
		_game = null;
		_records = null;
	}

	@Override
	public void addObserver(RDLSObserver o) {
		if (o != null && !_observers.contains(o)) {
			_observers.add(o);
			o.onRegister(_status.getStatus());
		}
	}

	@Override
	public void removeObserver(RDLSObserver o) {
		if (o != null)
			_observers.remove(o);
	}

	public void notifyError(String str, boolean fatal) {
		for (RDLSObserver o : _observers)
			o.notify(_status.getStatus(), str);
	}

	public void help() {
		for (RDLSObserver o : _observers)
			o.onHelpRequested();
	}

	// RECORDS
	public void loadRecords(List<Record> records) {
		_records = new Records(records, _observers, _status);
	}

	public void showRecords() {
		if (_status.getStatus() == ModelStatus.MENU) {
			for (RDLSObserver o : _observers) {
				o.onRecordsOpened(_records);
			}
		} else
			notifyError(NOT_IN_MENU_MSG, false);
	}

	// GAME
	public void newGame(int numPlayers, boolean bote, List<String> names) {
		if (_status.getStatus() == ModelStatus.MENU) {
			try {
				_game = new Game(numPlayers, bote, names, _observers, _status);
				// TEST
				if(!_wait) _game.setSlowMode();
				_game.start(false);
			} catch (IOException | IllegalArgumentException e) {
				_status.setStatus(ModelStatus.MENU);
				notifyError(e.getMessage(), false);
			}
		} else
			notifyError(NOT_IN_MENU_MSG, false);
	}

	public void cargarPartida(File in) throws IOException {
		if (_status.getStatus() == ModelStatus.MENU) {
			try {
				_game = new Game(_observers, _status);
				_game.cargarPartida(in);
				_game.start(true);
			} catch (IOException e) {
				_game = null;
				_status.setStatus(ModelStatus.MENU);
				throw new IOException(LOAD_ERROR_MSG);
			}
		} else
			notifyError(NOT_IN_MENU_MSG, false);
	}

	public void exitGame() {
		if (_game == null)
			notifyError(NOT_IN_GAME_MSG, false);
		else {
			for (RDLSObserver o : _observers) {
				o.onExit();
			}
			this.exitServer(); //Esta funcion solo hace cosas cuando estamos en juego en red
			_game = null;
			_status.setStatus(ModelStatus.MENU);
			for (RDLSObserver o : _observers) {
				o.onMenuOpened();
			}
		}
	}

	public void comprarVocal(char v) {
		if (_game == null)
			notifyError(NOT_IN_GAME_MSG, false);
		else if (_status.getStatus() == ModelStatus.JUEGO_ACABADO)
			notifyError(GAME_ENDED_MSG, false);
		else {
			_game.comprarVocal(v);
			if (_status.getStatus() == ModelStatus.JUEGO_ACABADO)
				endGame();
		}
	}

	public void elegirConsonante(char c) {
		if (_game == null)
			notifyError(NOT_IN_GAME_MSG, false);
		else if (_status.getStatus() == ModelStatus.JUEGO_ACABADO)
			notifyError(GAME_ENDED_MSG, false);
		else {
			_game.elegirLetra(c);
			if (_status.getStatus() == ModelStatus.JUEGO_ACABADO)
				endGame();
		}
	}

	public void endGame() {
		if(!_game.isCurrentPlayerIA())
			_records.update(_game.getCurrentPlayer());
		for (RDLSObserver o : _observers) {
			o.onGameEnd(_game, _game.getCurrentPlayer());
		}
	}

	public void reset() throws IOException {
		if (_game == null) notifyError(NOT_IN_GAME_MSG, false);
		else _game.reset();
	}

	public void guardarPartida(File out) {
		if (_game == null)
			notifyError(NOT_IN_GAME_MSG, false);
		else if (_status.getStatus() == ModelStatus.JUEGO_ACABADO)
			notifyError(GAME_ENDED_MSG, false);
		else
			_game.guardarPartida(out);
	}

	public void tirarRuleta() {
		if (_game == null)
			notifyError(NOT_IN_GAME_MSG, false);
		else if (_status.getStatus() == ModelStatus.JUEGO_ACABADO)
			notifyError(GAME_ENDED_MSG, false);
		else
			_game.tirarRuleta();
	}

	// IA
	public boolean isCommandNeeded() {
		return  _game == null ||
				_status.getStatus() == ModelStatus.MENU ||
				_status.getStatus() == ModelStatus.JUEGO_ACABADO ||
				!_game.isCurrentPlayerIA();
	}

	public void nextAction() {
		_game.nextAction();
	}
	
	// RED
	public void createServer(int numJugadores, int port) throws IOException {
		this.server = new Server(numJugadores, port, this);
	}
	
	public void joinServer(String ip, int port, String name) throws IOException {
		this._game = new Game(_observers, new Client(ip, port, name, this));
	}
	
	public String serialize() {
		return _game.serialize();
	}
	
	public void deserialize(String msg) {
		_game.deserialize(msg);
	}
	
	public void netStart() {
		_game.start(false);
	}

	public void shutServer() {
		server.shut();
	}
	
	public void serverShutGame() {
		if (_game == null)
			notifyError(NOT_IN_GAME_MSG, false);
		else {
			for (RDLSObserver o : _observers) {
				o.onExit();
			}
			_game.serverShutGame();//para la red
			_game = null;
			_status.setStatus(ModelStatus.MENU);
			for (RDLSObserver o : _observers) {
				o.onMenuOpened();
			}
		}
	}
	
	//Es una funcion que se utiliza en el juego en Red para actualizar bien el game
	//y enviarlo al servidor antes de desconectar al cliente del servidor
	public void exitServer() {
		if (this._game != null)
			this._game.exit();
	}

	// Métodos auxiliares para pruebas

	public String getGanador() {
		if(!_status.getStatus().equals(ModelStatus.JUEGO_ACABADO)) return null;
		else return _game.getCurrentPlayer().toString();
	}
	
	public void setSlowMode() {
		_wait = true;
	}

	public void setFastMode() {
		_wait = false;
	}

}
