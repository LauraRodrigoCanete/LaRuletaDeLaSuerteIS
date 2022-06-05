package model.ruleta.casillas;

import org.json.JSONObject;

import model.Game;

public class CasillaPuntos extends Casilla {

	private static final String TYPE = "CASILLA DE PUNTOS: ";
	
	int _puntos;
	
	public CasillaPuntos(int puntos) {
		super(String.format("%s%d PUNTOS", TYPE, puntos), String.valueOf(puntos));
		_puntos = puntos;
	}
	
	@Override
	public void execute(Game game) {}

	@Override
	public void executeAfterLetterChosen(Game game, int veces) {
		game.addPlayerPoints(_puntos * veces);
		game.addBote(_puntos * veces);
	}
	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		JSONObject puntos = new JSONObject();
		puntos.put("PUNTOS", _puntos);
		obj.put("type", "CASILLA_PUNTOS");
		obj.put("data", puntos);
		return obj;
	}

}