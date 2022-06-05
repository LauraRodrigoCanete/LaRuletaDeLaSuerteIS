package view;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class ChooseVowelDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private static final String vowels = "AEIOU";
	
	ChooseVowelDialog(Frame parent){
		super(parent,"Comprar vocal", true);
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new GridLayout(1,5, 10, 10));
		
		for(int i = 0; i < vowels.length(); ++i)
			mainPanel.add(new Celda(vowels.charAt(i), this));
		
		this.add(mainPanel);
		this.pack();
	}
	
	public void open() {
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
