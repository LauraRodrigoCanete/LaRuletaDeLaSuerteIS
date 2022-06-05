package model.factories;

import org.json.JSONObject;

import model.ruleta.casillas.Casilla;
import model.ruleta.casillas.CasillaX2;


public class NewCasillaX2Builder extends Builder<Casilla> { //Se encarga de crear CasillaX2

	private static final String TYPE = "CASILLA_X2";
	
	public NewCasillaX2Builder() {
		super(TYPE);
	}

	@Override
	protected Casilla createTheInstance(JSONObject data) { //Usa la informacion del JSON para crear una CasillaX2
		return new CasillaX2(); //Para la creación de la CasillaX2 no es necesaria ningun dato extra
	}

}
