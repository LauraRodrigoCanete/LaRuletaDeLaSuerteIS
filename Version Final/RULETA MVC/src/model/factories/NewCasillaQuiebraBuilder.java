package model.factories;

import org.json.JSONObject;

import model.ruleta.casillas.Casilla;
import model.ruleta.casillas.CasillaQuiebra;

public class NewCasillaQuiebraBuilder extends Builder<Casilla> { //Se encarga de crear CasillaQuiebra

	private static final String TYPE = "CASILLA_QUIEBRA";
	
	public NewCasillaQuiebraBuilder() {
		super(TYPE);
	}

	@Override
	protected Casilla createTheInstance(JSONObject data) { //Usa la informacion del JSON para crear una CasillaQuiebra
		return new CasillaQuiebra(); //Para la creación de la CasillaQuiebra no es necesaria ningun dato extra
	}

}
