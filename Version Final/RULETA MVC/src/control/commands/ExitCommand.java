package control.commands;

import model.Model;

public class ExitCommand extends Command {

	private static final String NAME = "exit";

	private static final String DETAILS = "[e]xit";

	private static final String SHORTCUT = "e";

	private static final String HELP = "salir del juego";

	public ExitCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	@Override
	public void execute(Model model) {
		model.exitGame();
	}

}
