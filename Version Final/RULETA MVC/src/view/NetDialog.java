package view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class NetDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private Frame _frame;
	private NorthPanel _parent;

	public NetDialog(Frame frame, NorthPanel parent) {
		super(frame, true);
		this._frame = frame;
		this._parent = parent;
		initGUI();
	}

	private void initGUI() {
		setTitle("Jugar en red");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setAlignmentY(CENTER_ALIGNMENT);
		setContentPane(mainPanel);
		
		//MAIN PANEL
		mainPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		
		//CREATE SERVER
		JButton create = new JButton("Crear servidor");
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				dispose(); //Cerramos el anterior JDialog
				CreateServerDialog csDialog = new CreateServerDialog(_frame, _parent);
				csDialog.open();
			}
		});
		mainPanel.add(create);
		mainPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		
		//JOIN SERVER
		JButton join = new JButton("Unirse a un servidor");
		join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); //Cerramos el anterior JDialog
				JoinServerDialog jsDialog = new JoinServerDialog(_frame, _parent);
				jsDialog.open();
			}
		});
		mainPanel.add(join);
		mainPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		
		
		setPreferredSize(new Dimension(350, 100));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	public void open() {
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);

		setVisible(true);
	}
}



