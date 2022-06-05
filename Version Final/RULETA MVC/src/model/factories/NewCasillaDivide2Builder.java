package model.factories;

import org.json.JSONObject;

import model.ruleta.casillas.Casilla;
import model.ruleta.casillas.CasillaDivide2;

public class NewCasillaDivide2Builder extends Builder<Casilla> { //Se encarga de crear CasillaDivide2

	private static final String TYPE = "CASILLA_DIVIDE_2";
	
	public NewCasillaDivide2Builder() {
		super(TYPE);
	}

	@Override
	protected Casilla createTheInstance(JSONObject data) { //Usa la informacion del JSON para crear una CasillaDivide2
		return new CasillaDivide2(); //Para la creación de la CasillaDivide2 no es necesaria ningun dato extra
	}

}
