package control.commands;

import model.Model;

public class HelpCommand extends Command{
	private static final String NAME = "help";

	private static final String DETAILS = "[h]elp";

	private static final String SHORTCUT = "h";

	private static final String HELP = "enseña el menu de ayuda";

	public HelpCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(Model model) {
		model.help();
	}
}
