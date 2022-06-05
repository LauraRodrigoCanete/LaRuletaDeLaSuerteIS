package model.panel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Esta clase va a guardar todas las frases del juego, cogiéndolas del fichero del atributo ARCHIVO
public class Diccionario {
	private static final String ARCHIVO = "resources/frases_ruleta.txt";
	private static final String LOAD_ERROR_MSG = "LOADING FILE COULD NOT BE FOUND";
	private List<Panel> paneles;
	
	public Diccionario () throws IOException { 
		this.paneles = new ArrayList<>();
		
		//Crea un BufferedReader con los parámetros que se le introducen línea a línea
		try(BufferedReader in = new BufferedReader(new FileReader(ARCHIVO))){
			String frase;
			String pista;
			String categoria;
			Categoria cat;
			
			while ((frase = in.readLine()) != null && !frase.isEmpty()) {
				pista = in.readLine();
				categoria = in.readLine();
				cat = Categoria.valueOf(categoria);
				if(cat == null) throw new IllegalArgumentException("El formato del fichero de las frases no es correcto");
				Panel panel = new Panel (frase, pista, cat);
				this.paneles.add(panel);
				
			}
			
		} catch (IOException e) {
			throw new IOException(String.format("[ERROR]: %s", LOAD_ERROR_MSG), e);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		
	}
	
	public Panel getRandomPanel(Random random) {
		double indice = random.nextDouble();
		return this.paneles.get((int)(indice * paneles.size()));
	}

}
