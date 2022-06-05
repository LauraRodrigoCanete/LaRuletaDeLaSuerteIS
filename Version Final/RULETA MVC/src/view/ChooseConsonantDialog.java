package view;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class ChooseConsonantDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private static final String consonants = "BCDFGHJKLMNÑPQRSTVWXYZ";
	
	ChooseConsonantDialog(Frame parent){
		super(parent,"Elegir consonante", true);
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new GridLayout(2, 11, 10, 10));
		
		for(int i = 0; i < consonants.length(); ++i)
			mainPanel.add(new Celda(consonants.charAt(i), this));
		
		this.add(mainPanel);
		this.pack();
	}
	
	public void open() {
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
