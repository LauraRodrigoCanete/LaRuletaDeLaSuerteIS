package model.casillas;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.ruleta.casillas.CasillaBote;

public class CasillaBoteTest {
	
	private static final String REPORT = "{\"data\":{},\"type\":\"CASILLA_BOTE\"}";
	private static final String TYPE = "CASILLA BOTE : Llévate el bote acumulado";
	private static final String NAME = "CASILLA BOTE";
		
	@Test
	void test_1() { //Comprobar atributos y metodos
		CasillaBote casillaBote = new CasillaBote();
		JSONObject jo = new JSONObject(REPORT);
		assertTrue(jo.similar(casillaBote.report()));
		assertEquals(NAME, casillaBote.getName());
		assertEquals(TYPE, casillaBote.toString());
	}
}
