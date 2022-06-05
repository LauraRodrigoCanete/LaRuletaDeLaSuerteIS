package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.record.Record;

public class RecordTest {
	private static final String RECORD_1 = "{\"data\":{\"PLAYER\":\"Iker\",\"SCORE\":1500},\"type\":\"RECORD\"}";
	private static final String RECORD_2 = "{\"data\":{\"PLAYER\":\"Laura\",\"SCORE\":1500},\"type\":\"RECORD\"}";
	private static final String RECORD_3 = "{\"data\":{\"PLAYER\":\"Laura\",\"SCORE\":1400},\"type\":\"RECORD\"}";
	private static final String RECORD_4 = "{\"data\":{\"PLAYER\":\"Laura\",\"SCORE\":1400},\"type\":\"RECORD\"}";
	
	@Test
	void test_1() { //Comprobar metodos de Records y el sus reports
		Record r = new Record("Iker", 1500);
		assertEquals("Iker", r.getPlayerName());
		assertEquals(1500, r.getScore());
		JSONObject jo1 = new JSONObject(RECORD_1);
		assertTrue(jo1.similar(r.report()));
		
		r.setFirst("Laura");
		JSONObject jo2 = new JSONObject(RECORD_2);
		assertTrue(jo2.similar(r.report()));
		
		r.setSecond(1400);
		JSONObject jo3 = new JSONObject(RECORD_3);
		assertTrue(jo3.similar(r.report()));
		
		assertEquals("Laura", r.getPlayerName());
		assertEquals(1400, r.getScore());
		JSONObject jo4 = new JSONObject(RECORD_4);
		assertTrue(jo4.similar(r.report()));
	}
}

