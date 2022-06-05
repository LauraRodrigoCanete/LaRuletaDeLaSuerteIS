package model;

import org.json.JSONObject;

public class Memento {
	//aqui se guarda el estado de todos los elementos del juego
	private JSONObject state;
	
	public Memento(JSONObject s) {
		state = s;
	}
	
	public JSONObject getState() {
		return state;
	}
	
	public void setState(JSONObject s) {
		state = s;
	}
}
