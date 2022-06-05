package control.commands;

import misc.CharUtilities;
import model.Model;

public class BuyVowelCommand extends Command{
	
	private final static String NAME = "comprar";
	
	private static final String DETAILS = "[c]omprar <vocal>";

	private static final String SHORTCUT = "c";

	private static final String HELP = "comprar vocal con los puntos del jugador";
	
	private static final String CHAR_MSG = "[ERROR]: Solo puedes introducir un caracter";
	
	private static final String VOWEL_MSG = "[ERROR]: Debes introducir una vocal";
	
	private char _vocal;

	public BuyVowelCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	
	@Override
	public void execute(Model model) {
		model.comprarVocal(_vocal);
	}
	
	@Override
	protected Command parse(String [] commandWords) throws Exception {
		if (!matchCommandName(commandWords[0]))
			return null;
		
		if(commandWords.length != 2)
			throw new IllegalArgumentException(String.format(INCORRECT_NUMBER_OF_ARGS_MSG, NAME, DETAILS));
		
		if(commandWords[1].length() > 1)
			throw new IllegalArgumentException(CHAR_MSG);
		
		if(!CharUtilities.isVowel(commandWords[1].charAt(0)))
			throw new IllegalArgumentException(VOWEL_MSG);
		
		_vocal = commandWords[1].charAt(0);
		
		return this;
	}

}
