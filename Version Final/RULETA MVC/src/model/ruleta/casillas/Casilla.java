package model.ruleta.casillas;

import org.json.JSONObject;

import model.Game;
import model.exceptions.SkipTurnException;

public abstract class Casilla {
	protected String _type;
	protected String _name;
	
	public Casilla(String type, String name) {
		_type = type;
		_name = name;
	}
	public abstract void execute(Game game) throws SkipTurnException;
	
	public abstract void executeAfterLetterChosen(Game game, int veces);
	
	public String toString() {
		return _type;
	}
	
	public String getName() {
		return _name;
	}
	
	public abstract JSONObject report();
}
