package model.factories;

import org.json.JSONObject;

public interface Factory<T> { //Modelaremos la factoria a través de esta interfaz genérica
	
	public T createInstance(JSONObject info);
	
}
