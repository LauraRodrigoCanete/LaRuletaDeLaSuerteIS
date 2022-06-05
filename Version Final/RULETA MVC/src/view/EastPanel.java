package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import control.GUIController;
import model.Game;
import model.ModelStatus;
import model.Player;
import model.RDLSObserver;
import model.record.Records;

public class EastPanel extends JPanel implements RDLSObserver, ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JButton throwRoulette, buyVowel, chooseConsonant, help, reset;
	
	private GUIController _ctrl;
	
	static char selected;

	public EastPanel(GUIController ctrl) {
		initGUI();
		_ctrl = ctrl;
		selected = ' ';
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		
		this.add(center, BorderLayout.CENTER);
		
		throwRoulette = new JButton();
		throwRoulette.setActionCommand("throw");
		throwRoulette.setToolTipText("Tira de la ruleta");
		throwRoulette.addActionListener(this);
		throwRoulette.setIcon(new ImageIcon((new ImageIcon("resources/icons/tirar.png")).getImage().getScaledInstance(115, 115, Image.SCALE_SMOOTH)));
		center.add(throwRoulette);
		
		center.add(new JSeparator());
		
		buyVowel = new JButton();
		buyVowel.setActionCommand("buy");
		buyVowel.setToolTipText("Comprar vocal");
		buyVowel.addActionListener(this);
		buyVowel.setIcon(new ImageIcon((new ImageIcon("resources/icons/comprar.png")).getImage().getScaledInstance(115, 115, Image.SCALE_SMOOTH)));
		center.add(buyVowel);
		
		center.add(new JSeparator());
		
		chooseConsonant = new JButton();
		chooseConsonant.setActionCommand("choose");
		chooseConsonant.setToolTipText("Elegir consonante");
		chooseConsonant.addActionListener(this);
		chooseConsonant.setIcon(new ImageIcon((new ImageIcon("resources/icons/choose.png")).getImage().getScaledInstance(115, 115, Image.SCALE_SMOOTH)));
		center.add(chooseConsonant);
		
		center.add(new JSeparator());
		
		help = new JButton();
		help.setActionCommand("help");
		help.setToolTipText("Ayuda");
		help.addActionListener(this);
		help.setIcon(new ImageIcon((new ImageIcon("resources/icons/help.png")).getImage().getScaledInstance(115, 115, Image.SCALE_SMOOTH)));
		center.add(help);
		
		center.add(new JSeparator());
		
		reset = new JButton();
		reset.setActionCommand("reset");
		reset.setToolTipText("Resetea la partida");
		reset.addActionListener(this);
		reset.setIcon(new ImageIcon((new ImageIcon("resources/icons/reset.png")).getImage().getScaledInstance(115, 115, Image.SCALE_SMOOTH)));
		center.add(reset);
		
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		switch(s) {
		case "throw":
			throwR();
			break;
		case "buy":
			buy();
			break;
		case "choose":
			choose();
			break;
		case "help":
			help();
			break;
		case "reset":
			reset();
			break;
		}
		
	}
	
	private void reset() {
		try {
			_ctrl.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void choose() {
		ChooseConsonantDialog dialog = new ChooseConsonantDialog( (Frame) SwingUtilities.getWindowAncestor(this) );
		dialog.open();
		if (selected != ' ')
			_ctrl.elegirConsonate(selected);
		selected = ' ';
	}

	private void buy() {
		ChooseVowelDialog dialog = new ChooseVowelDialog( (Frame) SwingUtilities.getWindowAncestor(this) );
		dialog.open();
		if (selected != ' ')
			_ctrl.comprarVocal(selected);
		selected = ' ';
	}

	private void throwR() {
		_ctrl.throwRoulette();
	}

	private void help() {
		_ctrl.help();
	}

	@Override
	public void onGameStart(Game game, Player jugTurno) {
		if(game.isTurnInServer()) { //Es para no activar los botones del resto de jugadores en red
			throwRoulette.setEnabled(true);
			buyVowel.setEnabled(true); 
			chooseConsonant.setEnabled(true);
		}
		help.setEnabled(true);
		reset.setEnabled(true);
	}

	@Override
	public void onGameEnd(Game game, Player ganador) {}

	@Override
	public void onPlayStart(Game game) {}

	@Override
	public void onTurnChanged(Game game) {
		if(!game.isTurnInServer()) { //Es para no activar los botones del resto de jugadores en red
			throwRoulette.setEnabled(false);
			buyVowel.setEnabled(false); 
			chooseConsonant.setEnabled(false);
		}
	}

	@Override
	public void onRouletteThrown(String infoCasilla, int angulo, int desplazamiento, boolean skip) {}

	@Override
	public void onAttemptMade(Game game, char letra, int veces, int points) {}

	@Override
	public void onHelpRequested() {
		JOptionPane.showMessageDialog(null, _ctrl.getHelp());
	}

	@Override
	public void onReset(Game game, Player jugTurno) {}

	@Override
	public void onExit() {}

	@Override
	public void onMenuOpened() {
		throwRoulette.setEnabled(false);
		buyVowel.setEnabled(false); 
		chooseConsonant.setEnabled(false);
		help.setEnabled(false);
		reset.setEnabled(false);
	}

	@Override
	public void onRecordsOpened(Records _records) {}

	@Override
	public void onRegister(ModelStatus status) {}

	@Override
	public void notify(ModelStatus status, String info) {}

	@Override
	public void onNewRecord() {}

	@Override
	public void onActionEnd() {}
	
	void setThrowEnable(boolean enable) {
		throwRoulette.setEnabled(enable);
	}
	
	void setResetEnable(boolean e) {
		reset.setEnabled(e);
	}

	@Override
	public void onServerAction(Game game, boolean isYourTurn) {
		setEnablePlayButtons(isYourTurn);
	}
	
	private void setEnablePlayButtons(boolean enable) {
		throwRoulette.setEnabled(enable);
		buyVowel.setEnabled(enable);
		chooseConsonant.setEnabled(enable);
	}

}
