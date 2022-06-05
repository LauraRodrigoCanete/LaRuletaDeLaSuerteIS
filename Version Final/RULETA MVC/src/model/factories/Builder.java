package model.factories;

import org.json.JSONObject;

public abstract class Builder<T> { // es una clase capaz de crear una instancia de un tipo especifico
	/*
	 Todos los JSON que entrarán en el builder seran de la forma:
	 {
	"type" : "el tipo especifico",
	"data" : { un JSON que guarda los datos correspondientes al tipo }
	}
	
	De esta manera en el createInstance si el tipo del JSON coincide con el atributo type entonces
	significara que este sera el builder encargado de crear esa clase y se llamara al método createTheInstance
	que será implementado por las clases que hereden de builder
	*/
	
	protected String _type; 

	Builder(String type) {
		if (type == null)
			throw new IllegalArgumentException("Invalid type: " + type);
		else
			_type = type;
	}

	public T createInstance(JSONObject info) {

		T b = null;

		if (_type != null && _type.equals(info.getString("type"))) {
			b = createTheInstance(info.has("data") ? info.getJSONObject("data") : new JSONObject());
		}

		return b;
	}

	protected abstract T createTheInstance(JSONObject data);
}