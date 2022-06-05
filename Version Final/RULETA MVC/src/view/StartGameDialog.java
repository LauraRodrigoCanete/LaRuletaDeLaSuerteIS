package view;


import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class StartGameDialog extends JDialog{
	
	private static final long serialVersionUID = 1L;
	
	private int _status;
	private JSpinner _numberPlayers;
	private JRadioButton _bote;
	private List<JTextArea> _names;
	private final int MAX_LENGTH = 20;

	public StartGameDialog(Frame frame) {
		super(frame, true);
		_names = new ArrayList<JTextArea>();
		initGUI();
	}

	private void initGUI() {

		_status = 0;//para q si cierro el cuadro de dialogo sin pulsar a los botones salga tambien el status de 0 por defecto

		setTitle("Empezar a jugar");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel numberPanel = new JPanel();
		numberPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(numberPanel);


		_numberPlayers = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
		numberPanel.add( new JLabel("Numero de jugadores: "));
		numberPanel.add(_numberPlayers);
		
		_bote = new JRadioButton("Seleccione para que exista la casilla bote");
		_bote.setBounds(100, 50, 100, 30);
		numberPanel.add(_bote);

		JPanel buttonsPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				StartGameDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);
		
		JButton okButton = new JButton("OK");
		buttonsPanel.add(okButton);
		mainPanel.add(buttonsPanel);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JPanel namesPanel = new JPanel();
				//namesPanel.setLayout(new BoxLayout(namesPanel, BoxLayout.Y_AXIS));
				mainPanel.add(namesPanel);
				
				
				if (_numberPlayers.getValue() != null) {
					okButton.setEnabled(false);
					
					JPanel names = new JPanel();
					names.setLayout(new BoxLayout(names, BoxLayout.Y_AXIS));
					for(int i = 1; i < (int)_numberPlayers.getValue() +1; i++) {
						JPanel p = new JPanel();
						JTextArea t = new JTextArea(1,20);
						t.setLineWrap(true);
						_names.add(t);
						t.setDocument(new PlainDocument() {//para poner un maximo de caracteres
							private static final long serialVersionUID = 1L;

							@Override
						    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
						        if (str == null || t.getText().length() >= MAX_LENGTH) {
						            return;
						        }
						 
						        super.insertString(offs, str, a);
						    }
						});
						p.add(new JLabel("Nombre jugador " + i +":"));
						p.add(_names.get(i-1));
						names.add(p);
					}
					namesPanel.add(names);
					
					JButton finishedButton = new JButton("Finished");
					finishedButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							_status = 1;
							StartGameDialog.this.setVisible(false);
						}
					});
					namesPanel.add(finishedButton);
					StartGameDialog.this.pack();
					StartGameDialog.this.validate();
					StartGameDialog.this.repaint();
					
				}
			}
		});
		

		//setPreferredSize(new Dimension(500, 200)); no poner
		pack();
		setResizable(true);//importante
		setVisible(false);
	}
	
	public int open() {
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);

		setVisible(true);//aqui se para para q el usuario interactue, se hace visible aqui, NO EN LA CONSTRUCTORA para que esté refrescada
		return _status;
	}

	public List<String> getPlayerNames() {
		List<String> lista = new ArrayList<String>();
		for(JTextArea t: _names) {
			lista.add(t.getText());
		}
		return lista;
	}

	public int getNumPlayers() {
		return (_numberPlayers.getValue() == null? 0 : (int) _numberPlayers.getValue());
	}
	
	public boolean hayBote() {//si lo selecciona quiere que SI haya
		return _bote.isSelected(); 
	}
}


