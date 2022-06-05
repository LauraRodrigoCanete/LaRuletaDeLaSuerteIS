package control.commands;

import misc.CharUtilities;
import model.*;

public class ChooseConsonantCommand extends Command {

	private final static String NAME = "consonante";
	
	private static final String DETAILS = "[k]onsonante <consonante>";

	private static final String SHORTCUT = "k";

	private static final String HELP = "Elige la consontante que quieres adivinar en el panel";
	
	private static final String CHAR_MSG = "[ERROR]: Solo puedes introducir un caracter";
	
	private static final String CONSONANT_MSG = "[ERROR]: Debes introducir una consonante";
	
	private char _consonante;
	
	public ChooseConsonantCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(Model model) {
		model.elegirConsonante(_consonante);
	}
	
	@Override
	protected Command parse(String[] commandWords) throws Exception {
		if (!matchCommandName(commandWords[0]))
			return null;
		
		if(commandWords.length != 2) 
			throw new IllegalArgumentException(String.format(INCORRECT_NUMBER_OF_ARGS_MSG, NAME, DETAILS));
		
		if(commandWords[1].length() > 1)
			throw new IllegalArgumentException(CHAR_MSG);
		
		if(CharUtilities.isVowel(commandWords[1].charAt(0)))
			throw new IllegalArgumentException(CONSONANT_MSG);
		
		_consonante = commandWords[1].charAt(0);
		
		return this;
	}

}
