package model.casillas;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.ruleta.casillas.CasillaDivide2;

public class CasillaDivide2Test {
	
	private static final String REPORT = "{\"data\":{},\"type\":\"CASILLA_DIVIDE_2\"}";
	private static final String TYPE = "CASILLA 1/2 : Tus puntos disminuyen a la mitad";
	private static final String NAME = "CASILLA 1/2";
		
	@Test
	void test_1() { //Comprobar atributos y metodos
		CasillaDivide2 casillaDivide2 = new CasillaDivide2();
		JSONObject jo = new JSONObject(REPORT);
		assertTrue(jo.similar(casillaDivide2.report()));
		assertEquals(NAME, casillaDivide2.getName());
		assertEquals(TYPE, casillaDivide2.toString());
	}
}
