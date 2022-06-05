package strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Model;
import model.factories.Factorias;

public class OteTest {
	private static final int REPETICIONES = 100000;
	private static final boolean BOTE = true;
	private static final String NOMBRES[] = {"Pepote", "Juanote", "Menganote"};
	
	public static void main(String[] args) {
		Map<String, Integer> ganadores = new TreeMap<>();
		for(String str : NOMBRES) ganadores.put(str, 0);
		List<String> nombres = new ArrayList<>();
		for(String str : NOMBRES) nombres.add(str);
		// Ejecución de la prueba:
		Factorias.initFactories();
		for(int i = 0; i < REPETICIONES; ++i) {
			Model model = new Model();
			model.setFastMode();
			model.newGame(3, BOTE, nombres);
			String str = model.getGanador();
			ganadores.put(str, ganadores.get(str) + 1);
		}
		System.out.println(ganadores);
	}
}
