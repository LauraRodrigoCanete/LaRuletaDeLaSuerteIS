package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import control.GUIController;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private EastPanel eastP;
	private Ruleta _ruleta;
	
	private GUIController _ctrl;

	public MainWindow(GUIController ctrl) {
		super("La ruleta de la suerte");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		//toolbar
		mainPanel.add(new NorthPanel(_ctrl, this), BorderLayout.PAGE_START);
		//statusbar
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		
		//panel, puntuaciones y ruleta
		JPanel viewsPanel = new JPanel(new GridLayout(2, 1));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		//panel
		JPanel panel_rdls_panel = new JPanel();
		panel_rdls_panel.setLayout(new BoxLayout(panel_rdls_panel, BoxLayout.Y_AXIS));
		viewsPanel.add(panel_rdls_panel);
		
		//puntuaciones y ruleta
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.X_AXIS));
		viewsPanel.add(tablesPanel);
		
		//botones
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		mainPanel.add(eastPanel, BorderLayout.EAST);
		
		JPanel centre = createViewPanel(new CenterPanel(_ctrl), "Juego");
		centre.setPreferredSize(new Dimension(800, 400));
		panel_rdls_panel.add(centre);
		
		eastP = new EastPanel(_ctrl);
		JPanel east = createViewPanel(eastP, "Botones");
		east.setPreferredSize(new Dimension(200, 800));
		eastPanel.add(east);
		
		JPanel pointsView = createViewPanel(new JTable(new PunctuationsTable(_ctrl)), "Puntuaciones");
		pointsView.setPreferredSize(new Dimension(400, 400));
		tablesPanel.add(pointsView);
		
		_ruleta = new Ruleta(_ctrl, this);
		JPanel ruleta = createViewPanel(_ruleta, "Ruleta");
		ruleta.setPreferredSize(new Dimension(400, 400));
		tablesPanel.add(ruleta);
		
		this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                _ruleta.sizeChanged();
            }
		});
		this.addWindowListener(new WindowAdapter() {
			  @Override
			  public void windowClosing(WindowEvent e) {
				  _ctrl.exitApp();
				  System.exit(0);
			  }
			});
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	void setResetEnabled(boolean e) {
		eastP.setResetEnable(e);
	}
	
	void setThrowEnable(boolean e) {
		eastP.setThrowEnable(e);
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		p.setBorder(new TitledBorder(new LineBorder(Color.black, 2),title));
		p.add(new JScrollPane(c));
		return p;
	}

}
