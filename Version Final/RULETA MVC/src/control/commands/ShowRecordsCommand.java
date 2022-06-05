package control.commands;

import model.Model;

public class ShowRecordsCommand extends Command {

	private static final String NAME = "records";

	private static final String DETAILS = "[rec]ords";

	private static final String SHORTCUT = "rec";

	private static final String HELP = "muestra los records del juego";
	
	public ShowRecordsCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(Model model) throws Exception {
		model.showRecords();
	}

}
