package model.factories;


import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.ruleta.Ruleta;


public class NewRuletaBuilderTest {
	
	/*
	 test_1 y test_2: 
	 comprueba que el builder NewRuletaBuilderTest cree una instancia correcta 
	 de la ruleta a raiz de la informacion del JSON que es la que se espera utilizar en
	 la aplicación
	 
	 Primero una ruleta sin casillas para solo centrarnos en la creación de la Clase Ruleta y luego con
	 una casilla para ver si funcionan conjuntamente la creacion de la casilla y como esta se añade a la ruleta
	*/
	
	
	@Test
	void test_1() {
		NewRuletaBuilder eb = new NewRuletaBuilder();
		
		String inputJSon = "{ \"data\" : { \"RULETA\" : [] } , \"type\" : \"RULETA\" }"; // ruleta sin casillas
		Ruleta o = eb.createInstance(new JSONObject(inputJSon));
		assertTrue( o instanceof Ruleta );
		
	}
	
	
	@Test
	void test_2() {
		NewRuletaBuilder eb = new NewRuletaBuilder();
		
		String inputJSon = "{ \"data\" : { \"RULETA\" : [ {\"type\" : \"CASILLA_DIVIDE_2\" , \"data\" : {}} ] } , \"type\" : \"RULETA\" }"; //ruleta con una casilla
		Factorias.initFactories(); //Esto es para iniciar las variables estaticas de factoria
		Ruleta o = eb.createInstance(new JSONObject(inputJSon));
		assertTrue( o instanceof Ruleta );
		
	}
}

