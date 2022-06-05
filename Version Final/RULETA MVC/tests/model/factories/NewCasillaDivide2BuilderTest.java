package model.factories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;

import model.ruleta.casillas.Casilla;
import model.ruleta.casillas.CasillaDivide2;

public class NewCasillaDivide2BuilderTest {
	
	/*
	 test_1: 
	 comprueba que el builder NewCasillaDivide2BuilderTest cree una instancia correcta 
	 de casillaDivide2 a raiz de la informacion del JSON que es la que se espera utilizar en
	 la aplicación para crear todas las casillas de la ruleta
	*/ 
	
	@Test
	void test_1() {
		NewCasillaDivide2Builder eb = new NewCasillaDivide2Builder();
		
		String inputJSon = "{ \"type\" : \"CASILLA_DIVIDE_2\", \"data\" : {} }";
		Casilla o = eb.createInstance(new JSONObject(inputJSon));
		assertTrue( o instanceof CasillaDivide2 );
		
	}
}
