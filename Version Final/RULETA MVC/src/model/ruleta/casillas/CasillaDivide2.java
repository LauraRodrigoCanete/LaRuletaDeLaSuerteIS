package model.ruleta.casillas;

import org.json.JSONObject;

import model.Game;

public class CasillaDivide2 extends Casilla {

	private static final String TYPE = "CASILLA 1/2 : Tus puntos disminuyen a la mitad";
	private static final String NAME = "CASILLA 1/2";
	
	public CasillaDivide2() {
		super(TYPE, NAME);
	}

	@Override
	public void execute(Game game) {}

	@Override
	public void executeAfterLetterChosen(Game game, int veces) {
		game.playerPointsRatio(0.5);
	}
	
	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		obj.put("type", "CASILLA_DIVIDE_2");
		obj.put("data", new JSONObject());
		return obj;
	}

}