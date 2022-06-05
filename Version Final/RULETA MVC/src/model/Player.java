package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import model.strategies.AlwaysBuyVowelStrategy;
import model.strategies.CheckFirstStrategy;
import model.strategies.ChooseLetterStrategy;
import model.strategies.ChooseNextActionStrategy;
import model.strategies.ManualStrategy;
import model.strategies.NeverBuyVowelStrategy;
import model.strategies.RandomLetterStrategy;
import model.strategies.RandomNotUsedLetterStrategy;
import model.strategies.SometimesBuyVowelStrategy;


public class Player {

	private int _points;
	private String _name;
	private ChooseNextActionStrategy _nextActionStrategy;
	private ChooseLetterStrategy _letterStrategy;
	
	private final static Map<String, ChooseNextActionStrategy> nameToActionStrategy;
	static {
		Map<String, ChooseNextActionStrategy> auxMap = new HashMap<>();
		auxMap.put("Juanito", new AlwaysBuyVowelStrategy());
		auxMap.put("Pepito", new AlwaysBuyVowelStrategy());
		auxMap.put("Menganito", new AlwaysBuyVowelStrategy());
		auxMap.put("Juan", new SometimesBuyVowelStrategy());
		auxMap.put("Pepe", new SometimesBuyVowelStrategy());
		auxMap.put("Mengano", new SometimesBuyVowelStrategy());
		auxMap.put("Juanote", new NeverBuyVowelStrategy());
		auxMap.put("Pepote", new NeverBuyVowelStrategy());
		auxMap.put("Menganote", new NeverBuyVowelStrategy());
		nameToActionStrategy = Collections.unmodifiableMap(auxMap);
	}
	private final static Map<String, ChooseLetterStrategy> nameToLetterStrategy;
	static {
		Map<String, ChooseLetterStrategy> auxMap = new HashMap<>();
		auxMap.put("Juanito", new RandomLetterStrategy());
		auxMap.put("Pepito", new RandomNotUsedLetterStrategy());
		auxMap.put("Menganito", new CheckFirstStrategy());
		auxMap.put("Juan", new RandomLetterStrategy());
		auxMap.put("Pepe", new RandomNotUsedLetterStrategy());
		auxMap.put("Mengano", new CheckFirstStrategy());
		auxMap.put("Juanote", new RandomLetterStrategy());
		auxMap.put("Pepote", new RandomNotUsedLetterStrategy());
		auxMap.put("Menganote", new CheckFirstStrategy());
		nameToLetterStrategy = Collections.unmodifiableMap(auxMap);
	}
	
	public Player() {
		_points = 0;
		_nextActionStrategy = new ManualStrategy();
		_letterStrategy = new ManualStrategy();
	}
	
	public Player(String nombre) {
		_points = 0;
		_name = nombre;
		_nextActionStrategy = elegirEstrategiaAccion(nombre);
		_letterStrategy = elegirEstrategiaLetra(nombre);
	}

	public Player(String nombre, int puntos) {
		this._points = puntos;
		this._name = nombre;
		_nextActionStrategy = elegirEstrategiaAccion(nombre);
		_letterStrategy = elegirEstrategiaLetra(nombre);
	}
	
	public int getPoints() {
		return _points;
	}
	
	public void addPoints(int points) {
		_points = Math.max(0, _points + points);
	}
	
	public String getName() {
		return _name;
	}
	
	public void reset() {
		this._points = 0;
	}

	public void pointsRatio(double d) {
		_points = (int) (_points * d);
	}
	
	public String toString() {
		return getName();
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("points", _points);
		jo.put("name", _name);
		return jo;
	}
	
	public static Player unpack(JSONObject jo) {
		return new Player(jo.getString("name"), jo.getInt("points"));
	}

	public Character elegirConsonante(Game game) {
		return _letterStrategy.chooseNextConsonant(game);
	}

	public Character elegirVocal(Game game) {
		return _letterStrategy.chooseNextVowel(game);
	}
	
	public boolean nextAction(Game game) {
		return _nextActionStrategy.chooseNextAction(game, _points);
	}
	
	public boolean isIA() {
		return _letterStrategy.isIA();
	}
	
	/*
	 * Nombres de las IA:
	 * Juanito -> Always Buy + Random Letter
	 * Pepito -> Always Buy + Random Not Used Letter
	 * Menganito -> Always Buy + Check First
	 * Juan -> Sometimes Buy + Random Letter
	 * Pepe -> Sometimes Buy + Random Not Used Letter
	 * Mengano -> Sometimes Buy + Check First
	 * Juanote -> Never Buy + Random Letter
	 * Pepote -> Never Buy + Random Not Used Letter
	 * Menganote -> Never Buy + Check First
	 */

	private ChooseLetterStrategy elegirEstrategiaLetra(String nombre) {
		return nameToLetterStrategy.containsKey(nombre) ? nameToLetterStrategy.get(nombre) : new ManualStrategy();
	}

	private ChooseNextActionStrategy elegirEstrategiaAccion(String nombre) {
		return nameToActionStrategy.containsKey(nombre) ? nameToActionStrategy.get(nombre) : new ManualStrategy();
	}

}
