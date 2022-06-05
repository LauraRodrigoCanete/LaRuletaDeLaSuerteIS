package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.json.*;

import model.*;
import model.factories.Factorias;
import model.record.Record;

public class GUIController {

	private Model _model;
	private final String _help = "<html><body> <i> Botones de la parte superior </i> "
			+ "<br> <br> <b> Cargar partida</b>: Carga una partida anterior que se dejó a medias."
			+ "<br> <b> Guardar partida </b>: Guarda en un fichero el estado del juego."
			+ "<br> <b> Ver records </b>: Consulta las mayores puntuaciones obtenidas en el juego."
			+ "<br> <b> Jugar </b>: Comienza una nueva partida."
			+ "<br> <b> Red </b>: Crea o únete a un servidor.<br>"
			 
			+ "<br> <i> Botones de la parte derecha </i>"
			+ "<br> <br> Tirar: Lanza la ruleta. ¡Buena suerte!"
			+ "<br> Comprar vocal: Compra una vocal."
			+ "<br> Elegir consonante: Elige la letra que quieras. Cuantas más apariciones mejor, así que piénsala bien."
			+ "<br> Ayuda: Muestra este mensaje."
			+ "<br> Reset: Comienza el juego de nuevo. </body></html>";

	public GUIController(Model model) {
		_model = model;
	}

	
	//Si el fichero de entrada no posee un apartado de récords, lanza excepción, en otro caso, crea los récords (usando una factoría)
	// y  los guarda en un array
	public void loadRecords() throws FileNotFoundException {
		InputStream is =  new FileInputStream(new File("examples/records.json"));
		JSONObject obj = new JSONObject( new JSONTokener(is) );
		if(!obj.has("RECORDS"))
			throw new IllegalArgumentException("El fichero para cargar records no es correcto");

		JSONArray arr = obj.getJSONArray("RECORDS");

		List<Record> records = new ArrayList<>();

		for(int i = 0; i < arr.length(); ++i)
			records.add(Factorias.factoria_records.createInstance(arr.getJSONObject(i)));

		_model.loadRecords(records);
	}
	
	public void addObserver(RDLSObserver o) {
		_model.addObserver(o);
	}

	public void removeObserver(RDLSObserver o) {
		_model.removeObserver(o);
	}
	
	//A partir de aquí, se implementan los métodos que sirven de puente entre la interfaz y  el modelo a través de este controlador
	//Todas las peticiones que hace la vista al modelo se hacen a través del controlador
	public void help() {
		_model.help();
	}

	public String getHelp() {
		return _help;
	}

	public void cargarPartida(File in) throws IOException {
		_model.cargarPartida(in); 
	}

	public void guardarPartida(File out) {
		_model.guardarPartida(out); 
	}

	public void verRecords() {
		_model.showRecords();
	}

	public void reset() throws IOException {
		_model.reset();
	}

	public void throwRoulette() {
		_model.tirarRuleta();
	}

	public void createNewGame(int numPlayers, boolean bote, List<String> names) {
		_model.newGame(numPlayers, bote, names);
	}

	public void elegirConsonate(char selected) {
		_model.elegirConsonante(selected);
	}

	public void comprarVocal(char selected) {
		_model.comprarVocal(selected);
	}

	public void exit() {
		_model.exitGame();
	}
	
	//RED
	public void createServer(int numJugadores, int port) throws IOException {
		_model.createServer(numJugadores, port);
	}
	
	public void joinServer(String ip, int port, String name) throws IOException {
		_model.joinServer(ip, port, name);
	}

	public void shutServer() {
		_model.shutServer();
		
	}
	
	//Para cuando se quiera salir de la apicación pulsando la "X"
	public void exitApp() {
		this._model.exitServer();
	}
}
