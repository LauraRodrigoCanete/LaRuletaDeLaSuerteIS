package model.panel;

import org.json.JSONObject;

import misc.CharUtilities;


public class Letra {
	private char letra;
	private boolean descubierta;
	
	public Letra(char c) {
		this.letra = c;
		this.descubierta = !CharUtilities.isLetter(c);
	}

	
	public Letra(char c, boolean descubierta) {
		this.letra = c;
		this.descubierta = descubierta;
	}
	
	//Utiliza la clase CharUtilities para revisar si una letra está en el panel y no ha sido descubierta ya
	public boolean check(char letra2) {
		if(this.descubierta) return false;
		else if(CharUtilities.toUpper(this.letra) == CharUtilities.toUpper(letra2)) {
			this.descubierta = true;
			return true;
		}
		else return false;
	}

	public boolean isDiscovered() {
		return this.descubierta;
	}
	
	public String toString() {
		return String.valueOf(letra);
	}
	
	public Character getChar() {
		return letra;
	}
		
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("letra", letra);
		if(descubierta)
			jo.put("descubierta", 1);
		else
			jo.put("descubierta", 0);
		return jo;	
	}

	public static Letra unpack(JSONObject jo) {
		int aux = jo.getInt("descubierta");
		boolean d = aux == 0 ? false : true;
		return new Letra((char)jo.getInt("letra"), d);
	}

	//este método es de uso exclusivo para facilitar los test
	public static boolean equals(Letra l1, Letra l2) {
		return l1.letra == l2.letra && l1.descubierta == l2.descubierta;
	}


	public void reset() {
		descubierta = !CharUtilities.isLetter(letra);
	}
}
