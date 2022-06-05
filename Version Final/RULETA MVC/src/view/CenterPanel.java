package view;

import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.GUIController;
import model.Game;
import model.ModelStatus;
import model.Player;
import model.RDLSObserver;
import model.record.Records;


public class CenterPanel extends JPanel implements RDLSObserver{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GUIController _ctrl;
	private JLabel panel, pista, categoria;
	
	
	public CenterPanel(GUIController _ctrl) {
		this._ctrl = _ctrl;
		this.initGUI();
		this._ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panel = new JLabel();
		panel.setFont(new Font("Serif", Font.PLAIN, 25));
		pista = new JLabel();
		pista.setFont(new Font("Serif", Font.PLAIN, 25));
		categoria = new JLabel();
		categoria.setFont(new Font("Serif", Font.PLAIN, 25));
		
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		pista.setAlignmentX(Component.CENTER_ALIGNMENT);
		categoria.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.add(panel);
		this.add(pista);
		this.add(categoria);
		
		this.add(Box.createVerticalGlue());
		
		this.setVisible(true);
	}

	@Override
	public void onGameStart(Game game, Player jugTurno) {
		panel.setText(game.getPanel());
		pista.setText(game.getPista());
		categoria.setText(game.getCategoria());
	}

	@Override
	public void onGameEnd(Game game, Player ganador) {
		panel.setText(game.getPanel());
	}

	@Override
	public void onPlayStart(Game game) {
		
	}

	@Override
	public void onTurnChanged(Game game) {
	}

	@Override
	public void onRouletteThrown(String infoCasilla, int angulo, int desplazamiento, boolean skip) {		
	}

	@Override
	public void onAttemptMade(Game game, char letra, int veces, int points) {
		panel.setText(game.getPanel());
	}

	@Override
	public void onHelpRequested() {}

	@Override
	public void onReset(Game game, Player jugTurno) {
		panel.setText(game.getPanel());
		pista.setText(game.getPista());
		categoria.setText(game.getCategoria());
	}

	@Override
	public void onExit() {}

	@Override
	public void onMenuOpened() {}

	@Override
	public void onRecordsOpened(Records _records) {}

	@Override
	public void onRegister(ModelStatus status) {}

	@Override
	public void notify(ModelStatus status, String info) {
		JOptionPane.showMessageDialog(this, info);
	}

	@Override
	public void onNewRecord() {}

	@Override
	public void onActionEnd() {}

	@Override
	public void onServerAction(Game game, boolean isYourTurn) {
		panel.setText(game.getPanel());
		pista.setText(game.getPista());
		categoria.setText(game.getCategoria());
	}
	
}

