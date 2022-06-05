package model.factories;

import java.util.ArrayList;
import java.util.List;

import model.record.Record;
import model.ruleta.casillas.Casilla;

public class Factorias { 
	/*
	 Esta clase guarda las factorias para iniciar los records y las casillas y así proporcionar
	 un acceso único en la aplicación para la generación y creación de Records y Casillas
	 */
	 
	public static Factory<Casilla> factoria_casillas;
	public static Factory<Record> factoria_records;
	
	public static void initFactories() {
		List<Builder<Casilla>> builders = new ArrayList<>();
		builders.add(new NewCasillaDivide2Builder());
		builders.add(new NewCasillaPierdeTurnoBuilder());
		builders.add(new NewCasillaPuntosBuilder());
		builders.add(new NewCasillaX2Builder());
		builders.add(new NewCasillaQuiebraBuilder());
		builders.add(new NewCasillaBoteBuilder());
		factoria_casillas = new BuilderBasedFactory<Casilla>(builders);
		
		List<Builder<Record>> builder = new ArrayList<>();
		builder.add(new NewRecordBuilder());
		factoria_records = new BuilderBasedFactory<Record>(builder);
	}
}