package misc;

//Esta clase se utiliza para tratar uniformemente las entradas de letras en el panel
public class CharUtilities {
	
	private static final Character VOCALES[] = { 'a','e','i','o','u' };
	private static final Character CONSONANTES[] = { 'b','c','d','f','g','h','j','k','l','m','n','ñ','p','q','r','s','t','v','w','x','y','z'};
	
	public static char toUpper(char c) {
		switch(c) {
		case 'á':
		case 'Á':
			return 'A';
		case 'é':
		case 'É':
			return 'E';
		case 'í':
		case 'Í':
			return 'I';
		case 'ó':
		case 'Ó':
			return 'O';
		case 'ú':
		case 'Ú':
		case 'ü':
		case 'Ü':
			return 'U';
		case 'ñ':
		case 'Ñ':
			return 'Ñ';
		default:
			return Character.toUpperCase(c);
		}
	}
	
	public static boolean isVowel(char c) {
		char aux = CharUtilities.toUpper(c);
		switch(aux) {
		case 'A':
		case 'E':
		case 'I':
		case 'O':
		case 'U':
			return true;
		default:
			return false;
		}
	}

	public static boolean isLetter(char c) {
		return Character.isLetter(toUpper(c)) || toUpper(c) == 'Ñ';
	}
	
	public static char nth_vowel(int n) {
		return VOCALES[n % VOCALES.length];
	}
	
	public static char nth_consonant(int n) {
		return CONSONANTES[n % CONSONANTES.length];
	}
	
}

