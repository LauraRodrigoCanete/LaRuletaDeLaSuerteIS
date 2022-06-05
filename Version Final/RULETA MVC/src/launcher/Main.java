package launcher;

import java.io.*;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.*;

import control.ConsoleController;
import control.GUIController;
import model.Model;
import model.factories.Factorias;
import view.*;

public class Main {
	private static enum ViewMode { CONSOLE, GUI }
	private static ViewMode _mode;
	
	//método para extraer las dos opciones de los argumentos del run configuration
	private static void parseArgs(String[] args) {
		Options cmdLineOptions = buildOptions();

		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseViewModeOption(line);
			
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
	}
	
	//opción para pedir mensaje de ayuda, por defecto no sale
	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}
	
	//opción para seleccionar el modo de juego, por defecto GUI
	private static void parseViewModeOption(CommandLine line) throws ParseException {
		_mode = ViewMode.valueOf(line.getOptionValue("m", (ViewMode.GUI).toString()).toUpperCase());
		if(_mode == null)
			throw new ParseException("Mode must be one of these: CONSOLE, GUI");
	}
	
	private static Options buildOptions() {
		Options cmdLineOptions = new Options();
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Game mode, console or GUI (default)").build());
		return cmdLineOptions;
	}
	
	//CONSOLE
	private static void startBatchMode() throws IOException {
		Model model = new Model();
		model.setFastMode();
		ConsoleController control = new ConsoleController(model);
		ConsoleView consoleView = new ConsoleView(control);
		control.loadRecords();
		
		control.run();
	}
	
	//GUI
	private static void startGuiMode() throws IOException {
		Model model = new Model();
		GUIController control = new GUIController(model);
		control.loadRecords();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(control);
			}
		});

		
	}
	
	private static void start(String[] args) throws IOException {
		Factorias.initFactories();
		parseArgs(args);
		switch(_mode) {
		case CONSOLE:
			startBatchMode();
			break;
		case GUI:
			startGuiMode();
		}
	}
	
	
	public static void main(String[] args) {		
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
