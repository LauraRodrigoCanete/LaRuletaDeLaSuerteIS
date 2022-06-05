package model.factories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;

import model.ruleta.casillas.Casilla;
import model.ruleta.casillas.CasillaX2;

public class NewCasillaX2BuilderTest {
	
	/*
	 test_1: 
	 comprueba que el builder NewCasillaX2BuilderTest cree una instancia correcta 
	 de casillaX2 a raiz de la informacion del JSON que es la que se espera utilizar en
	 la aplicación para crear todas las casillas de la ruleta
	*/
	
	@Test
	void test_1() {
		NewCasillaX2Builder eb = new NewCasillaX2Builder();
		
		String inputJSon = "{ \"type\" : \"CASILLA_X2\", \"data\" : {} }";
		Casilla o = eb.createInstance(new JSONObject(inputJSon));
		assertTrue( o instanceof CasillaX2 );
		
	}
}