package control;

import java.util.Scanner;


//Tenemos esta clase para acoger el patrón singleton en la clase Scanner ya que más d euna instancia de esta clase pueden ocasionar  problemas
//al leer del mismo flujo
public class Lectura {
	private static Scanner scanner;
	
	private Lectura() {};
	
	public static Scanner getScanner() {
		if(scanner == null)
			scanner = new Scanner(System.in);

		return scanner;
	}
	
	public static void closeScanner() {
		if(scanner != null)
			scanner.close();
		scanner = null;
	}
}
