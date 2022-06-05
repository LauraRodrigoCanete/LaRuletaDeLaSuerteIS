package control.commands;

import model.Model;

public class ThrowCommand extends Command{
	private static final String NAME = "Tirar ruleta";

	private static final String DETAILS = "(tirar) []";

	private static final String SHORTCUT = "";

	private static final String HELP = "tira la ruleta para obtener una puntuacion";
	
	public ThrowCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(Model model) {
		model.tirarRuleta();
	}
	
	//Se sobreescribe para que tirar se acepte como un salto de línea
	@Override
	protected Command parse(String[] commandWords) throws Exception {
		if ("".equalsIgnoreCase(commandWords[0])) {
			commandWords[0] = SHORTCUT;
		}
		return super.parse(commandWords);
	}
}
