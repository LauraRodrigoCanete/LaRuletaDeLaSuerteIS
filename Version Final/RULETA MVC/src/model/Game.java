package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import Net.Client;
import Net.Server.SpecialMsg;
import misc.CharUtilities;
import model.exceptions.SkipTurnException;
import model.factories.Factorias;
import model.panel.Diccionario;
import model.panel.Panel;
import model.ruleta.Ruleta;
import model.ruleta.casillas.Casilla;

public class Game {
	
	private static final int COSTE_VOCAL = 25;
	
	private int _numPlayers;
	private Ruleta _ruleta;
	private Random _random;
	private List<Player> _players;
	private Panel _panel;
	private int _currentPlayerIndex;
	private int _bote;
	private boolean _hayBote;
	private Diccionario _diccionario;
	private Status _status;
	
	private List<RDLSObserver> _observers;
	
	private Client _client;
	
	private boolean _slowMode = true; // Por defecto, va lento
	private static final long WAIT_MILLISECONDS = 500L;
	
	//PARA NUEVA PARTIDA
	Game(int players, boolean conBote, List<String> names, List<RDLSObserver> observers, Status status) throws IOException, IllegalArgumentException {
		_numPlayers = players;
		_observers = observers;
		_status = status;
		_hayBote = conBote;
		loadRuleta(conBote);
		createPlayers(names);
		_client = null;
		priv_reset();
	}
	
	//PARA CARGAR PARTIDA
	Game(List<RDLSObserver> observers, Status status) throws IOException {
		_observers = observers;
		_status = status;
		_players = new ArrayList<>();
		_client = null;
		priv_reset();
	}
	
	//PARA RED (PARA ASOCIAR EL GAME AL CLIENTE Y PODER DESERIALIZAR MÁS TARDE
	Game(List<RDLSObserver> observers, Client client) throws IOException{
		_observers = observers;
		this._client  = client;
		_random = new Random();
		_diccionario = new Diccionario();
		_status = new Status(ModelStatus.COMIENZO_DE_JUGADA);
	}

	//INICIALIZACIÓN
	private void createPlayers(List<String> names) {
		if(_numPlayers != names.size()) throw new IllegalArgumentException("Debe haber tantos nombres como jugadores");
		_players = new ArrayList<Player>();
		for(String n: names) {
			_players.add(new Player(n));
		}
	}
	
	//metodo para cargar convenientemente la ruleta del juego
	private void loadRuleta(boolean conBote) throws FileNotFoundException, IllegalArgumentException {
		String fileName = conBote? "resources/casillas_ruleta_bote.json" : "resources/casillas_ruleta.json";
		
		try (InputStream in = new FileInputStream(new File(fileName))) {
			JSONObject obj = new JSONObject( new JSONTokener(in) );
			
			if(!obj.has("CASILLAS"))
				throw new IllegalArgumentException("El fichero para cargar la ruleta no es correcto");
			
			JSONArray arr = obj.getJSONArray("CASILLAS");
			List<Casilla> casillas = new ArrayList<>();
			for(int i = 0; i < arr.length(); ++i)
				casillas.add(Factorias.factoria_casillas.createInstance(arr.getJSONObject(i)));
			
			_ruleta = new Ruleta(casillas);
		}
		catch(JSONException e) {
			throw new IllegalArgumentException("El fichero para cargar la ruleta no es correcto");
		}
		catch(IOException e) {
			throw new FileNotFoundException("El fichero para cargar la ruleta no se ha encontrado");
		}
	}
	
	private void priv_reset() throws IOException {
		_random = new Random();
		if(_diccionario == null) _diccionario = new Diccionario();
		_panel = _diccionario.getRandomPanel(_random);
		_currentPlayerIndex = (int) (_random.nextDouble() * _numPlayers);
		resetPlayerPoints();
		_bote = 0;
		_status.setStatus(ModelStatus.COMIENZO_DE_JUGADA);
	}
	
