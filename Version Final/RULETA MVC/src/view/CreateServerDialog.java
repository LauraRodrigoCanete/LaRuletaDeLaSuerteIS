package view;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class CreateServerDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	
	private NorthPanel _parent;
	private JSpinner _numberPlayers;
	private JTextArea _puerto;
	private final int MAX_LENGTH = 20;

	public CreateServerDialog(Frame frame, NorthPanel parent) {
		super(frame, true);
		this._parent = parent;
		initGUI();
	}

	private void initGUI() {

		setTitle("Crear servidor");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		
		//NUMBER OF PLAYERS
		JPanel p1 = new JPanel();
		mainPanel.add(p1);
		

		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		_numberPlayers = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
		_numberPlayers.setMaximumSize(new Dimension(40 ,20));
		p1.add( new JLabel("Número de jugadores: "));
		p1.add(_numberPlayers);
		mainPanel.add(Box.createRigidArea(new Dimension(20, 5)));
		
		//PUERTO
		JPanel p2 = new JPanel();
		mainPanel.add(p2);
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		

		_puerto = new JTextArea(1,20);
		_puerto.setLineWrap(false);
		_puerto.setSize(new Dimension(150, 20));
		
		_puerto.setDocument(new PlainDocument() {//para poner un maximo de caracteres
			private static final long serialVersionUID = 1L;
			@Override
		    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		        if (str == null || _puerto.getText().length() >= MAX_LENGTH) {
		            return;
		        }
		        super.insertString(offs, str, a);
		    }
		});
		p2.add(new JLabel("Puerto del servidor: "));
		p2.add(_puerto);
		mainPanel.add(Box.createRigidArea(new Dimension(10, 20)));
		
		
		//OK BUTTON
		JButton okButton = new JButton("OK");
		okButton.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(okButton);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer numPlayers = null;
				Integer puerto = null;
				
				try {
					puerto = Integer.parseInt(_puerto.getText());
					numPlayers = (Integer) _numberPlayers.getValue();
					
					if(puerto == null || numPlayers == null) {
						JOptionPane.showMessageDialog(_parent, "Rellena los campos por favor.");
					}
					else if(puerto <= 0) {
						JOptionPane.showMessageDialog(_parent, "Ha habido un error con el puerto introducido.");
					}
					else {
						dispose();
						_parent.disableNetBtn();
						_parent.createServer(numPlayers, puerto);
						
					}
				}
				catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(_parent, "Rellena los campos por favor.");
				}
				
			}
		});
		mainPanel.add(Box.createRigidArea(new Dimension(10, 10)));

		pack();
		setResizable(false);
		setVisible(false);
	}
	
	
	
	public void open() {
		setLocation(getParent().getLocation().x, getParent().getLocation().y);

		setVisible(true);//aqui se para para q el usuario interactue, se hace visible aqui, NO EN LA CONSTRUCTORA para que esté refrescada
	}

}


