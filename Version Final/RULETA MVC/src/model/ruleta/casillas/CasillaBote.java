package model.ruleta.casillas;

import org.json.JSONObject;

import model.Game;
import model.exceptions.SkipTurnException;

public class CasillaBote extends Casilla {

	private static final String TYPE = "CASILLA BOTE : Llévate el bote acumulado";
	private static final String NAME = "CASILLA BOTE";
	
	public CasillaBote() {
		super(TYPE, NAME);
	}

	@Override
	public void execute(Game game) throws SkipTurnException {}

	@Override
	public void executeAfterLetterChosen(Game game, int veces) {
		game.executeBote();
	}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		obj.put("type", "CASILLA_BOTE");
		obj.put("data", new JSONObject());
		return obj;
	}

}
