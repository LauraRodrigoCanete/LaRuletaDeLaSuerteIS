package model.panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import misc.CharUtilities;

public class Panel {

	private List<Letra> frase;
	private Set<Character> _vocalesUsadas, _consonantesUsadas;
	private String pista;
	private Categoria category;
	private Map<Character, Integer> _apariciones;
	
	public Panel(String frase, String pista, Categoria cat) {
		this.frase = new ArrayList<Letra>();
		_apariciones = new HashMap<Character, Integer>();
		for(int i = 0; i < frase.length(); ++i) {
			this.frase.add(new Letra(frase.charAt(i)));
			CharUtilities.toUpper(frase.charAt(i)); 
		}
		this.pista = pista;
		this.category = cat;
		cargarIA();
	}
	
	public Panel(List<Letra> frase, String pista, String category) {
		this.frase = frase;
		this.pista = pista;
		this.category = Categoria.valueOf(category.toUpperCase());
		cargarIA();
	}
	
	public int check(char letra) {
		int res = 0;
		letra = CharUtilities.toUpper(letra);
		if (CharUtilities.isVowel(letra)) {
			_vocalesUsadas.add(letra);
		} else {
			_consonantesUsadas.add(letra);
		}
		for(Letra l : frase) {
			if (l.check(letra)) {
				++res;
				_apariciones.put(letra, _apariciones.get(letra) - 1);
			}
		}
		return res;
	}
	
	public boolean isCompleted() {
		for(Letra l : frase) {
			if(!l.isDiscovered()) return false;
		}
		return true;
	}
	
	//Si está descubierta pone guión y si no pone la letra en cuestión
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(Letra l : frase) {
			if(!l.isDiscovered()) {
				str.append("_");
			}
			else str.append(l.toString());
			str.append(' ');
		}
		return str.toString();
	}
	
	public String getPista() {
		return this.pista;
	}
	
	public Categoria getCategoria() {
		return this.category;
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		for(Letra l: frase) {
			ja.put(l.report());
		}
		jo.put("frase", ja);
		jo.put("pista", pista);
		jo.put("categoria", category.toString());
		return jo;	
	}
	
	public static Panel unpack(JSONObject jo) {
		JSONArray ja = jo.getJSONArray("frase");
		List<Letra> frase = new ArrayList<Letra>();
		for(int i = 0; i < ja.length(); i++) {
			frase.add(Letra.unpack(ja.getJSONObject(i)));
		}
		return new Panel(frase, jo.getString("pista"), jo.getString("categoria"));
	}

	public Set<Character> getVocalesUsadas() {
		return Collections.unmodifiableSet(_vocalesUsadas);
	}
	
	public Set<Character> getConsonantesUsadas(){
		return Collections.unmodifiableSet(_consonantesUsadas);
	}
	
	public int numApariciones(char c) {
		if(!_apariciones.containsKey(c)) {
			return 0;
		} else {
			return _apariciones.get(c);
		}
	}

	public Map<Character, Integer> getApariciones() {
		return Collections.unmodifiableMap(_apariciones);
	}
	
	// Cosas de la IA
	// Es un metodo que se encarga de inicializar convenientemente las estructuras de datos que van a utilizar la IA
	private void cargarIA() {
		_apariciones = new HashMap<Character, Integer>();
		_vocalesUsadas = new HashSet<Character>();
		_consonantesUsadas = new HashSet<Character>();
		for (Letra l : frase) {
			Character c = CharUtilities.toUpper(l.getChar());
			boolean discovered = l.isDiscovered();
			if (discovered) {
				if(CharUtilities.isLetter(c)) {
					if (CharUtilities.isVowel(c))
						_vocalesUsadas.add(c);
					else
						_consonantesUsadas.add(c);
				}
			} else {
				if (!_apariciones.containsKey(c))
					_apariciones.put(c, 0);
				_apariciones.put(c, _apariciones.get(c) + 1);
			}
		}
	}

	public void reset() { 
		for (Letra l : frase) 
			l.reset();
		cargarIA();
	}

	//esto se utilizará para que cuando solo queden vocales en la frase si se dicen 
	//todas las consonantes, las vocales seran gratis
	public boolean onlyVowelsLeft() {
		return _consonantesUsadas.size() == 22;
	}
	
	
}