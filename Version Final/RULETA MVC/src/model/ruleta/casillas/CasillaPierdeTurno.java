package model.ruleta.casillas;

import org.json.JSONObject;

import model.Game;
import model.exceptions.SkipTurnException;

public class CasillaPierdeTurno extends Casilla {

	private static final String TYPE = "PIERDE EL TURNO : Pierdes el turno";
	private static final String NAME = "PIERDE EL TURNO";
	
	public CasillaPierdeTurno() {
		super(TYPE, NAME);
	}

	@Override
	public void execute(Game game) throws SkipTurnException {
		throw new SkipTurnException("Pierdes el turno");
	}

	@Override
	public void executeAfterLetterChosen(Game game, int veces) {}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		obj.put("type", "CASILLA_PIERDE_TURNO");
		obj.put("data", new JSONObject());
		return obj;
	}
}
