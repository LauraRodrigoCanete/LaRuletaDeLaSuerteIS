package view;


import java.awt.Dimension;
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
import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class JoinServerDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	
	private NorthPanel _parent;
	private JTextArea _nombre;
	private JTextArea _puerto;
	private JTextArea _IP;
	private final int MAX_LENGTH = 20;

	public JoinServerDialog(Frame frame, NorthPanel parent) {
		super(frame, true);
		this._parent = parent;
		initGUI();
	}

	private void initGUI() {

		setTitle("Unirse a un servidor");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		setContentPane(mainPanel);
		
		mainPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		
		//NOMBRE
		JPanel p1 = new JPanel();
		mainPanel.add(p1);
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		_nombre = new JTextArea(1,20);
		_nombre.setMaximumSize(new Dimension(250 ,20));
		_nombre.setLineWrap(true);
		_nombre.setDocument(new PlainDocument() {//para poner un maximo de caracteres
			private static final long serialVersionUID = 1L;

			@Override
		    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		        if (str == null || _nombre.getText().length() >= MAX_LENGTH) {
		            return;
		        }
		 
		        super.insertString(offs, str, a);
		    }
		});
		p1.add(new JLabel("Nombre del cliente: "));
		p1.add(_nombre);
		
		mainPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		
		//PUERTO
		JPanel p2 = new JPanel();
		mainPanel.add(p2);
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		_puerto = new JTextArea(1,20);
		_puerto.setMaximumSize(new Dimension(250 ,20));
		_puerto.setLineWrap(true);
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

		mainPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		
		//IP
		JPanel p3 = new JPanel();
		mainPanel.add(p3);
		p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		_IP = new JTextArea(1,20);
		_IP.setMaximumSize(new Dimension(290 ,20));
		_IP.setLineWrap(true);
		_IP.setDocument(new PlainDocument() {//para poner un maximo de caracteres
			private static final long serialVersionUID = 1L;

			@Override
		    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		        if (str == null || _IP.getText().length() >= MAX_LENGTH) {
		            return;
		        }
		 
		        super.insertString(offs, str, a);
		    }
		});
		p3.add(new JLabel("Dirección IP: "));
		p3.add(_IP);
		
		mainPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		
		//OK BUTTON
		JButton okButton = new JButton("OK");
		mainPanel.add(okButton);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = _IP.getText();
				String nombre = _nombre.getText();
				Integer puerto = null;
				try {
					puerto = Integer.parseInt(_puerto.getText());
					
					if (ip == null || nombre == null || puerto == null) {
						JOptionPane.showMessageDialog(_parent, "Rellena los campos por favor.");
					}
					else if(puerto <= 0) {
						JOptionPane.showMessageDialog(_parent, "Ha habido un error con el puerto introducido.");
					}
					else {
						dispose();
						_parent.disableNetBtn();
						_parent.joinServer(ip, puerto, nombre);
					}
				}
				catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(_parent, "Rellena los campos por favor.");
				}
			}
			
		});
		

		setPreferredSize(new Dimension(400, 220));
		pack();
		setResizable(true);//importante
		setVisible(false);
	}
	
	public void open() {
		setLocation(getParent().getLocation().x, getParent().getLocation().y);

		setVisible(true);
	}

	public int getPuerto() {
		int value;
		try {
			value = Integer.parseInt(_puerto.getText());
		}
		catch(NumberFormatException e) {
			return -1;
		}
		return value;
	}

	public String getNombre() {
		return _nombre.getText();
	}
	
	public String getIP() {
		return _IP.getText();
	}
	
}


