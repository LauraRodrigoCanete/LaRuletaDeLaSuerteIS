package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.panel.Categoria;
import model.panel.Letra;
import model.panel.Panel;

public class PanelTest {
	private static String NOMBRE = "Picasso";
	
	@Test
	void test_0() {
		//Comprobamos como funciona el check de un panel y ver que no se diferencia entre tildes ni mayusculas	
		
		Panel p = new Panel("Pátata", "Cocina", Categoria.COMIDA);
		assertEquals(3, p.check('Á'));
		assertEquals(0, p.check('a'));
	}
	
	@Test 
	void test_1() {
		//Comprobamos como se va completando poco a poco el panel y que no se diferencia entre mayusuculas y minusculas
		
		Panel p = new Panel("Murciélago", "COVID19", Categoria.PELICULAS);
		assertEquals(Categoria.PELICULAS, p.getCategoria());
		assertEquals("COVID19", p.getPista());
		
		assertEquals(1, p.check('U'));
		assertEquals(1, p.check('r'));
		assertEquals(1, p.check('m'));
		assertEquals(1, p.check('í'));
		assertEquals(1, p.check('E'));
		assertEquals(1, p.check('c'));
		assertEquals(1, p.check('l'));
		assertEquals(1, p.check('Á'));
		assertEquals(false, p.isCompleted());
		assertEquals(1, p.check('g'));
		assertEquals(0, p.check('U'));
		assertEquals(0, p.check('Z'));
		assertEquals(0, p.check('ñ'));
		assertEquals(1, p.check('o'));
		assertEquals(true, p.isCompleted());
	}
	
	@Test 
	void test_2() {
		//Comprobamos como se comporta el panel con las diéresis en la ü que es la única que no se hace distinción
		
		Panel p = new Panel("Pingüino", "Happy Feet", Categoria.PELICULAS);
		assertEquals(Categoria.PELICULAS, p.getCategoria());
		assertEquals("Happy Feet", p.getPista());
		assertEquals(1, p.check('ü'));
		assertEquals(0, p.check('u'));
		assertEquals(false, p.isCompleted());
		assertEquals(0, p.check('ï'));
		assertEquals(2, p.check('i'));
		assertEquals(0, p.check('ñ'));
		assertEquals(1, p.check('o'));
		assertEquals(false, p.isCompleted());
		
		Panel p1 = new Panel("Pingüino", "Happy Feet", Categoria.PELICULAS);
		assertEquals(1, p1.check('u'));
		assertEquals(0, p1.check('ü'));
		assertEquals(0, p1.check('u'));
		assertEquals(0, p1.check('ü'));
	}
	
	@Test 
	void test_3() {
		//Comprobar mas cosas del panel, no solo las letras
		
		List<Letra> l = new ArrayList<>(NOMBRE.length());
		for(int i = 0; i< NOMBRE.length(); ++i) {
			l.add(new Letra((char) NOMBRE.charAt(i)));
		}
		Panel p = new Panel(l, "Pintor Español", "PELICULAS");
		assertEquals(Categoria.PELICULAS, p.getCategoria());
		assertEquals(1, p.check('c'));
		assertEquals(0, p.check('ñ'));
		assertEquals(2, p.check('S'));
		assertEquals(1, p.check('P'));
		assertEquals(false, p.isCompleted());
		assertEquals("Pintor Español", p.getPista());
		assertEquals(1, p.check('i'));
		assertEquals(1, p.check('a'));
		assertEquals(1, p.check('o'));
		assertEquals(true, p.isCompleted());
	}
	
	@Test 
	void test_4() {
		//Comprobar que le panel se represente correctamente poniendo signos, espacios y no descubriendo letras
		
		Panel p = new Panel("Hola, ¿que tal?", "Hola", Categoria.CITASPROFESORES);
		assertEquals("_ _ _ _ ,   ¿ _ _ _   _ _ _ ? ", p.toString());
	}
	
	@Test
	void test_5() {
		//Comprobacion de memento
		
		String s = "{\"frase\":[{\"descubierta\":0,\"letra\":85},{\"descubierta\":0,\"letra\":110},{\"descubierta\":1,\"letra\":32},{\"descubierta\":0,\"letra\":116},{\"descubierta\":0,\"letra\":101},{\"descubierta\":0,\"letra\":115},{\"descubierta\":0,\"letra\":116},{\"descubierta\":1,\"letra\":32},{\"descubierta\":0,\"letra\":100},{\"descubierta\":0,\"letra\":101},{\"descubierta\":1,\"letra\":32},{\"descubierta\":0,\"letra\":97},{\"descubierta\":0,\"letra\":110},{\"descubierta\":0,\"letra\":116},{\"descubierta\":0,\"letra\":237},{\"descubierta\":0,\"letra\":103},{\"descubierta\":0,\"letra\":101},{\"descubierta\":0,\"letra\":110},{\"descubierta\":0,\"letra\":111},{\"descubierta\":0,\"letra\":115},{\"descubierta\":1,\"letra\":32},{\"descubierta\":0,\"letra\":110},{\"descubierta\":0,\"letra\":111},{\"descubierta\":1,\"letra\":32},{\"descubierta\":0,\"letra\":115},{\"descubierta\":0,\"letra\":105},{\"descubierta\":0,\"letra\":114},{\"descubierta\":0,\"letra\":118},{\"descubierta\":0,\"letra\":101},{\"descubierta\":1,\"letra\":32},{\"descubierta\":0,\"letra\":112},{\"descubierta\":0,\"letra\":97},{\"descubierta\":0,\"letra\":114},{\"descubierta\":0,\"letra\":97},{\"descubierta\":1,\"letra\":32},{\"descubierta\":0,\"letra\":110},{\"descubierta\":0,\"letra\":97},{\"descubierta\":0,\"letra\":100},{\"descubierta\":0,\"letra\":97}],\"categoria\":\"PELICULAS\",\"pista\":\"COVID19\"}";
		Panel p = new Panel("Un test de antígenos no sirve para nada", "COVID19", Categoria.PELICULAS);
		JSONObject jo = new JSONObject(s);
		assertTrue(jo.similar(p.report()));
		
		Panel p1 = Panel.unpack(jo);
		assertEquals(p1.getCategoria(), p.getCategoria());
		assertEquals(p1.getPista(), p.getPista());
		assertEquals(p1.isCompleted(), p.isCompleted());
		assertEquals(p1.toString(), p.toString());
	}
}
