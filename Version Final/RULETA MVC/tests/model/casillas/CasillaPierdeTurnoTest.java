package model.casillas;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.ruleta.casillas.CasillaPierdeTurno;

public class CasillaPierdeTurnoTest {
	
	private static final String REPORT = "{\"data\":{},\"type\":\"CASILLA_PIERDE_TURNO\"}";
	private static final String TYPE = "PIERDE EL TURNO : Pierdes el turno";
	private static final String NAME = "PIERDE EL TURNO";
		
	@Test
	void test_1() { //Comprobar atributos y metodos
		CasillaPierdeTurno casillaPierdeTurno = new CasillaPierdeTurno();
		JSONObject jo = new JSONObject(REPORT);
		assertTrue(jo.similar(casillaPierdeTurno.report()));
		assertEquals(NAME, casillaPierdeTurno.getName());
		assertEquals(TYPE, casillaPierdeTurno.toString());
	}
}