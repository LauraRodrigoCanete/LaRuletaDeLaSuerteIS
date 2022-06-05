package model.factories;

import org.json.JSONObject;

import model.ruleta.casillas.Casilla;
import model.ruleta.casillas.CasillaPuntos;

public class NewCasillaPuntosBuilder extends Builder<Casilla> { //Se encarga de crear CasillaPuntos

	private static final String TYPE = "CASILLA_PUNTOS";
	
	public NewCasillaPuntosBuilder() {
		super(TYPE);
	}

	@Override
	protected Casilla createTheInstance(JSONObject data) { //Usa la informacion del JSON para crear una CasillaPuntos
		return new CasillaPuntos(data.getInt("PUNTOS")); //Para la creación de la CasillaPunts es necesario el dato PUNTOS del JSON data
	}

}
