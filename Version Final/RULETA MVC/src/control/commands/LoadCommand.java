package control.commands;

import model.Model;

public class LoadCommand extends Command {

	private static final String NAME = "cargar";

	private static final String DETAILS = "cargar | [l]";

	private static final String SHORTCUT = "l";

	private static final String HELP = "carga la partida guardada";
	
	public LoadCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(Model model) throws Exception {
		model.cargarPartida(null);
	}

}
