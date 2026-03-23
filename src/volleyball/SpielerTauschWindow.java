package volleyball;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * View-Klasse: Fenster zum Tauschen zweier Spieler innerhalb einer Liste.
 *
 * <p>Der Benutzer wählt zwei Positionen (ComboBoxen) und klickt auf "Tauschen".
 * Die Vorher- und Nachher-Ansicht werden nebeneinander dargestellt.</p>
 *
 * <p><strong>MVC-Rolle:</strong> View + Controller – nimmt die Eingabe
 * entgegen und delegiert die Verarbeitung an den
 * {@link VolleyballspielerTeamManager} (Model).</p>
 */
public class SpielerTauschWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    // ---- UI-Komponenten ----
    private JPanel contentPane;
    private JComboBox<String> cbPositionenVon;
    private JComboBox<String> cbPositionenNach;
    private JTextArea taVorher;
    private JTextArea taNachher;

    /** Referenz auf den gemeinsamen Manager (Model) */
    private final VolleyballspielerTeamManager manager;

    /** Gewählte Kategorie: 1 = Startaufstellung, 2 = Ersatzspieler */
    private final int auswahl;

    // ---- Konstruktor ----

    /**
     * Erstellt das Tausch-Fenster für die gewählte Spieler-Kategorie.
     *
     * @param manager Der gemeinsame Team-Manager (Model)
     * @param auswahl 1 = Startaufstellung, 2 = Ersatzspieler
     */
    public SpielerTauschWindow(VolleyballspielerTeamManager manager, int auswahl) {
        this.manager = manager;
        this.auswahl = auswahl;
        initUI();
        // Vorher-Anzeige initial befüllen
        taVorher.setText(aktuelleListeAlsString());
    }

    // ---- UI-Initialisierung ----

    /**
     * Baut alle UI-Komponenten des Fensters auf.
     */
    private void initUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 393);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Titel
        JLabel lbTitle = new JLabel("Spieler Tauschen");
        lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbTitle.setIcon(new ImageIcon(SpielerTauschWindow.class.getResource("/image/ic_launcher.png")));
        lbTitle.setBounds(24, 11, 292, 78);
        contentPane.add(lbTitle);

        // Positions-Labels
        JLabel lbVon = new JLabel("Von Position:");
        lbVon.setBounds(33, 124, 89, 14);
        contentPane.add(lbVon);

        JLabel lbNach = new JLabel("Nach Position:");
        lbNach.setBounds(237, 124, 89, 14);
        contentPane.add(lbNach);

        // Positions-ComboBoxen: dynamisch aus der aktuellen Listengröße befüllt
        String[] positionen = erstellePositionenArray();
        cbPositionenVon = new JComboBox<>(positionen);
        cbPositionenVon.setBounds(149, 120, 55, 22);
        contentPane.add(cbPositionenVon);

        cbPositionenNach = new JComboBox<>(positionen);
        cbPositionenNach.setBounds(351, 120, 55, 22);
        contentPane.add(cbPositionenNach);

        // Vorher/Nachher-Labels
        JLabel lbVorher = new JLabel("Vorher:");
        lbVorher.setBounds(34, 175, 73, 14);
        contentPane.add(lbVorher);

        JLabel lbNachher = new JLabel("Nachher:");
        lbNachher.setBounds(237, 175, 79, 14);
        contentPane.add(lbNachher);

        // Vorher-Anzeige
        taVorher = new JTextArea();
        taVorher.setEditable(false);
        JScrollPane spVorher = new JScrollPane(taVorher);
        spVorher.setBounds(33, 216, 139, 68);
        contentPane.add(spVorher);

        // Nachher-Anzeige
        taNachher = new JTextArea();
        taNachher.setEditable(false);
        JScrollPane spNachher = new JScrollPane(taNachher);
        spNachher.setBounds(237, 214, 154, 68);
        contentPane.add(spNachher);

        // Logo-Banner
        JLabel lbBanner = new JLabel("");
        lbBanner.setIcon(new ImageIcon(SpielerTauschWindow.class.getResource("/image/logo_final.png")));
        lbBanner.setBounds(256, 315, 170, 30);
        contentPane.add(lbBanner);

        // Tauschen-Button
        JButton btTauschen = new JButton("Tauschen");
        btTauschen.addActionListener(e -> onTauschenKlick());
        btTauschen.setBounds(33, 322, 139, 23);
        contentPane.add(btTauschen);
    }

    // ---- Hilfsmethoden ----

    /**
     * Erzeugt ein String-Array mit 1-basierten Positionsbezeichnungen,
     * entsprechend der aktuellen Größe der gewählten Spielerliste.
     *
     * @return positionsnummern als String-Array, z.B. ["1", "2", ..., "6"]
     */
    private String[] erstellePositionenArray() {
        int groesse = manager.holeSpielerliste(auswahl).size();
        String[] positionen = new String[groesse];
        for (int i = 0; i < groesse; i++) {
            positionen[i] = String.valueOf(i + 1);
        }
        return positionen;
    }

    /**
     * Gibt den aktuellen Inhalt der gewählten Liste als formatierten String zurück.
     *
     * @return Zeilenweise Spielerliste
     */
    private String aktuelleListeAlsString() {
        switch (auswahl) {
            case 1: return manager.zeigeStartaufstellung();
            case 2: return manager.zeigeErsatzspieler();
            default: return "";
        }
    }

    // ---- Controller-Methoden (Action-Handler) ----

    /**
     * Verarbeitet den Klick auf den "Tauschen"-Button.
     *
     * <p>Liest die gewählten Positionen (0-basiert aus ComboBox-Index),
     * delegiert den Tausch an den Manager (Model) und aktualisiert
     * die Nachher-Anzeige.</p>
     */
    private void onTauschenKlick() {
        // Eingabe: ComboBox-Index entspricht dem 0-basierten Array-Index
        int von  = cbPositionenVon.getSelectedIndex();
        int nach = cbPositionenNach.getSelectedIndex();

        // Verarbeitung: Delegation an Manager (Model)
        manager.tausche(auswahl, von, nach);

        // Ausgabe: Nachher-Anzeige aktualisieren
        taNachher.setText(aktuelleListeAlsString());
    }
}
