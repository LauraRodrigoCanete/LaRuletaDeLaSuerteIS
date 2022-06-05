package model.factories;

import org.json.JSONObject;

import model.record.Record;

public class NewRecordBuilder extends Builder<Record> { //Se encarga de crear Record
	
	private static final String TYPE = "RECORD";

	public NewRecordBuilder() {
		super(TYPE);
	}

	@Override
	protected Record createTheInstance(JSONObject data) { //Usa la informacion del JSON para crear un Record
		return new Record(data.getString("PLAYER"), data.getInt("SCORE")); 
	} //Para la creación de Record es necesario el dato PLAYER y SCORE del JSON data

}
