package model.casillas;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;


import model.ruleta.casillas.CasillaPuntos;

public class CasillaPuntosTest {
	
	private static final String REPORT = "{\"data\":{ \"PUNTOS\" : 75 },\"type\":\"CASILLA_PUNTOS\"}";
	private static final String TYPE = "CASILLA DE PUNTOS: 75 PUNTOS";
	private static final String NAME = "75";
		
	@Test
	void test_1() { //Comprobar atributos y metodos
		CasillaPuntos casillaPuntos = new CasillaPuntos(75);
		JSONObject jo = new JSONObject(REPORT);
		assertTrue(jo.similar(casillaPuntos.report()));
		assertEquals(NAME, casillaPuntos.getName());
		assertEquals(TYPE, casillaPuntos.toString());
	}
}
