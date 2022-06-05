package model.factories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;

import model.ruleta.casillas.Casilla;
import model.ruleta.casillas.CasillaBote;

public class NewCasillaBoteBuilderTest { 
	
	/*
	 test_1: 
	 comprueba que el builder NewCasillaBoteBuilder cree una instancia correcta 
	 de casillaBote a raiz de la informacion del JSON que es la que se espera utilizar en
	 la aplicación para crear todas las casillas de la ruleta
	*/

	@Test
	void test_1() {
		NewCasillaBoteBuilder eb = new NewCasillaBoteBuilder();
		
		String inputJSon = "{ \"type\" : \"CASILLA_BOTE\", \"data\" : {} }";
		Casilla o = eb.createInstance(new JSONObject(inputJSon));
		assertTrue( o instanceof CasillaBote );
		
	}
}
