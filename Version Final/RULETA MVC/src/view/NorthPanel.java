package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import control.GUIController;
import model.Game;
import model.ModelStatus;
import model.Player;
import model.RDLSObserver;
import model.record.Record;
import model.record.Records;

public class NorthPanel extends JPanel implements RDLSObserver{
	
	private static final long serialVersionUID = 1L;
	
	private GUIController _ctrl;
	private JToolBar barra;
	private JButton cargarPartida;
	private JButton guardarPartida;
	private JButton consultarRecords;
	private JButton jugar;
	private JButton net;
	private JButton salirAmenu;
	
	private Records _records;
	
	private MainWindow _mainWindow;
	
	public NorthPanel(GUIController _ctrl, MainWindow mainWindow) {
		this._ctrl = _ctrl;
		initGUI();
		_ctrl.addObserver(this);
		_mainWindow = mainWindow;
	}
	
	private void initGUI() {
		cargarPartida = new JButton();
		guardarPartida = new JButton();
		consultarRecords = new JButton();
		jugar = new JButton();
		net = new JButton();
		salirAmenu = new JButton();
		barra = new JToolBar();
		this.setLayout(new BorderLayout());
		this.add(barra, BorderLayout.NORTH);		
		
		barra.addSeparator();
		
		//boton cargar partida
		cargarPartida.setToolTipText("Selecciona un archivo para cargar una partida");
		cargarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fcC = new JFileChooser();
				fcC.setCurrentDirectory(new File("examples/"));
				fcC.setMultiSelectionEnabled(false);
				fcC.setFileFilter(new FileNameExtensionFilter("Archivos json", "json"));
				int ret = fcC.showOpenDialog(thisPanel());
				if (ret == JFileChooser.APPROVE_OPTION) {
					try {
						File in = fcC.getSelectedFile();
						_ctrl.cargarPartida(in);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(thisPanel(), "Ha habido un error eligiendo el archivo.");
					}
					JOptionPane.showMessageDialog(thisPanel(), "Se ha seleccionado abrir el archivo: " + fcC.getSelectedFile());
				} else if (ret == JFileChooser.CANCEL_OPTION){
					JOptionPane.showMessageDialog(thisPanel(), "Se ha pulsado cancelar.");
				} else {//ha habido un error
					JOptionPane.showMessageDialog(thisPanel(), "Ha habido un error eligiendo el archivo.");
				}
			}
		});
		cargarPartida.setIcon(new ImageIcon((new ImageIcon("resources/icons/cargarPartida.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		barra.add(cargarPartida);
		barra.addSeparator();

		//boton guardar partida
		guardarPartida.setToolTipText("Selecciona un archivo para guardar una partida en él");
		guardarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fcG = new JFileChooser();
				fcG.setCurrentDirectory(new File("examples/"));
				fcG.setMultiSelectionEnabled(false);
				fcG.setFileFilter(new FileNameExtensionFilter("Archivos json", "json"));
				int ret = fcG.showOpenDialog(thisPanel());
				if (ret == JFileChooser.APPROVE_OPTION) {
					File out = fcG.getSelectedFile();
					_ctrl.guardarPartida(out);
					JOptionPane.showMessageDialog(thisPanel(), "Se ha seleccionado abrir el archivo: " + fcG.getSelectedFile());
				} else if (ret == JFileChooser.CANCEL_OPTION){
					JOptionPane.showMessageDialog(thisPanel(), "Se ha pulsado cancelar.");
				} else {//ha habido un error
					JOptionPane.showMessageDialog(thisPanel(), "Ha habido un error eligiendo el archivo.");
				}
			}
		});
		guardarPartida.setIcon(new ImageIcon((new ImageIcon("resources/icons/guardarPartida.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		barra.add(guardarPartida);
		barra.addSeparator();

		//boton consultar records
		consultarRecords.setToolTipText("Selecciona un archivo para consultar los records");
		consultarRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					_ctrl.verRecords();
					String s = "Records: ";
					for(Record r : _records.getRecords()) {
						s += "\n" + r.getPlayerName() + ": " + r.getScore();
					}
					JOptionPane.showMessageDialog(thisPanel(), s);
				}
			}
		);		
		
		consultarRecords.setIcon(new ImageIcon((new ImageIcon("resources/icons/consultarRecords.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		barra.add(consultarRecords);
		barra.addSeparator();

		//boton jugar
		jugar.setToolTipText("Empezar a jugar");
		jugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				StartGameDialog dialog = new StartGameDialog(getPreviousFrame());

				//sin esta linea no es visible, se hace visible en el open
				int status = dialog.open();
				if (status != 0)
					_ctrl.createNewGame(dialog.getNumPlayers(), dialog.hayBote(), dialog.getPlayerNames());
			}
		});
		jugar.setIcon(new ImageIcon((new ImageIcon("resources/icons/jugar.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		barra.add(jugar);
		barra.addSeparator();
		
		//boton net
		net.setToolTipText("Jugar en red");
		net.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				NetDialog dialogNet = new NetDialog(getPreviousFrame(), thisPanel());
				dialogNet.open();
			}
		});
		net.setIcon(new ImageIcon((new ImageIcon("resources/icons/net.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		barra.add(net);
		
		barra.add(Box.createGlue());
		
		
		salirAmenu.setToolTipText("Volver al menú de inicio");
		salirAmenu.setIcon(new ImageIcon((new ImageIcon("resources/icons/exit.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		salirAmenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				_ctrl.exit();
			}
			
		});
		barra.add(salirAmenu);
		barra.addSeparator();
	}
	
	private NorthPanel thisPanel() {
		return this;
	}
	
	private JFrame getPreviousFrame() {
		return (JFrame) SwingUtilities.getWindowAncestor(this);
	}
	

	@Override
	public void onGameStart(Game game, Player jugTurno) {
		cargarPartida.setEnabled(false);
		guardarPartida.setEnabled(true);
		consultarRecords.setEnabled(false);
		jugar.setEnabled(false);
		net.setEnabled(false);
	}

	@Override
	public void onGameEnd(Game game, Player ganador) {}

	@Override
	public void onPlayStart(Game game) {}

	@Override
	public void onTurnChanged(Game game) {}

	@Override
	public void onRouletteThrown(String infoCasilla, int angulo, int desplazamiento, boolean skip) {}

	@Override
	public void onAttemptMade(Game game, char letra, int veces, int points) {}

	@Override
	public void onHelpRequested() {}

	@Override
	public void onReset(Game game, Player jugTurno) {}

	@Override
	public void onExit() {
		_mainWindow.setResetEnabled(true);
	}

	@Override
	public void onMenuOpened() {
		cargarPartida.setEnabled(true);
		guardarPartida.setEnabled(false);
		consultarRecords.setEnabled(true);
		jugar.setEnabled(true);
		net.setEnabled(true);
	}

	@Override
	public void onRecordsOpened(Records _records) {
		this._records = _records;
	}

	@Override
	public void onRegister(ModelStatus status) {}

	@Override
	public void notify(ModelStatus status, String info) {}

	@Override
	public void onNewRecord() {}

	@Override
	public void onActionEnd() {}

	@Override
	public void onServerAction(Game game, boolean isYourTurn) {}
	
	//RED
	
	public void joinServer(String ip, int port, String name) {
		try {
			_ctrl.joinServer(ip, port, name);
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(this, "Problema al unirse al servidor.");
			this.net.setEnabled(true);
		}
	}
	
	public void createServer(int numJugadores, int port) {
		try {
			_ctrl.createServer(numJugadores, port);
			ShutServerDialog dialog = new ShutServerDialog(this.getPreviousFrame());
			dialog.open();
			
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(this, "Problema al crear el servidor.");
			this.net.setEnabled(true);
		}
	}

	public void disableNetBtn() {
		this.net.setEnabled(false);
	}
	
	
	
	public class ShutServerDialog extends JDialog {

		private static final long serialVersionUID = 1L;

		public ShutServerDialog(Frame frame) {
			super(frame, true);
			initGUI();
		}
		
		public void initGUI() {
			this.setTitle("Server running...");
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			setContentPane(mainPanel);
			
			JButton shutButton = new JButton("Shut server");
			shutButton.setAlignmentX(CENTER_ALIGNMENT);
			mainPanel.add(shutButton);
			shutButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					_ctrl.shutServer();
					
					//Botonoes de nuevo se habilitan
					cargarPartida.setEnabled(true);
					guardarPartida.setEnabled(true);
					consultarRecords.setEnabled(true);
					jugar.setEnabled(true);
					net.setEnabled(true);
					
					dispose();
				}
			});
			mainPanel.add(shutButton);
			pack();
			this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			setResizable(false);
			setVisible(false);
		}
		
		public void open() {
			setLocation(getParent().getLocation().x, getParent().getLocation().y);
			this.setVisible(true);
		}
	}

}