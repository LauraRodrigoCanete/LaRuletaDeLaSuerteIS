package model.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> { 
	/*
	Hemos decidido para crear esta clase para extender una factoria con más opciones sin 
	necesidad de modificar el código. Para ello, usamos un elemento básico: builder
	esta será una clase capaz de crear instancias de un tipo específico. Para ello
	dispondremos de un array de estos builders para ir recorriéndolo hasta dar con el builder
	adecuado que crea el elemento que queremos y así fabricar los elementos del juego. 
	
	Especialmente se va a utilizar para poder crear casillas leyéndolas de un JSON sin importar cuantas
	haya ni el orden en el que estén y para leer los records del juego.
	*/

	private List<Builder<T>> _builders;
	

	public BuilderBasedFactory(List<Builder<T>> builders) {
		_builders = new ArrayList<>(builders); 
	}

	@Override
	public T createInstance(JSONObject info) { // Info describe el objeto a crear
		if (info != null) {
			for (Builder<T> bb : _builders) { // Recorremos la lista de builders hasta encontrar el capaz de crear el objeto
				T o = bb.createInstance(info);
				if (o != null)
					return o;
			}
		}

		throw new IllegalArgumentException("Invalid value for createInstance: " + info);
	}
}