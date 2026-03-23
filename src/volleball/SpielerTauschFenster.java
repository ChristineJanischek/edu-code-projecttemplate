package volleyball;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SpielerTauschFenster extends JFrame {
	private JComboBox cbPositionenVon;
	private JComboBox cbPositionenNach;
	private JTextArea taNachher;
	private JScrollPane spNachher;
	private JTextArea taVorher;
	private JScrollPane spVorher;
	private JPanel contentPane;
	private int auswahl;
	
	private VolleyballspielerTeamManager myManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpielerTauschFenster frame = new SpielerTauschFenster();
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
	
	public SpielerTauschFenster() {

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbTitle = new JLabel("Spieler Tauschen");
		lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbTitle.setIcon(new ImageIcon(SpielerTauschFenster.class.getResource("/image/ic_launcher.png")));
		lbTitle.setBounds(24, 11, 292, 78);
		contentPane.add(lbTitle);
		
		JLabel lbVon = new JLabel("Von Position:");
		lbVon.setBounds(33, 124, 89, 14);
		contentPane.add(lbVon);
		
		cbPositionenVon = new JComboBox();
		cbPositionenVon.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6"}));
		cbPositionenVon.setBounds(149, 120, 55, 22);
		contentPane.add(cbPositionenVon);
		
		JLabel lbNach = new JLabel("Nach Position:");
		lbNach.setBounds(237, 124, 89, 14);
		contentPane.add(lbNach);
		
		cbPositionenNach = new JComboBox();
		cbPositionenNach.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6"}));
		cbPositionenNach.setBounds(351, 120, 55, 22);
		contentPane.add(cbPositionenNach);
		
		JLabel lbVorher = new JLabel("Vorher:");
		lbVorher.setBounds(34, 175, 73, 14);
		contentPane.add(lbVorher);
		
		JLabel lbNachher = new JLabel("Nachher:");
		lbNachher.setBounds(237, 175, 79, 14);
		contentPane.add(lbNachher);
		
		spVorher = new JScrollPane();
		spVorher.setBounds(33, 216, 139, 68);
		contentPane.add(spVorher);
		
		taVorher = new JTextArea();
		taVorher.setEditable(false);
		
		//Ausgabe der Spieler im Spielerfenster
		schreibeVorher();
		
		
		spNachher = new JScrollPane();
		spNachher.setBounds(237, 214, 154, 68);
		contentPane.add(spNachher);
		
		taNachher = new JTextArea();
		taNachher.setEditable(false);
		spNachher.setViewportView(taNachher);
		
		JLabel lbBanner = new JLabel("");
		lbBanner.setIcon(new ImageIcon(SpielerTauschFenster.class.getResource("/image/logo_final.png")));
		lbBanner.setBounds(256, 315, 170, 30);
		contentPane.add(lbBanner);
		
		
		JButton btTauschen = new JButton("Tauschen");
		btTauschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Eingabe
				int von = leseVon();
				System.out.println(von);
				
				int nach = leseNach();
				System.out.println(nach);
				
				//Verarbeitung
				//Ermittelt anhand der Auswahl (auswahl)die benötigte Spielerliste und gibt diese zurück
				String[] spieler = myManager.holeSpielerliste(auswahl);
				myManager.tausche(spieler, von, nach);
				
				
				//Ausgabe
				schreibeNachher();
				spNachher.setViewportView(taNachher);
				
			}
		});
		btTauschen.setBounds(33, 322, 139, 23);
		contentPane.add(btTauschen);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public SpielerTauschFenster(VolleyballspielerTeamManager o, int pAuswahl) {
		
		this.myManager = o;
		this.auswahl = pAuswahl;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbTitle = new JLabel("Spieler Tauschen");
		lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbTitle.setIcon(new ImageIcon(SpielerTauschFenster.class.getResource("/image/ic_launcher.png")));
		lbTitle.setBounds(24, 11, 292, 78);
		contentPane.add(lbTitle);
		
		JLabel lbVon = new JLabel("Von Position:");
		lbVon.setBounds(33, 124, 89, 14);
		contentPane.add(lbVon);
		
		cbPositionenVon = new JComboBox();
		cbPositionenVon.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6"}));
		cbPositionenVon.setBounds(149, 120, 55, 22);
		contentPane.add(cbPositionenVon);
		
		JLabel lbNach = new JLabel("Nach Position:");
		lbNach.setBounds(237, 124, 89, 14);
		contentPane.add(lbNach);
		
		cbPositionenNach = new JComboBox();
		cbPositionenNach.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6"}));
		cbPositionenNach.setBounds(351, 120, 55, 22);
		contentPane.add(cbPositionenNach);
		
		JLabel lbVorher = new JLabel("Vorher:");
		lbVorher.setBounds(34, 175, 73, 14);
		contentPane.add(lbVorher);
		
		JLabel lbNachher = new JLabel("Nachher:");
		lbNachher.setBounds(237, 175, 79, 14);
		contentPane.add(lbNachher);
		
		spVorher = new JScrollPane();
		spVorher.setBounds(33, 216, 139, 68);
		contentPane.add(spVorher);
		
		taVorher = new JTextArea();
		taVorher.setEditable(false);
		
		//Ausgabe der Spieler im Spielerfenster
		schreibeVorher();
		
		spVorher.setViewportView(taVorher);
		
		
		spNachher = new JScrollPane();
		spNachher.setBounds(237, 214, 154, 68);
		contentPane.add(spNachher);
		
		taNachher = new JTextArea();
		taNachher.setEditable(false);
		
		
		JLabel lbBanner = new JLabel("");
		lbBanner.setIcon(new ImageIcon(SpielerTauschFenster.class.getResource("/image/logo_final.png")));
		lbBanner.setBounds(256, 315, 170, 30);
		contentPane.add(lbBanner);
		
		
		JButton btTauschen = new JButton("Tauschen");
		btTauschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Eingabe
				int von = leseVon();
				System.out.println(von);
				
				int nach = leseNach();
				System.out.println(nach);
				
				//Verarbeitung
				//Ermittelt anhand der Auswahl (auswahl)die benötigte Spielerliste und gibt diese zurück
				String[] spieler = myManager.holeSpielerliste(auswahl);
				myManager.tausche(spieler, von, nach);
				
				
				//Ausgabe
				schreibeNachher();
				spNachher.setViewportView(taNachher);
				
			}
		});
		btTauschen.setBounds(33, 322, 139, 23);
		contentPane.add(btTauschen);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	//Hilfsmethoden
	
	public int leseVon() {
		int von = cbPositionenVon.getSelectedIndex();
		return von;
	}
	
	public int leseNach() {
		int nach = cbPositionenNach.getSelectedIndex();
		return nach;
	}
	
	public void schreibeVorher() {		
		String ergebnis;
		switch (this.auswahl) {
			case 1: 
				ergebnis = myManager.zeigeStartaufstellung();
				break;
			case 2:
				ergebnis = myManager.zeigeErsatzspieler();
				break;
			default:
				ergebnis = "";
				break;
		}
		taVorher.setText(ergebnis);
	}
	

	
	public void schreibeNachher() {	
			String ergebnis;
			switch (this.auswahl) {
				case 1: 
					ergebnis = myManager.zeigeStartaufstellung();
					break;
				case 2:
					ergebnis = myManager.zeigeErsatzspieler();
					break;
				default:
					ergebnis = "";
					break;
			}			
		taNachher.setText(ergebnis);
	}
}


