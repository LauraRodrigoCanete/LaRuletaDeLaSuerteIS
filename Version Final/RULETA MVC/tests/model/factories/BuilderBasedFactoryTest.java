package model.factories;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.record.Record;
import model.ruleta.casillas.Casilla;
import model.ruleta.casillas.CasillaBote;
import model.ruleta.casillas.CasillaDivide2;
import model.ruleta.casillas.CasillaPierdeTurno;
import model.ruleta.casillas.CasillaPuntos;
import model.ruleta.casillas.CasillaQuiebra;
import model.ruleta.casillas.CasillaX2;

public class BuilderBasedFactoryTest {
	
	/*
	 
	 Tiene unas factorias como atributo que son las que se van a utilizar en el juego
	 
	 test_1:
	 Comprueba si funciona el builderBasedFactory de casillas para que con sus builders cree
	 la casilla CasillaPierdeTurno
	 
	 test_2:
	 Comprueba si funciona el builderBasedFactory de casillas para que con sus builders cree
	 la casilla CasillaPuntos
	 
	 test_3:
	 Comprueba si funciona el builderBasedFactory de casillas para que con sus builders cree
	 la casilla CasillaX2
	 
	 test_4:
	 Comprueba si funciona el builderBasedFactory de casillas para que con sus builders cree
	 la casilla CasillaDivide2
	 
	 test_5:
	 Comprueba como se comporta el builderBasedFactory de casillas para que cuando pides crear
	 una casilla que no corresponde con ningun builder se lance excepcion
	 
	 test_6:
	 Comprueba si funciona el builderBasedFactory de casillas para que con sus builders cree
	 la casilla CasillaQuiebra
	 
	 test_7:
	 Comprueba si funciona el builderBasedFactory de casillas para que con sus builders cree
	 la casilla CasillaBote
	 
	 test_8:
	 Comprueba como se comporta el builderBasedFactory de casillas cuando pides crear
	 algo corrupto y sin sentido [algo que por ejemplo ni si quiera tenga forma de JSON], 
	 no se procese y se lance excepcion

	 -------------------------------------------------------------------------------------
	 
	 test_9:
	 Comprueba como se comporta el builderBasedFactory de records cuando pides crear
	 algo corrupto y sin sentido [algo que por ejemplo ni si quiera tenga forma de JSON], 
	 no se procese y se lance excepcion
	 
	 test_10:
	 Comprueba como se comporta el builderBasedFactory de records para que cuando pides crear
	 un JSON que no se corresponde con un record se lance excepcion
	 
	 test_11:
     Comprueba si funciona el builderBasedFactory de records para que con sus builders cree
	 la un record
	 
	*/
	
	
	
	

	private static  Factory<Casilla> createCasillaFactory() {
		List<Builder<Casilla>> builders = new ArrayList<>();
		builders.add(new NewCasillaDivide2Builder());
		builders.add(new NewCasillaPierdeTurnoBuilder());
		builders.add(new NewCasillaPuntosBuilder());
		builders.add(new NewCasillaX2Builder());
		builders.add(new NewCasillaQuiebraBuilder());
		builders.add(new NewCasillaBoteBuilder());
		return new BuilderBasedFactory<Casilla>(builders);
	}

	private static  Factory<Record> createRecordFactory() {
		List<Builder<Record>> builder = new ArrayList<>();
		builder.add(new NewRecordBuilder());
		return new BuilderBasedFactory<Record>(builder);
	}

	@Test
	void test_1() {
		Factory<Casilla> casillasFactory = createCasillaFactory();

		String inputJSon = "{ \"type\" : \"CASILLA_DIVIDE_2\", \"data\" : {} }";

		assertTrue(casillasFactory.createInstance(new JSONObject(inputJSon)) instanceof CasillaDivide2);
	}

	@Test
	void test_2() {
		Factory<Casilla> casillasFactory = createCasillaFactory();

		String inputJSon = "{ \"type\" : \"CASILLA_PIERDE_TURNO\", \"data\" : {} }";

		assertTrue(casillasFactory.createInstance(new JSONObject(inputJSon)) instanceof CasillaPierdeTurno);
	}

	@Test
	void test_3() {
		Factory<Casilla> casillasFactory = createCasillaFactory();

		String inputJSon = "{ \"type\" : \"CASILLA_PUNTOS\", \"data\" : {\"PUNTOS\" : 75} }";

		assertTrue(casillasFactory.createInstance(new JSONObject(inputJSon)) instanceof CasillaPuntos);
	}

	@Test
	void test_4() {
		Factory<Casilla> casillasFactory = createCasillaFactory();

		String inputJSon = "{ \"type\" : \"CASILLA_X2\", \"data\" : {} }";

		assertTrue(casillasFactory.createInstance(new JSONObject(inputJSon)) instanceof CasillaX2);
	}

	@Test
	void test_5() {
		Factory<Casilla> casillasFactory = createCasillaFactory();

		String inputJSon = "{ \"type\" : \"bla\", \"data\" : {} }";

		assertThrows(Exception.class, () -> casillasFactory.createInstance(new JSONObject(inputJSon)));
	}

	
	@Test
	void test_6() {
		Factory<Casilla> casillasFactory = createCasillaFactory();

		String inputJSon = "{ \"type\" : \"CASILLA_QUIEBRA\", \"data\" : {} }";

		assertTrue(casillasFactory.createInstance(new JSONObject(inputJSon)) instanceof CasillaQuiebra);
	}

	@Test
	void test_7() {
		Factory<Casilla> casillasFactory = createCasillaFactory();

		String inputJSon = "{ \"type\" : \"CASILLA_BOTE\", \"data\" : {} }";

		assertTrue(casillasFactory.createInstance(new JSONObject(inputJSon)) instanceof CasillaBote);
	}
	
	@Test
	void test_8() {
		Factory<Casilla> casillasFactory = createCasillaFactory();

		String inputJSon = "bla";

		assertThrows(Exception.class, () -> casillasFactory.createInstance(new JSONObject(inputJSon)));
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@Test
	void test_9() {
		Factory<Record> recordsFactory = createRecordFactory();

		String inputJSon = "bla";

		assertThrows(Exception.class, () -> recordsFactory.createInstance(new JSONObject(inputJSon)));
	}
	
	@Test
	void test_10() {
		Factory<Record> recordsFactory = createRecordFactory();

		String inputJSon = "{\"type\" : \"bla\", \"data\" : {} }";

		assertThrows(Exception.class, () -> recordsFactory.createInstance(new JSONObject(inputJSon)));
	}
	
	@Test
	void test_11() {
		Factory<Record> recordsFactory = createRecordFactory();

		String inputJSon = "{ \"data\" : {\"PLAYER\" : \"Iker\" , \"SCORE\" : 800} , \"type\" : \"RECORD\" }";

		assertTrue(recordsFactory.createInstance(new JSONObject(inputJSon)) instanceof Record);
	}

}