	//FUNCIONALIDAD
	void start(boolean aMedias) { //booleano para no sobreescribir el estado cargado
		if(!aMedias) _status.setStatus(ModelStatus.COMIENZO_DE_JUGADA);
		for(RDLSObserver o : _observers) {
			o.onGameStart(this, getCurrentPlayer());
		}
		for(RDLSObserver o : _observers)
			o.onActionEnd();
		nextAction();
	}
	
	void reset() throws IOException {
		_panel.reset();
		priv_reset();
		for(RDLSObserver o : _observers)
			o.onReset(this, getCurrentPlayer());
		nextAction();
	}

	public void tirarRuleta() {
		if(_status.getStatus() != ModelStatus.COMIENZO_DE_JUGADA) {
			notifyError("No es el momento para hacer eso", false);
		} else {
			int angulo = _ruleta.getCurrentAngle();
			int desplazamiento = _ruleta.cambiarCasilla(_random.nextDouble());
			Casilla casilla = _ruleta.getCurrentCasilla();
			_status.setStatus(ModelStatus.RULETA_TIRADA);

			try {
				casilla.execute(this);
				for(RDLSObserver o : _observers) {
					o.onRouletteThrown(casilla.toString(), angulo, desplazamiento, false);
				}
			} catch (SkipTurnException e) {
				for(RDLSObserver o : _observers) {
					o.onRouletteThrown(casilla.toString(), angulo, desplazamiento, true);
				}
				skipTurn();
				advance();
			}
		}
		
		for(RDLSObserver o : _observers)
			o.onActionEnd();
		nextAction();
	}
	
	public void comprarVocal(char v) {
		if(_status.getStatus() != ModelStatus.COMIENZO_DE_JUGADA) {
			notifyError("No es el momento para hacer eso", false);
		}
		else if (getCurrentPlayer().getPoints() < getCosteVocal()) {
			for(RDLSObserver o : _observers)
				o.notify(_status.getStatus(), "No tienes suficientes puntos para comprar una vocal");
		}
		else {
			getCurrentPlayer().addPoints(-getCosteVocal());
			elegirLetra(v);
		}
		for(RDLSObserver o : _observers)
			o.onActionEnd();
		nextAction();
	}

	public int getCosteVocal() {
		if (_panel.onlyVowelsLeft()) return 0;
		else return COSTE_VOCAL;
	}

	void elegirLetra(char letra) {
		if(!CharUtilities.isLetter(letra)) {
			notifyError("El caracter debe ser una letra.", false);
		}
		else if(_status.getStatus() == ModelStatus.COMIENZO_DE_JUGADA && !CharUtilities.isVowel(letra)) {
			notifyError("Debes tirar la ruleta antes de elegir consonante", false);
		}
		else if(_status.getStatus() == ModelStatus.RULETA_TIRADA && CharUtilities.isVowel(letra)) {
			notifyError("Una vez tirada la ruleta debes elegir consonante.", false);
		}
		else {
			int veces = _panel.check(letra);
			int puntos = 0;
			if(_status.getStatus() == ModelStatus.RULETA_TIRADA) {
				int puntosIniciales = getCurrentPlayer().getPoints();
				_ruleta.getCurrentCasilla().executeAfterLetterChosen(this, veces);
				puntos = getCurrentPlayer().getPoints() - puntosIniciales;
			}
			for(RDLSObserver o : _observers) {
				o.onAttemptMade(this, letra, veces, puntos);
			}
			if(veces == 0) skipTurn();
			advance();
		}
		
		for(RDLSObserver o : _observers){
			o.onActionEnd();
		}
		nextAction();
	}
	
	private void resetPlayerPoints() {
		for(Player p : _players) p.reset();
	}

	private void skipTurn() {
		_status.setStatus(ModelStatus.COMIENZO_DE_JUGADA);
		_currentPlayerIndex = (_currentPlayerIndex + 1) % _numPlayers;
		for(RDLSObserver o : _observers)
			o.onTurnChanged(this);
	}

	//Es el método que se llama después de elegirLetra o tirarRuleta y avanza el estado del juego
	private void advance() {
		if(_panel.isCompleted()) {
			_status.setStatus(ModelStatus.JUEGO_ACABADO);
		}
		else {
			_status.setStatus(ModelStatus.COMIENZO_DE_JUGADA);
			for(RDLSObserver o : _observers)
				o.onPlayStart(this);
		}
		if(_client != null) {
			_client.sendMsg(this.serialize(), SpecialMsg.NONE);
		}
	}

