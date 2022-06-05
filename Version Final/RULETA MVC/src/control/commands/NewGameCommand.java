package control.commands;

import java.util.ArrayList;
import java.util.List;

import model.Model;

public class NewGameCommand extends Command {

	private static final String NAME = "jugar";

	private static final String DETAILS = "[j]ugar <nºjugadores> (bote?)<SI/NO> [nombres]";

	private static final String SHORTCUT = "j";

	private static final String HELP = "crea una nueva partida con el número de jugadores indicado y con una ruleta con o sin bote según se haya elegido.";
	
	private static final String NUM_PLAYERS_MSG = "[ERROR]: El número de jugadores debe ser un número entero entre 2 y 4";
	
	private static final String BOTE_ARG_MSG = "[ERROR]: Se debe indicar si se quiere bote o no (SI/NO)";
	
	private static final String REPETED_NAME_MSG = "[ERROR]: No se deben repetir nombres";
	
	private int _nPlayers;
	private boolean _conBote;
	private List<String> _names;
	
	
	public NewGameCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	
	//Se sobreescribe el parse para comprobar que tienen los siguientes parámetros: 
	//				número de jugadores (debe haber entre dos y cuatro)
	//				SI o NO para comprobar si quiere bote o no
	//				nombres de los jugadores (debe haber tantos como número de jugadores)
	@Override 
	protected Command parse(String [] words) throws Exception {
		if (!matchCommandName(words[0])) 
			return null;
		
		_names = new ArrayList<String>();
		try {
			_nPlayers = Integer.parseInt(words[1]);
			if(_nPlayers < 2 || _nPlayers > 4)
				throw new IllegalArgumentException(NUM_PLAYERS_MSG);
			
			if("SI".equalsIgnoreCase(words[2])) _conBote = true;
			else if("NO".equalsIgnoreCase(words[2])) _conBote = false;
			else throw new IllegalArgumentException(BOTE_ARG_MSG);
			
			for(int i = 0; i < _nPlayers; ++i) {
				String name = words[i + 3];
				for(String n : _names) {
					if(n.equals(name)) throw new IllegalArgumentException(REPETED_NAME_MSG);
				}
				_names.add(name);
			}
		} catch (NumberFormatException e1) {
			throw new IllegalArgumentException(NUM_PLAYERS_MSG);
		} catch (ArrayIndexOutOfBoundsException e2) {
			throw new IllegalArgumentException(String.format(INCORRECT_NUMBER_OF_ARGS_MSG, NAME, DETAILS));
		}
		
		return this;
	}

	@Override
	public void execute(Model model) throws Exception {
		model.newGame(_nPlayers, _conBote, _names);
	}

}
