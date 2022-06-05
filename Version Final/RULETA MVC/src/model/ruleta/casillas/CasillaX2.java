package model.ruleta.casillas;

import org.json.JSONObject;

import model.Game;

public class CasillaX2 extends Casilla {

	private static final String TYPE = "CASILLA x2 : Tus puntos se duplican";
	private static final String NAME = "CASILLA x2";
	
	public CasillaX2() {
		super(TYPE, NAME);
	}

	@Override
	public void execute(Game game) {}

	@Override
	public void executeAfterLetterChosen(Game game, int veces) {
		game.playerPointsRatio(2);
	}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		obj.put("type", "CASILLA_X2");
		obj.put("data", new JSONObject());
		return obj;
	}
}
