package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.panel.Letra;

public class LetraTest {
	
	/*
	 Todo aquello que no sean letras debe descubrirse nada mas crearse, mientras que las letras no deben
	 descubrirse. Esta es una de las cosas que se van a comprobar
	*/
	
	private static final String L1 = "{\"descubierta\":1,\"letra\":46}";
	private static final String L2 = "{\"descubierta\":0,\"letra\":97}";
	private static final String L3 = "{\"descubierta\":1,\"letra\":241}";
	private static final String L4 = "{\"descubierta\":1,\"letra\":225}";
	private static final String L5 = "{\"descubierta\":1,\"letra\":225}";
	private static final String L6 = "{\"descubierta\":1,\"letra\":102}";
	private static final String L7 = "{\"descubierta\":1,\"letra\":103}";
	private static final String L8 = "{\"descubierta\":1,\"letra\":193}";
	
	@Test
	void test_1() { 
		//Comprobamos que el punto como no es letra se debe descubrir
		Letra l1 = new Letra('.');
		assertEquals(true, l1.isDiscovered());
		assertEquals(".", l1.toString());
		
		//Comprobacion Memento
		JSONObject jo1 = new JSONObject(L1);
		assertTrue(jo1.similar(l1.report()));
		Letra l1_unpack = Letra.unpack(jo1);
		assertEquals(true, Letra.equals(l1,l1_unpack));
	}
		
// --------------------------------------------------
	
	@Test
	void test_2() {
		//Comprobamos que la vocal a como es una letra se debe descubrir
		Letra l2 = new Letra('a');
		assertEquals(false, l2.isDiscovered());
		assertEquals("a", l2.toString());
		
		//Comprobacion Memento
		JSONObject jo2 = new JSONObject(L2);
		assertTrue(jo2.similar(l2.report()));
		Letra l2_unpack = Letra.unpack(jo2);
		assertEquals(true, Letra.equals(l2,l2_unpack));
	}
	
// --------------------------------------------------
	
	@Test
	void test_3() {
		//Comprobamos que la consonante ñ como es una letra se debe descubrir ademas como es una letra rara
		//hacemos varias comprobaciones para ver que solo se descubre con el check si exactamente se hace con ñ
		Letra l3 = new Letra('ñ');
		assertEquals(false, l3.isDiscovered());
		l3.check('n');
		assertEquals(false, l3.isDiscovered());
		l3.check('ñ');
		assertEquals(true, l3.isDiscovered());
		assertEquals("ñ", l3.toString());
		
		//Comprobacion Memento
		JSONObject jo3 = new JSONObject(L3);
		assertTrue(jo3.similar(l3.report()));	
		Letra l3_unpack = Letra.unpack(jo3);
		assertEquals(true, Letra.equals(l3,l3_unpack));	
		
	}
		
// --------------------------------------------------
	
	@Test
	void test_4() {
		//Comprobamos que la vocal á como es una letra se debe descubrir ademas como es una letra rara
		//hacemos varias comprobaciones para ver no hay distincion de tildes
		Letra l4 = new Letra('á');
		assertEquals(false, l4.isDiscovered());
		assertEquals(true, l4.check('a'));
		assertEquals(true, l4.isDiscovered());
		assertEquals(false, l4.check('á'));
		assertEquals(true, l4.isDiscovered());
		assertEquals("á", l4.toString());
		
		//Comprobacion Memento
		JSONObject jo4 = new JSONObject(L4);
		assertTrue(jo4.similar(l4.report()));
		Letra l4_unpack = Letra.unpack(jo4);
		assertEquals(true, Letra.equals(l4,l4_unpack));
	}
		
// --------------------------------------------------	
		
	@Test
	void test_5() {
		//Comprobamos que la vocal á como es una letra se debe descubrir ademas como es una letra rara
		//hacemos varias comprobaciones para ver no hay distincion de tildes
		Letra l5 = new Letra('á');
		assertEquals(false, l5.isDiscovered());
		assertEquals(true, l5.check('á'));
		assertEquals(true, l5.isDiscovered());
		assertEquals(false, l5.check('a'));
		assertEquals(true, l5.isDiscovered());
		assertEquals("á", l5.toString());
		
		//Comprobacion Memento
		JSONObject jo5 = new JSONObject(L5);
		assertTrue(jo5.similar(l5.report()));
		Letra l5_unpack = Letra.unpack(jo5);
		assertEquals(true, Letra.equals(l5,l5_unpack));
	}
		
// --------------------------------------------------		
		
	@Test
	void test_6() {
		//Comprobamos que la consonante f como es una letra se debe descubrir ademas hacemos varias
		//comprobaciones para ver que solo se descubre con el check si exactamente se hace con f
		Letra l6 = new Letra ('f', true);
		assertEquals(true, l6.isDiscovered());
		assertEquals(false, l6.check('n'));
		assertEquals(false, l6.check('f'));
		assertEquals("f", l6.toString());
		
		//Comprobacion Memento
		JSONObject jo6 = new JSONObject(L6);
		assertTrue(jo6.similar(l6.report()));
		Letra l6_unpack = Letra.unpack(jo6);
		assertEquals(true, Letra.equals(l6,l6_unpack));
	}
		
// --------------------------------------------------
		
	@Test
	void test_7() {
		//Comprobamos que la consonante g como es una letra se debe descubrir ademas hacemos varias
		//comprobaciones para ver que solo se descubre con el check si exactamente se hace con g
		Letra l7 = new Letra ('g', false);
		assertEquals(false, l7.isDiscovered());
		assertEquals(false, l7.check('h'));
		assertEquals(true, l7.check('g'));
		assertEquals(false, l7.check('g'));
		assertEquals("g", l7.toString());
		
		//Comprobacion Memento
		JSONObject jo7 = new JSONObject(L7);
		assertTrue(jo7.similar(l7.report()));
		Letra l7_unpack = Letra.unpack(jo7);
		assertEquals(true, Letra.equals(l7,l7_unpack));
	}
		
// --------------------------------------------------
		
	@Test
	void test_8() {
		//Comprobamos que la vocal Á como es una letra se debe descubrir ademas como es una letra rara
		//hacemos varias comprobaciones para ver no hay distincion de tildes ni de mayusculas
		Letra l8 = new Letra('Á');
		assertEquals(false, l8.isDiscovered());
		assertEquals(true, l8.check('a'));
		assertEquals(true, l8.isDiscovered());
		assertEquals(false, l8.check('Á'));
		assertEquals(true, l8.isDiscovered());
		assertEquals("Á", l8.toString());
		
		//Comprobacion Memento
		JSONObject jo8 = new JSONObject(L8);
		assertTrue(jo8.similar(l8.report()));
		Letra l8_unpack = Letra.unpack(jo8);
		assertEquals(true, Letra.equals(l8,l8_unpack));
	}
}
