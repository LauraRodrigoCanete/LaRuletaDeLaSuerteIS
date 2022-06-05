package model.factories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.record.Record;

import org.json.JSONObject;

public class NewRecordBuilderTest {
	
	/*
	 test_1: 
	 comprueba que el builder NewRecordBuilderTest cree una instancia correcta 
	 de record a raiz de la informacion del JSON que es la que se espera utilizar en
	 la aplicación para guardar la información del ranking de records conseguidos en el juego
	*/
	

	@Test
	void test_1() {
		NewRecordBuilder eb = new NewRecordBuilder();
		
		String inputJSon = "{ \"data\" : {\"PLAYER\" : \"Iker\" , \"SCORE\" : 800} , \"type\" : \"RECORD\" }";
		Record o = eb.createInstance(new JSONObject(inputJSon));
		assertTrue( o instanceof Record );
		
	}
}
