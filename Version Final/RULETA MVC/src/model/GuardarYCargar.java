package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.json.JSONObject;
import org.json.JSONTokener;

public class GuardarYCargar {
	private Game _game;
	private File _defaultFile;
	
	public GuardarYCargar(Game game){
		_game = game;
		_defaultFile = new File("examples/output.json");
	}
	
	//guardamos la partida actual en un archivo
	public void guardar(File output) throws IOException{
		Memento memento = _game.createMemento();
		try(PrintStream p = new PrintStream(new FileOutputStream( output != null ? output : _defaultFile))) {
			p.println(memento.getState().toString());
		}
	}
	
	
	//cargamos una partida de un archivo
	public void cargar(File input) throws IOException{
		try(FileInputStream in = new FileInputStream(input != null ? input : _defaultFile)){
			JSONObject jo = new JSONObject(new JSONTokener(in)); 
			Memento memento = new Memento(jo);
			_game.setMemento(memento);
		}
	}
}
