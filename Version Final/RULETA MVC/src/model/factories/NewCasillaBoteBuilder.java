package model.factories;

import org.json.JSONObject;

import model.ruleta.casillas.Casilla;
import model.ruleta.casillas.CasillaBote;

public class NewCasillaBoteBuilder extends Builder<Casilla> { //Se encarga de crear CasillaBote

	private static final String TYPE = "CASILLA_BOTE";
	
	public NewCasillaBoteBuilder() {
		super(TYPE);
	}

	@Override
	protected Casilla createTheInstance(JSONObject data) { //Usa la informacion del JSON para crear una CasillaBote
		return new CasillaBote(); //Para la creación de la CasillaBote no es necesaria ningun dato extra
	}

}
