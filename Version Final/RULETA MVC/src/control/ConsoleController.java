package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

import org.json.*;

import control.commands.Command;
import model.*;
import model.factories.Factorias;
import model.record.Record;

public class ConsoleController {
	private static final String UNKNOWN_COMMAND_MSG = "[ERROR] : Comando desconocido";
	
	private Model _model;
	private Scanner _scanner;
	private String _help;
	
	
	public ConsoleController(Model model) {
		_model = model;
		_scanner = Lectura.getScanner();
		_help = Command.getHelp();
	}

	public void run() {
		String s = null;
		if (_model.isCommandNeeded()) { // Si no hace falta comando, no leemos la siguiente línea
			s = _scanner.nextLine();
		}
		while(!("q".equalsIgnoreCase(s) || ("quit".equalsIgnoreCase(s)))) { // Mientras no salgamos de la aplicación
			if (!_model.isCommandNeeded()) _model.nextAction(); // Si no hace falta comando, ejecutamos la siguiente acción
			else runCommand(s); // Ejecutamos el comando dado
			if (_model.isCommandNeeded())
				s = _scanner.nextLine(); // Lo mismo de antes, si no hace falta comando, no leemos nada.
		}
	}
	
	//Se intenta coger un comando con los parámetros introducidos, si todo ha ido bien lo ejecuta.
	private void runCommand(String s) {
		String[] parameters = s.trim().split(" ");
		Command command;
		try {
			command = Command.getCommand(parameters);
			if(command == null) {
				_model.notifyError(UNKNOWN_COMMAND_MSG, false);
			}
			else {
				try {
					command.execute(_model);
				} catch (Exception e) {
					_model.notifyError(e.getMessage(), false);
				}
			}
		} catch (Exception e1) { 
			_model.notifyError(e1.getMessage(), false);
		}
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
	
	public String getHelp() {
		return _help;
	}
	
}
