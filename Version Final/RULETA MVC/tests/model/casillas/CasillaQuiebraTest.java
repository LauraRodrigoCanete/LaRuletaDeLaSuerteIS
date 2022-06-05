package model.casillas;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.ruleta.casillas.CasillaQuiebra;

public class CasillaQuiebraTest {
	
	private static final String REPORT = "{\"data\":{},\"type\":\"CASILLA_QUIEBRA\"}";
	private static final String TYPE = "QUIEBRA : Tus puntos disminuyen a 0 y pierdes el turno";
	private static final String NAME = "QUIEBRA";
		
	@Test
	void test_1() { //Comprobar atributos y metodos
		CasillaQuiebra casillaQuiebra = new CasillaQuiebra();
		JSONObject jo = new JSONObject(REPORT);
		assertTrue(jo.similar(casillaQuiebra.report()));
		assertEquals(NAME, casillaQuiebra.getName());
		assertEquals(TYPE, casillaQuiebra.toString());
	}
}