	//le suma al jugador lo acumulado en el bote
	public void executeBote() {
		addPlayerPoints(_bote);
		_bote = 0;
	}

	public void playerPointsRatio(double d) {
		getCurrentPlayer().pointsRatio(d);
	}

	public void addPlayerPoints(int points) {
		getCurrentPlayer().addPoints(points);
	}

	public void addBote(int x) {
		_bote += x;
	}

	private void notifyError(String str, boolean fatal) {
		for(RDLSObserver o : _observers)
			o.notify(_status.getStatus(), str);
	}

	
	//GUARDAR Y CARGAR
	
	//se utilizan en el Patrón memento (en los métodos de createMemento() y setMemento()) y 
	// en el juego en Red (en lo métodos de serialize y deserialize)
	private JSONObject report() {
		//llama a todos los reports para crear un JSON global de toda la información del juego 
		JSONObject jo = new JSONObject();
		jo.put("panel", _panel.report());//cada componente se guarda a si mismo en un json
		JSONArray ja = new JSONArray();
		for(Player p: _players) {
			ja.put(p.report());
		}
		jo.put("players", ja);
		jo.put("turno", _currentPlayerIndex);
		jo.put("numJugadores", _numPlayers);
		jo.put("hayBote", _hayBote);
		if(_hayBote) jo.put("bote", _bote);
		JSONArray cs = _ruleta.report();
		jo.put("ruleta", cs);
		jo.put("status", _status.getStatus().toString());
		jo.put("angulo", _ruleta.getCurrentAngle());
		
		return jo;
	}
	
	//se utilizan en el Patrón memento (en los métodos de create y setMemento()) y 
	// en el juego en Red (en lo métodos de serialize y deserialize)
	private void unPack(JSONObject jo) {
		//desempaquetamos el json global con toda la información del juego delegando en todos los componentes 
		_panel = Panel.unpack(jo.getJSONObject("panel"));//cada componente se crea a sí mismo con el json de su información
		_numPlayers = jo.getInt("numJugadores");
		_status.setStatus(ModelStatus.valueOf(jo.getString("status")));
		JSONArray ja = jo.getJSONArray("players");
		_players = new ArrayList<Player>();
		for(int i = 0; i < _numPlayers; i++) {
			_players.add(Player.unpack(ja.getJSONObject(i)));
		}
		_currentPlayerIndex = jo.getInt("turno");
		_hayBote = jo.getBoolean("hayBote");
		if(_hayBote) _bote = jo.getInt("bote");
		
		List<Casilla> l = Ruleta.unpack(jo.getJSONArray("ruleta"));
		
		_ruleta = new Ruleta(l);
		_ruleta.setCurrentAngle(jo.getInt("angulo"));
	}
	
	public void setMemento(Memento m) throws FileNotFoundException{
		//ponemos el estado del juego al que teníamos guardado del pasado en la clase memento
		JSONObject jo = m.getState();
		//desempaquetar el json e igualar todo lo del game a él
		unPack(jo);
	}

	public Memento createMemento() {
		//congelamos el estado actual del juego en la clase memento
		return new Memento(this.report());
	}

	public void guardarPartida(File out) {
		//guarda la partida actual en el archivo out
		GuardarYCargar cyg = new GuardarYCargar(this);
		try {
			cyg.guardar(out);
			for(RDLSObserver o : _observers) {
				o.notify(_status.getStatus(), "[ÉXITO] La partida se ha guardado correctamente");
			}
		} catch (IOException e) {
			for(RDLSObserver o : _observers) {
				o.notify(_status.getStatus(), "[ERROR] : No se ha podido guardar la partida");
			}
		}
	}

	public void cargarPartida(File in) throws IOException {
		//carga la partida del archivo in para jugar ahora a esa
		GuardarYCargar g = new GuardarYCargar(this);
		g.cargar(in);
	}
	
	//GETTERS
	public int getNumPlayers() {
		return _numPlayers;
	}
	
