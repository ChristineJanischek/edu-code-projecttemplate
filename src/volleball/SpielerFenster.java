package volleyball;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

public class SpielerFenster extends JFrame {
	private JLabel lbTitel;
	private JScrollPane scrollPane ;
	private JTextArea taAusgabe;
	private JLabel lbBanner;
	private VolleyballspielerTeamManager myManager;
	private int auswahl;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpielerFenster frame = new SpielerFenster();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	//Default Konstruktor
	public SpielerFenster() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 242, 293);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbTitel = new JLabel("Spieler anzeigen");
		lbTitel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbTitel.setBounds(10, 11, 169, 25);
		contentPane.add(lbTitel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 68, 169, 93);
		contentPane.add(scrollPane);
		
		taAusgabe = new JTextArea();
		taAusgabe.setEditable(false);
		scrollPane.setViewportView(taAusgabe);
						
		lbBanner = new JLabel("");
		lbBanner.setIcon(new ImageIcon(SpielerFenster.class.getResource("/image/logo_final.png")));
		lbBanner.setBounds(29, 213, 175, 30);
		contentPane.add(lbBanner);		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	//Konstruktor mit Objekt der Fachklasse
	public SpielerFenster(VolleyballspielerTeamManager o, int pAuswahl) {
		
		this.myManager = o;
		this.auswahl = pAuswahl;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 242, 293);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbTitel = new JLabel("Spieler anzeigen");
		lbTitel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbTitel.setBounds(10, 11, 169, 25);
		contentPane.add(lbTitel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 68, 169, 93);
		contentPane.add(scrollPane);
		
		taAusgabe = new JTextArea();
		taAusgabe.setEditable(false);
		
		//Ausgabe der Spieler im Spielerfenster
		schreibeSpieler();
		
		scrollPane.setViewportView(taAusgabe);
		
		lbBanner = new JLabel("");
		lbBanner.setIcon(new ImageIcon(SpielerFenster.class.getResource("/image/logo_final.png")));
		lbBanner.setBounds(29, 213, 175, 30);
		contentPane.add(lbBanner);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	//Hilfsmethode
	
	private void schreibeSpieler() {
		String ergebnis;
		switch(this.auswahl) {
			case 1:
				ergebnis = this.myManager.zeigeStartaufstellung();
				break;
			case 2: 	
				ergebnis = this.myManager.zeigeErsatzspieler();
				break;
			case 3:
				ergebnis = this.myManager.zeigeKader();
				break;
			default:
				ergebnis = "";
		}
		taAusgabe.setText(ergebnis);
			
	}
	
	
	
	
}
