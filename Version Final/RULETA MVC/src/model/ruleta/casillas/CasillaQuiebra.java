package model.ruleta.casillas;

import org.json.JSONObject;

import model.Game;
import model.exceptions.SkipTurnException;

public class CasillaQuiebra extends Casilla {
	
	private static final String TYPE = "QUIEBRA : Tus puntos disminuyen a 0 y pierdes el turno";
	private static final String NAME = "QUIEBRA";
	
	public CasillaQuiebra() {
		super(TYPE, NAME);
	}
	
	@Override
	public void execute(Game game) throws SkipTurnException {
		game.playerPointsRatio(0);
		throw new SkipTurnException("Pierdes tus puntos y el turno");
	}

	@Override
	public void executeAfterLetterChosen(Game game, int veces) {}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		obj.put("type", "CASILLA_QUIEBRA");
		obj.put("data", new JSONObject());
		return obj;
	}
}