package model.ruleta;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;

import model.factories.Factorias;
import model.ruleta.casillas.Casilla;

public class Ruleta {
	private static final int VUELTA = 360; 
	
	private List<String> _nombresCasillas;
	private List<Casilla> _casillas;
	private int _size;
	private int _angulo; //Entre 0º y 359º
	
	public Ruleta(List<Casilla> casillas) {
		_casillas = casillas;
		_size = casillas.size();
		_angulo = 0;
		_nombresCasillas = new ArrayList<>();
		for(int i = 0; i < casillas.size(); ++i) {
			_nombresCasillas.add(casillas.get(i).getName());
		}
	}
	
	public int cambiarCasilla(double rand) {
		//desplazamiendo aleatorio entre 540 y 1080 grados;
		int desplazamiento = (int) ((0.5 + rand * 0.5) * 3 * VUELTA); 
		_angulo = (_angulo + desplazamiento) % VUELTA;
		return desplazamiento;
	}
	
	public Casilla getCurrentCasilla() {
		return _casillas.get((int) (_angulo/ ( (VUELTA + 0.0) / (_size + 0.0) )));
	}

	public int getSize() {
		return _size;
	}

	public JSONArray report() {
		JSONArray ja = new JSONArray();
		for(Casilla c : _casillas)
			ja.put(c.report());
		return ja;
	}

	public int getCurrentAngle() {
		return _angulo;
	}
	
	public List<String> getNombresCasillas() {
		return Collections.unmodifiableList(_nombresCasillas);
	}

	public static List<Casilla> unpack(JSONArray ja) {
		List<Casilla> l = new ArrayList<>();
		for(int i = 0; i < ja.length(); i++) {
			l.add(Factorias.factoria_casillas.createInstance(ja.getJSONObject(i)));
		}
		return l;
	}

	public void setCurrentAngle(int angulo) {
		_angulo = angulo;
	}

	
}
