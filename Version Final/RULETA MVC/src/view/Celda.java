package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class Celda extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	private char letter;
	
	private JDialog _dialog;
	
	public Celda(char c, JDialog d) {
		super(new String() + c);
		letter = c;
		_dialog = d;
		initGUI();
	}
	
	private void initGUI() {
		setPreferredSize(new Dimension(50,50));
		setToolTipText(new String() + letter);
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				EastPanel.selected = letter;
				_dialog.setVisible(false);
			}
		});
		this.setVisible(true);
	}
	
}