	public Player getCurrentPlayer() {
		return _players.get(_currentPlayerIndex);
	}
	
	public int getBote() {
		return _bote;
	}

	public boolean hayBote() {
		return _hayBote;
	}

	public String getMarcador() {
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < _players.size(); ++i) {
			String name = _players.get(i).getName();
			if(name == null) name = "Jugador " + Integer.toString(i);
			str.append(name).append(": ").append(_players.get(i).getPoints()).append(" puntos");
			if(i < _players.size() - 1)
				str.append(System.lineSeparator());
		}
		return str.toString();
	}

	public String getPanel() {
		return _panel.toString();
	}

	public String getCategoria() {
		return _panel.getCategoria().toString();
	}

	public String getPista() {
		return _panel.getPista();
	}

	public ModelStatus getStatus() {
		return _status.getStatus();
	}

	public String getCasilla() {
		if(_status.getStatus() == ModelStatus.RULETA_TIRADA)
			return _ruleta.getCurrentCasilla().toString();
		else
			return null;
	}
	
	public List<String> getNombresCasillas() {
		return _ruleta.getNombresCasillas();
	}
	
	public int getRouletteAngle() {
		return _ruleta.getCurrentAngle();
	}
	

	// IA
	public Set<Character> getVocalesUsadas() {
		return _panel.getVocalesUsadas();
	}
	
	public Set<Character> getConsonantesUsadas() {
		return _panel.getConsonantesUsadas();
	}

	public void elegirConsonante() {
		elegirLetra(getCurrentPlayer().elegirConsonante(this));
	}

	public Character elegirVocal() {
		return getCurrentPlayer().elegirVocal(this);
	}
	
	void nextAction() {
		if(_slowMode) nextActionSlowMode();
		else nextActionFastMode();	
	}
	
	void nextActionSlowMode() {
		try {
			Thread.sleep(WAIT_MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {				
				nextActionFastMode();
			}
		});
	}
	
	void nextActionFastMode() {
		if (_panel.isCompleted())
			_status.setStatus(ModelStatus.JUEGO_ACABADO);
		else {
			getCurrentPlayer().nextAction(this);
		}
	}

	public Map<Character, Integer> getApariciones() {
		return _panel.getApariciones();
	}

	public boolean isCurrentPlayerIA() {
		return getCurrentPlayer().isIA();
	}

	public List<Player> getPlayers() {
		return Collections.unmodifiableList(_players);
	}

	//RED	
	public String serialize() {
		return report().toString();
	}
	
	public void deserialize(String msg) {
		unPack(new JSONObject(msg));
		for(RDLSObserver o: _observers) {
			o.onServerAction(this, getCurrentPlayer().getName().equals(_client.get_name()));
		}
	}
	
	public boolean isTurnInServer() {
		return (_client == null) || getCurrentPlayer().getName().equals(_client.get_name());
	}
	
	//Si esta en red ajusta bien el game para y enviarlo al 
	//servidor antes de desconectar al cliente del servidor
	public void exit() {
		if(this._client != null) { 
			if(_numPlayers != 1) { // Si solo hay un jugador, hacer todo el proceso de actualizar cual es el siguiente jugador y eliminarlo de la lista
				Player delete = null;
				int deleteIndex = 0;
				while(delete == null) {
					if((_players.get(deleteIndex).getName()).equals(_client.get_name())) {
						delete = _players.get(deleteIndex);
					}
					else
						++deleteIndex;
				}
				
				_players.remove(delete);
				--_numPlayers;
				
				if(_currentPlayerIndex > deleteIndex)
					--_currentPlayerIndex;
				else if (_currentPlayerIndex == deleteIndex && _currentPlayerIndex == _numPlayers) {
					++_currentPlayerIndex;
					_currentPlayerIndex %= _numPlayers;
				}
			}
			
			this.exitServer();
		}
	}

	public void serverShutGame() {
		if(this._client != null) {
			this._client.sendMsg(this.serialize(), SpecialMsg.SHUT);
		}
	}
	
	public void exitServer() {
		if (this._client != null);
	}
	
	
	public void setSlowMode() {
		_slowMode = true;
	}
}
