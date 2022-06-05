package control.commands;

import model.Model;

public class SaveCommand extends Command {
	
	private static final String NAME = "guardar";

	private static final String DETAILS = "[g]uardar";

	private static final String SHORTCUT = "g";

	private static final String HELP = "guarda el estado del juego";

	public SaveCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(Model model) throws Exception {
		model.guardarPartida(null);
	}

}
