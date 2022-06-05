package control.commands;

import java.io.IOException;

import model.Model;

public class ResetCommand extends Command {

	private static final String NAME = "reset";

	private static final String DETAILS = "[r]eset";

	private static final String SHORTCUT = "r";

	private static final String HELP = "resetea la partida actual que se esta jugando";

	public ResetCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(Model model) throws IOException {
		model.reset();
	}
	
}
