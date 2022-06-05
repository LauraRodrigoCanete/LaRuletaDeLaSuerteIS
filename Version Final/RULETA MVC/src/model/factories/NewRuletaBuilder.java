package model.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.ruleta.Ruleta;
import model.ruleta.casillas.Casilla;

public class NewRuletaBuilder extends Builder<Ruleta> {

	private static final String TYPE = "RULETA";
	
	public NewRuletaBuilder() {
		super(TYPE);
	}

	@Override
	protected Ruleta createTheInstance(JSONObject data) {
			
		if(!(data.has("RULETA")))
			throw new IllegalArgumentException("Wrong loading format");
		
		JSONArray arr = data.getJSONArray("RULETA");
				
		List<Casilla> casillas = new ArrayList<Casilla>();
		
		for(int i = 0; i < arr.length(); ++i) {
			casillas.add(Factorias.factoria_casillas.createInstance(arr.getJSONObject(i)));
		}
		
		return new Ruleta(casillas);
		
	}

}
