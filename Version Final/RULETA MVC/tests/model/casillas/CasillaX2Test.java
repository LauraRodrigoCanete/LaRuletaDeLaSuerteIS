package model.casillas;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import model.ruleta.casillas.CasillaX2;

public class CasillaX2Test {
	
	private static final String REPORT = "{\"data\":{},\"type\":\"CASILLA_X2\"}";
	private static final String TYPE = "CASILLA x2 : Tus puntos se duplican";
	private static final String NAME = "CASILLA x2";
		
	@Test
	void test_1() { //Comprobar atributos y metodos
		CasillaX2 casillaX2 = new CasillaX2();
		JSONObject jo = new JSONObject(REPORT);
		assertTrue(jo.similar(casillaX2.report()));
		assertEquals(NAME, casillaX2.getName());
		assertEquals(TYPE, casillaX2.toString());
	}
}
