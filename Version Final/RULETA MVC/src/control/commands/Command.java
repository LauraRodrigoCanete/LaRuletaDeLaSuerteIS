package control.commands;

import model.*;

public abstract class Command {

	protected static final String INCORRECT_NUMBER_OF_ARGS_MSG = "[ERROR]: Número de argumentos incorrecto para comando %s: %s";

	/* @formatter:off */
	protected static final Command[] AVAILABLE_COMMANDS = { //Lista de todos los comandos disponibles en el modo consola
		new HelpCommand(),
		new ShowRecordsCommand(),
		new NewGameCommand(),
		new LoadCommand(),
		new ResetCommand(),
		new SaveCommand(),
		new ExitCommand(),
		new ThrowCommand(),
		new BuyVowelCommand(),
		new ChooseConsonantCommand(),
	};
	/* @formatter:on */

	public static Command getCommand(String[] commandWords) throws Exception {
		Command d = null;
		for(Command c : AVAILABLE_COMMANDS) {
			d = c.parse(commandWords);
			if(d != null) //Si un comando devuelve un objeto no nulo, se ha encontrado el comando
				return d;
		}
		return d;
	}

	private final String name;

	private final String shortcut;

	private final String details;

	private final String help;
	

	public Command(String name, String shortcut, String details, String help) {
		this.name = name;
		this.shortcut = shortcut;
		this.details = details;
		this.help = help;
	}

	//Cada clase implementará de manera diferente este execute
	public abstract void execute(Model model) throws Exception; 

	protected boolean matchCommandName(String name) {
		return this.shortcut.equalsIgnoreCase(name) || this.name.equalsIgnoreCase(name);
	}

	
	//Comprueba si la secuencia coincide con el shortcut o el nombre del comando
	//Los comandos que así lo requieran, podrán sobreescribir este método, para cambiar el parseo
	protected Command parse(String[] words) throws Exception { 
		if (matchCommandName(words[0])) {
			if (words.length != 1)
				throw new IllegalArgumentException(String.format(INCORRECT_NUMBER_OF_ARGS_MSG, name, details));
			else
				return this;
		}
		return null;
	} 
	

	public String infoForHelp() {
		return details + ": " + help;
	}
	
	public static String getHelp() {
		StringBuilder buffer = new StringBuilder("Available commands:");
		for(Command c : AVAILABLE_COMMANDS) {
			buffer.append(System.lineSeparator())
				.append(c.infoForHelp());
		}
		return buffer.toString();
	}

}
