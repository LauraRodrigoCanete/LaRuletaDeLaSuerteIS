package model.factories;

import org.json.JSONObject;

import model.ruleta.casillas.Casilla;
import model.ruleta.casillas.CasillaPierdeTurno;

public class NewCasillaPierdeTurnoBuilder extends Builder<Casilla> { //Se encarga de crear CasillaPierdeTurno

	private static final String TYPE = "CASILLA_PIERDE_TURNO";
	
	public NewCasillaPierdeTurnoBuilder() {
		super(TYPE);
	}

	@Override
	protected Casilla createTheInstance(JSONObject data) { //Usa la informacion del JSON para crear una CasillaPierdeTurno
		return new CasillaPierdeTurno(); //Para la creación de la CasillaPierdeTurno no es necesaria ningun dato extra
	}

}
