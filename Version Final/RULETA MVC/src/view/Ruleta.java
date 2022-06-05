package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import control.GUIController;
import model.Game;
import model.ModelStatus;
import model.Player;
import model.RDLSObserver;
import model.record.Records;

public class Ruleta extends JPanel implements RDLSObserver {

	private static final long serialVersionUID = 1L;
	private static final Color _BG_COLOR = Color.WHITE;
	
	private class Spinner implements Runnable {
		private int _angulo;
		private int _desplazamiento;
		
		public Spinner(int angulo, int desplazamiento) {
			_angulo = angulo;
			_desplazamiento = desplazamiento;
		}
		
		@Override
		public void run() {
			int prev = _angulo;
			//velocidad inicial para que el giro se haga en TIEMPO_GIRO ms;
			double velocidadInicial = Math.sqrt(2 * _desplazamiento * ACELERACION);
			double velocidad = velocidadInicial;
			double anguloGirado = 0;
			long ini = System.currentTimeMillis();
			long t = System.currentTimeMillis() - ini;
			while(velocidad >= 0) {
				anguloGirado = velocidadInicial * t - 0.5 * ACELERACION * t * t;
				velocidad = velocidadInicial - ACELERACION * t;
				_angulo = (int) Math.max(prev + (int) Math.ceil(anguloGirado), prev); //Por si comienza a retroceder
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						Ruleta.this.setAngleAndRepaint(_angulo);
					}
				});
				try {
					Thread.sleep(20L);
				} catch (InterruptedException e) {
				}
				t = System.currentTimeMillis() - ini;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					Ruleta.this.setAngleAndRepaint((prev + _desplazamiento) % 360);
					_mainWindow.setThrowEnable(_canThrow);
				}
			});
		}
	}

	private static final int MARGEN = 5;
	private static final int DISTANCE_FROM_BORDER = 5;
	private static final double ACELERACION = 0.00015; //Esta aceleración funciona bien 
	
	private BufferedImage _ruleta;
	private MainWindow _mainWindow;
	private int _angle;
	private List<String> _casillas;
	private boolean _canThrow;
	

	public Ruleta(GUIController _ctrl, MainWindow mainWindow) {
		_canThrow = false;
		_angle = 0;
		_mainWindow = mainWindow;
		_ctrl.addObserver(this);
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		if(_ruleta == null) {
			g.setColor(Color.red);
			g.drawString("No hay ruleta aún!", getWidth() / 2 - 50, getHeight() / 2);
		}
		else {
			int radio = Math.min(getWidth(), getHeight()) / 2 - MARGEN;
			int centerY = getHeight() / 2;
			int centerX = getWidth() / 2;
			
			//Flecha
			g.setColor(Color.BLUE);
			g.fillArc(centerX + radio - 15, centerY - 18, 40, 40, 30, -60);
			
			//g.setFont(new Font("Helvetica", Font.BOLD, 12));//grados. para debugear
			//g.drawString(String.valueOf(_angle), 10, 10); 
			
			g.rotate(Math.toRadians(-_angle), centerX, centerY);
			g.drawImage(_ruleta, 0, 0, null);
		}
		g.dispose();
	}
	
	private void createRuleta(List<String> casillas) {
		_ruleta = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int nCasillas = casillas.size();
		double delta = 360.0 / nCasillas;
		int radio = Math.min(getWidth(), getHeight()) / 2 - MARGEN;
		int centerY = getHeight() / 2;
		int centerX = getWidth() / 2;
		
		Graphics2D g2d = _ruleta.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setComposite(AlphaComposite.Clear);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setFont(new Font("Helvetica", Font.BOLD, 12));
		g2d.setComposite(AlphaComposite.Src);
		for(int i = 0; i < nCasillas; ++i) {
			//Dibujamos el borde
			g2d.setColor(Color.WHITE);
		    g2d.drawLine(centerX, centerY, centerX + radio, centerY);
		    
		    //Dibujamos la sección
		    g2d.setColor(colorCasilla(casillas.get(i)));
		    g2d.fillArc(centerX - radio, centerY - radio, 2*radio, 2*radio, 0, (int) -Math.ceil(delta));
			
		    //Dibujamos el nombre de la casilla
		    g2d.rotate(Math.toRadians(delta / 2), centerX, centerY);
		    g2d.setColor(Color.WHITE);
		    FontMetrics fontMetrics = g2d.getFontMetrics();
		    int stringWidth = fontMetrics.stringWidth(casillas.get(i));
		    g2d.drawString(casillas.get(i), (centerX + radio - stringWidth - DISTANCE_FROM_BORDER), 
		               (int)(centerY + (double)fontMetrics.getHeight() / 2 - fontMetrics.getMaxDescent()));
		    g2d.rotate(Math.toRadians(delta / 2), centerX, centerY);
		}
		
		g2d.dispose();
	}
	
	void sizeChanged() {
		if(_ruleta != null && _casillas != null) {
			createRuleta(_casillas);
			repaint();
		}
	}
	
	private Color colorCasilla(String casilla) {
		int puntos;
		Color color;
		try {
			puntos = Integer.parseInt(casilla);
			int intensity = Math.min((int) ((puntos / 200.0) * 191), 191);
			if(puntos == 0)
				color = new Color(150, 150, 150); //gris
			else
				color = new Color(255,  191 - intensity, 0); //entre amarillo y rojo
		} catch(NumberFormatException e) {
			if("CASILLA BOTE".equals(casilla))
				color = new Color(51, 122, 255); //azul
			else if("CASILLA 1/2".equals(casilla))
				color = new Color(0, 211, 255); //azul
			else if("PIERDE EL TURNO".equals(casilla))
				color = new Color(201, 0, 207); //majenta
			else if("QUIEBRA".equals(casilla))
				color = new Color(34, 34, 34); //gris oscuro casi negro
			else if("CASILLA x2".equals(casilla))
				color = new Color(0, 203, 7); //verde
			else
				color = new Color(250, 155, 255); //rosa. color por defecto
		}
		
		return color;
	}	
	
	private void setAngleAndRepaint(int angle) {
		_angle = angle;
		this.repaint();
	}

	@Override
	public void onGameStart(Game game, Player jugTurno) {
		_canThrow = game.isTurnInServer();
		_casillas = game.getNombresCasillas();
		_angle = game.getRouletteAngle();
		createRuleta(_casillas);
		repaint();
	}

	@Override
	public void onGameEnd(Game game, Player ganador) {
		_canThrow = false;
	}

	@Override
	public void onPlayStart(Game game) {
		setAngleAndRepaint(game.getRouletteAngle());
	}

	@Override
	public void onTurnChanged(Game game) {
		_canThrow = game.isTurnInServer();
	}

	@Override
	public void onRouletteThrown(String infoCasilla, int angulo, int desplazamiento, boolean skip) {
		_mainWindow.setThrowEnable(false); //bloqueamos el boton de la ruleta hasta que el giro haya parado
		_angle = angulo;
		Thread t = new Thread(new Spinner(_angle, desplazamiento));
		t.start();
	}

	@Override
	public void onAttemptMade(Game game, char letra, int veces, int points) {
		setAngleAndRepaint(game.getRouletteAngle());
	}

	@Override
	public void onHelpRequested() {}

	@Override
	public void onReset(Game game, Player jugTurno) {
		_casillas = game.getNombresCasillas();
		_angle = game.getRouletteAngle();
		createRuleta(_casillas);
		repaint();
	}

	@Override
	public void onExit() {
		_canThrow = false;
	}

	@Override
	public void onMenuOpened() {}

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

	@Override
	public void onServerAction(Game game, boolean isYourTurn) {
		_canThrow = isYourTurn;
		setAngleAndRepaint(game.getRouletteAngle());
	}
	
}
