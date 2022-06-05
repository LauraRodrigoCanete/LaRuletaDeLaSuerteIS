package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.panel.Letra;

public class PlayerTest {
	
	private static final String REPORT = "{\"points\":45,\"name\":\"Juan\"}\n";

	@Test
	void methods_test() { //Comprobar los metodos de player
		Player p = new Player("Juan", 10);
		
		p.addPoints(5);
		assertEquals(15, p.getPoints());
		
		p.pointsRatio(3);
		assertEquals(45, p.getPoints());
		
		p.reset();
		assertEquals(0, p.getPoints());
	}
	
	@Test 
	void report_test() { //Comprobar el memento de player
		Player p = new Player("Juan", 45);
		assertTrue(new JSONObject(REPORT).similar(p.report()));
		JSONObject jo = new JSONObject(REPORT);
		Player player = Player.unpack(jo);
		assertEquals("Juan", player.getName());
		assertEquals(45, player.getPoints());
	}
}
