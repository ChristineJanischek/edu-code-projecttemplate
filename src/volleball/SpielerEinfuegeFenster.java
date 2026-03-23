package volleyball;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class SpielerEinfuegeFenster extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNeuerSpieler;
	private JTextField tfStelle;
	private JScrollPane spAusgabe;
	private JTextArea taAusgabe;
	
	//Assoziation zur (Fach)klasse
	VolleyballspielerTeamManager myManager = new VolleyballspielerTeamManager();
	private int auswahl;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpielerEinfuegeFenster frame = new SpielerEinfuegeFenster();
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
	public SpielerEinfuegeFenster() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 411);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbTitel = new JLabel("Spieler einf\u00FCgen");
		lbTitel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbTitel.setIcon(new ImageIcon(SpielerEinfuegeFenster.class.getResource("/image/ic_launcher.png")));
		lbTitel.setBounds(10, 0, 262, 81);
		contentPane.add(lbTitel);
		
		JLabel lbNeuerSpieler = new JLabel("Neuer Spieler:");
		lbNeuerSpieler.setBounds(24, 116, 96, 14);
		contentPane.add(lbNeuerSpieler);
		
		JLabel lbStelle = new JLabel("an der Stelle mit dem Index:");
		lbStelle.setBounds(24, 153, 201, 14);
		contentPane.add(lbStelle);
		
		tfNeuerSpieler = new JTextField();
		tfNeuerSpieler.setBounds(118, 113, 169, 20);
		contentPane.add(tfNeuerSpieler);
		tfNeuerSpieler.setColumns(10);
		
		tfStelle = new JTextField();
		tfStelle.setBounds(248, 150, 39, 20);
		contentPane.add(tfStelle);
		tfStelle.setColumns(10);
		
		JButton btEinfuegen = new JButton(">>");
		btEinfuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Eingabe
				String neuerspieler = leseNeuerSpieler();
				int stelle = leseStelle();
				
				//Verarbeitung
				String[] spieler = myManager.holeSpielerliste(auswahl);
				String[] aktualisierteListe = myManager.einfuegen(spieler,neuerspieler,stelle);
				
				//Ausgabe
				schreibeAusgabe(aktualisierteListe);
				
			}
		});
		btEinfuegen.setBounds(24, 198, 89, 23);
		contentPane.add(btEinfuegen);
		
		JLabel lbBanner = new JLabel("");
		lbBanner.setIcon(new ImageIcon(SpielerEinfuegeFenster.class.getResource("/image/logo_final.png")));
		lbBanner.setBounds(249, 328, 175, 33);
		contentPane.add(lbBanner);
		
		spAusgabe = new JScrollPane();
		spAusgabe.setBounds(171, 197, 96, 120);
		contentPane.add(spAusgabe);
		
		taAusgabe = new JTextArea();
		spAusgabe.setViewportView(taAusgabe);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public SpielerEinfuegeFenster(VolleyballspielerTeamManager o, int pAuswahl) {
		this.auswahl = pAuswahl;
		this.myManager = o;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 411);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbTitel = new JLabel("Spieler einf\u00FCgen");
		lbTitel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbTitel.setIcon(new ImageIcon(SpielerEinfuegeFenster.class.getResource("/image/ic_launcher.png")));
		lbTitel.setBounds(10, 0, 262, 81);
		contentPane.add(lbTitel);
		
		JLabel lbNeuerSpieler = new JLabel("Neuer Spieler:");
		lbNeuerSpieler.setBounds(24, 116, 96, 14);
		contentPane.add(lbNeuerSpieler);
		
		JLabel lbStelle = new JLabel("an der Stelle mit dem Index:");
		lbStelle.setBounds(24, 153, 201, 14);
		contentPane.add(lbStelle);
		
		tfNeuerSpieler = new JTextField();
		tfNeuerSpieler.setBounds(118, 113, 169, 20);
		contentPane.add(tfNeuerSpieler);
		tfNeuerSpieler.setColumns(10);
		
		tfStelle = new JTextField();
		tfStelle.setBounds(248, 150, 39, 20);
		contentPane.add(tfStelle);
		tfStelle.setColumns(10);
		
		JButton btEinfuegen = new JButton(">>");
		btEinfuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Eingabe
				String neuerspieler = leseNeuerSpieler();
				int stelle = leseStelle();
				
				//Verarbeitung
				String[] spieler = myManager.holeSpielerliste(auswahl);
				String[] aktualisierteListe = myManager.einfuegen(spieler,neuerspieler,stelle);
				
				//Ausgabe
				schreibeAusgabe(aktualisierteListe);
				
			}
		});
		btEinfuegen.setBounds(24, 198, 89, 23);
		contentPane.add(btEinfuegen);
		
		JLabel lbBanner = new JLabel("");
		lbBanner.setIcon(new ImageIcon(SpielerEinfuegeFenster.class.getResource("/image/logo_final.png")));
		lbBanner.setBounds(249, 328, 175, 33);
		contentPane.add(lbBanner);
		
		spAusgabe = new JScrollPane();
		spAusgabe.setBounds(171, 197, 96, 120);
		contentPane.add(spAusgabe);
		
		taAusgabe = new JTextArea();
		spAusgabe.setViewportView(taAusgabe);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	//Hilfsmethoden
	
	private String leseNeuerSpieler() {
		String neuerSpieler = tfNeuerSpieler.getText();
		return neuerSpieler;
	}
	
	private int leseStelle() {
		int stelle = Integer.valueOf(tfStelle.getText());
		return stelle;
	}
	
	public void schreibeAusgabe(String[] pNeueListe) {		
		String ergebnis;
		switch (this.auswahl) {
			case 1: 
				myManager.setSpieler(pNeueListe);
				ergebnis = myManager.zeigeStartaufstellung();
				break;
			case 2:
				myManager.setErsatz(pNeueListe);
				ergebnis = myManager.zeigeErsatzspieler();
				break;
			default:
				ergebnis = "";
				break;
		}
		taAusgabe.setText(ergebnis);
	}
}
