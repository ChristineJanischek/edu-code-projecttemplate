package volleyball;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * View-Klasse: Fenster zum Einfügen eines neuen Spielers in eine Liste.
 *
 * <p>Der Benutzer gibt einen Spielernamen und einen Index (0-basiert) ein.
 * Nach dem Klick auf ">>" wird der neue Spieler eingefügt und die Liste
 * aktualisiert angezeigt.</p>
 *
 * <p><strong>MVC-Rolle:</strong> View + Controller – nimmt die Eingabe
 * entgegen, validiert sie und delegiert die Verarbeitung an den
 * {@link VolleyballspielerTeamManager} (Model).</p>
 */
public class SpielerEinfuegeWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    // ---- UI-Komponenten ----
    private JPanel contentPane;
    private JTextField tfNeuerSpieler;
    private JTextField tfStelle;
    private JTextArea taAusgabe;

    /** Referenz auf den gemeinsamen Manager (Model) */
    private final VolleyballspielerTeamManager manager;

    /** Gewählte Kategorie: 1 = Startaufstellung, 2 = Ersatzspieler */
    private final int auswahl;

    // ---- Konstruktor ----

    /**
     * Erstellt das Einfüge-Fenster für die gewählte Spieler-Kategorie.
     *
     * @param manager Der gemeinsame Team-Manager (Model)
     * @param auswahl 1 = Startaufstellung, 2 = Ersatzspieler
     */
    public SpielerEinfuegeWindow(VolleyballspielerTeamManager manager, int auswahl) {
        this.manager = manager;
        this.auswahl = auswahl;
        initUI();
    }

    // ---- UI-Initialisierung ----

    /**
     * Baut alle UI-Komponenten des Fensters auf.
     */
    private void initUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 411);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Titel
        JLabel lbTitel = new JLabel("Spieler einfügen");
        lbTitel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbTitel.setIcon(new ImageIcon(SpielerEinfuegeWindow.class.getResource("/image/ic_launcher.png")));
        lbTitel.setBounds(10, 0, 262, 81);
        contentPane.add(lbTitel);

        // Eingabe-Labels
        JLabel lbNeuerSpieler = new JLabel("Neuer Spieler:");
        lbNeuerSpieler.setBounds(24, 116, 96, 14);
        contentPane.add(lbNeuerSpieler);

        JLabel lbStelle = new JLabel("an der Stelle mit dem Index:");
        lbStelle.setBounds(24, 153, 201, 14);
        contentPane.add(lbStelle);

        // Eingabe-Textfelder
        tfNeuerSpieler = new JTextField();
        tfNeuerSpieler.setColumns(10);
        tfNeuerSpieler.setBounds(118, 113, 169, 20);
        contentPane.add(tfNeuerSpieler);

        tfStelle = new JTextField();
        tfStelle.setColumns(10);
        tfStelle.setBounds(248, 150, 39, 20);
        contentPane.add(tfStelle);

        // Einfügen-Button
        JButton btEinfuegen = new JButton(">>");
        btEinfuegen.addActionListener(e -> onEinfuegenKlick());
        btEinfuegen.setBounds(24, 198, 89, 23);
        contentPane.add(btEinfuegen);

        // Ausgabe-Bereich
        taAusgabe = new JTextArea();
        taAusgabe.setEditable(false);
        JScrollPane spAusgabe = new JScrollPane(taAusgabe);
        spAusgabe.setBounds(171, 197, 240, 160);
        contentPane.add(spAusgabe);

        // Logo-Banner
        JLabel lbBanner = new JLabel("");
        lbBanner.setIcon(new ImageIcon(SpielerEinfuegeWindow.class.getResource("/image/logo_final.png")));
        lbBanner.setBounds(249, 328, 175, 33);
        contentPane.add(lbBanner);
    }

    // ---- Controller-Methoden (Action-Handler) ----

    /**
     * Verarbeitet den Klick auf den ">>" Einfügen-Button.
     *
     * <p>Liest und validiert die Eingaben, delegiert das Einfügen
     * an den Manager (Model) und aktualisiert die Ausgabe-Ansicht.</p>
     */
    private void onEinfuegenKlick() {
        // Eingabe lesen
        String neuerSpielerName = tfNeuerSpieler.getText().trim();
        if (neuerSpielerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie einen Spielernamen ein.");
            return;
        }

        int stelle;
        try {
            stelle = Integer.parseInt(tfStelle.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Bitte eine gültige Zahl für die Stelle eingeben.");
            return;
        }

        int maxStelle = manager.holeSpielerliste(auswahl).size();
        if (stelle < 0 || stelle > maxStelle) {
            JOptionPane.showMessageDialog(this,
                "Ungültige Stelle. Erlaubter Bereich: 0 bis " + maxStelle + ".");
            return;
        }

        // Verarbeitung: Delegation an Manager (Model)
        manager.einfuegen(auswahl, neuerSpielerName, stelle);

        // Ausgabe aktualisieren
        anzeigeAktualisieren();
    }

    /**
     * Aktualisiert die Spielerlisten-Anzeige nach einer Einfüge-Operation.
     */
    private void anzeigeAktualisieren() {
        String inhalt;
        switch (auswahl) {
            case 1:
                inhalt = manager.zeigeStartaufstellung();
                break;
            case 2:
                inhalt = manager.zeigeErsatzspieler();
                break;
            default:
                inhalt = "";
        }
        taAusgabe.setText(inhalt);
    }
}
