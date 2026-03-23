package volleyball;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Hauptfenster extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup rbSpieler = new ButtonGroup();
	private JRadioButton rbStartaufstellung;
	private JRadioButton rbErsatzspieler;
	private JRadioButton rbKader;
	private int verhalten;
	
	//Assoziation zur (Fach)klasse
	VolleyballspielerTeamManager myManager = new VolleyballspielerTeamManager();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hauptfenster frame = new Hauptfenster();
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
	public Hauptfenster() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 413, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbTitel = new JLabel("Volleyball-Team-Manager");
		lbTitel.setIcon(new ImageIcon(Hauptfenster.class.getResource("/image/ic_launcher.png")));
		lbTitel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbTitel.setBounds(31, 11, 327, 84);
		contentPane.add(lbTitel);
		
		JLabel lbBanner = new JLabel("");
		lbBanner.setIcon(new ImageIcon(Hauptfenster.class.getResource("/image/logo_final.png")));
		lbBanner.setBounds(219, 290, 170, 30);
		contentPane.add(lbBanner);
		
		rbStartaufstellung = new JRadioButton("Startaufstellung");
		rbSpieler.add(rbStartaufstellung);
		rbStartaufstellung.setBounds(47, 124, 178, 23);
		contentPane.add(rbStartaufstellung);
		
		rbErsatzspieler = new JRadioButton("Ersatzspieler");
		rbSpieler.add(rbErsatzspieler);
		rbErsatzspieler.setBounds(47, 164, 189, 23);
		contentPane.add(rbErsatzspieler);
		
		rbKader = new JRadioButton("Kaderspieler");
		rbSpieler.add(rbKader);
		rbKader.setBounds(47, 208, 189, 23);
		contentPane.add(rbKader);
		
		JButton btAnzeigen = new JButton("anzeigen");
		btAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verhalten = 1;
				
				//Eingabe
				int auswahl = leseEingabe();
				
				//Verarbeitung
				myManager.zeigeStartaufstellung();
				
				//Neues Fenster erzeugen
				SpielerFenster spielerFenster = new SpielerFenster(myManager, auswahl);
				
				
				//Ausgabe
				spielerFenster.setVisible(true);
				
			}
		});
		btAnzeigen.setBounds(269, 146, 89, 23);
		contentPane.add(btAnzeigen);
		
		JButton btTauschen = new JButton("tauschen");
		btTauschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verhalten = 2;
				
				//Eingabe
				int auswahl = leseEingabe();
				
				//Neues Fenster erzeugen
				SpielerTauschFenster spielerTauschFenster = new SpielerTauschFenster(myManager, auswahl);
				
				//Ausgabe
				spielerTauschFenster.setVisible(true);
				
			}
		});
		btTauschen.setBounds(269, 180, 89, 23);
		contentPane.add(btTauschen);
		
		JButton btbtEinfuegen = new JButton("einf\u00FCgen");
		btbtEinfuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verhalten = 3;
				
				//Eingabe
				int auswahl = leseEingabe();
				
				//Neues Fenster erzeugen
				SpielerEinfuegeFenster spielerEinfuegeFenster = new SpielerEinfuegeFenster(myManager, auswahl);
				
				//Ausgabe
				spielerEinfuegeFenster.setVisible(true);				
				
			}
		});
		btbtEinfuegen.setBounds(269, 218, 89, 23);
		contentPane.add(btbtEinfuegen);
		
		JButton btEntfernen = new JButton("entfernen");
		btEntfernen.setBounds(269, 256, 89, 23);
		contentPane.add(btEntfernen);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	//Hilfsmethode
	private int leseEingabe() {
		boolean startaufstellung  = rbStartaufstellung.isSelected();
		boolean ersatzspieler = rbErsatzspieler.isSelected();
		boolean kaderspieler = rbKader.isSelected();
		int auswahl = 0;
		if(this.verhalten == 1) {								
			if(startaufstellung) {
				auswahl = 1;
			}
			else if(ersatzspieler) {
				auswahl = 2;
			}
			else if(kaderspieler) {
				auswahl = 3;
			}
			else if(startaufstellung == false && ersatzspieler == false && kaderspieler == false ) {
				JOptionPane.showMessageDialog(null, 
						"Bitte wählen Sie die geeignete Anzeigeoption aus!");
			}else {
				auswahl = 2;
			}
		
		}else if(this.verhalten == 2) {
			if(startaufstellung) {
				auswahl = 1;
			}
			else if(ersatzspieler) {
				auswahl = 2;
			}
			else if(kaderspieler) {
				JOptionPane.showMessageDialog(null, 
						"Nur die Startaufstellung oder Ersatzspieler können getauscht werden. \n Bitte wählen Sie die geeignete Tauschoption aus!");
			}
			else if(startaufstellung == false && ersatzspieler == false && kaderspieler == false ) {
				JOptionPane.showMessageDialog(null, 
						"Bitte wählen Sie die geeignete Tauschoption aus!");
			}
		}else if(this.verhalten == 3) {
			if(startaufstellung) {
				auswahl = 1;
			}
			else if(ersatzspieler) {
				auswahl = 2;
			}
			else if(kaderspieler) {
				JOptionPane.showMessageDialog(null, 
						"Nur in die Startaufstellung oder Ersatzspielerliste können neue Spieler eingefügt werden. \n Bitte wählen Sie die geeignete Einfügeoption aus!");
			}
			else if(startaufstellung == false && ersatzspieler == false && kaderspieler == false ) {
				JOptionPane.showMessageDialog(null, 
						"Bitte wählen Sie die geeignete Einfügeoption aus!");
			}
		}
		return auswahl;
	}
}
