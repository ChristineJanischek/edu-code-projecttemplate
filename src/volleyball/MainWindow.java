package volleyball;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/**
 * View-Klasse: Hauptfenster der Volleyball-Team-Manager-Anwendung.
 *
 * <p>Dient als Einstiegspunkt der Anwendung. Der Benutzer wählt hier eine
 * Spielerkategorie (Startaufstellung, Ersatzspieler oder Kader) und eine
 * Aktion (anzeigen, tauschen, einfügen, entfernen).</p>
 *
 * <p><strong>MVC-Rolle:</strong> View + Controller für die Haupt-Navigation.
 * Die gemeinsame {@link VolleyballspielerTeamManager}-Instanz (Model) wird
 * einmalig hier erzeugt und an alle Sub-Fenster weitergegeben.</p>
 */
public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    // ---- UI-Komponenten (View) ----
    private JPanel contentPane;
    private final ButtonGroup rbGruppe = new ButtonGroup();
    private JRadioButton rbStartaufstellung;
    private JRadioButton rbErsatzspieler;
    private JRadioButton rbKader;

    /**
     * Gemeinsame Manager-Instanz (Model) – wird einmalig erzeugt und
     * an alle Sub-Fenster weitergegeben (Assoziation).
     */
    private final VolleyballspielerTeamManager manager;

    // ---- Einstiegspunkt ----

    /**
     * Startet die Anwendung.
     *
     * @param args Kommandozeilenargumente (nicht genutzt)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainWindow frame = new MainWindow();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // ---- Konstruktor ----

    /**
     * Erstellt das Hauptfenster und initialisiert alle UI-Komponenten.
     */
    public MainWindow() {
        this.manager = new VolleyballspielerTeamManager();
        initUI();
    }

    // ---- UI-Initialisierung ----

    /**
     * Baut alle UI-Komponenten des Hauptfensters auf.
     */
    private void initUI() {
        setTitle("Volleyball-Team-Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 413, 368);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Titel-Label mit App-Icon
        JLabel lbTitel = new JLabel("Volleyball-Team-Manager");
        lbTitel.setIcon(new ImageIcon(MainWindow.class.getResource("/image/ic_launcher.png")));
        lbTitel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbTitel.setBounds(31, 11, 327, 84);
        contentPane.add(lbTitel);

        // Logo-Banner
        JLabel lbBanner = new JLabel("");
        lbBanner.setIcon(new ImageIcon(MainWindow.class.getResource("/image/logo_final.png")));
        lbBanner.setBounds(219, 290, 170, 30);
        contentPane.add(lbBanner);

        // Radio-Buttons zur Auswahl der Spieler-Kategorie
        rbStartaufstellung = new JRadioButton("Startaufstellung");
        rbGruppe.add(rbStartaufstellung);
        rbStartaufstellung.setBounds(47, 124, 178, 23);
        contentPane.add(rbStartaufstellung);

        rbErsatzspieler = new JRadioButton("Ersatzspieler");
        rbGruppe.add(rbErsatzspieler);
        rbErsatzspieler.setBounds(47, 164, 189, 23);
        contentPane.add(rbErsatzspieler);

        rbKader = new JRadioButton("Kaderspieler");
        rbGruppe.add(rbKader);
        rbKader.setBounds(47, 208, 189, 23);
        contentPane.add(rbKader);

        // Aktions-Buttons mit Lambda-ActionListenern
        JButton btAnzeigen = new JButton("anzeigen");
        btAnzeigen.addActionListener(e -> onAnzeigenKlick());
        btAnzeigen.setBounds(269, 146, 89, 23);
        contentPane.add(btAnzeigen);

        JButton btTauschen = new JButton("tauschen");
        btTauschen.addActionListener(e -> onTauschenKlick());
        btTauschen.setBounds(269, 180, 89, 23);
        contentPane.add(btTauschen);

        JButton btEinfuegen = new JButton("einfügen");
        btEinfuegen.addActionListener(e -> onEinfuegenKlick());
        btEinfuegen.setBounds(269, 218, 89, 23);
        contentPane.add(btEinfuegen);

        JButton btEntfernen = new JButton("entfernen");
        btEntfernen.setBounds(269, 256, 89, 23);
        contentPane.add(btEntfernen);
    }

    // ---- Controller-Methoden (Action-Handler) ----

    /**
     * Öffnet das Spieler-Anzeige-Fenster für die gewählte Kategorie.
     * Erlaubt: Startaufstellung, Ersatzspieler und Kader.
     */
    private void onAnzeigenKlick() {
        int auswahl = leseAuswahl(true, true, true);
        if (auswahl > 0) {
            new SpielerWindow(manager, auswahl).setVisible(true);
        }
    }

    /**
     * Öffnet das Spieler-Tausch-Fenster für die gewählte Kategorie.
     * Erlaubt: Startaufstellung und Ersatzspieler (nicht Kader).
     */
    private void onTauschenKlick() {
        int auswahl = leseAuswahl(true, true, false);
        if (auswahl > 0) {
            new SpielerTauschWindow(manager, auswahl).setVisible(true);
        }
    }

    /**
     * Öffnet das Spieler-Einfüge-Fenster für die gewählte Kategorie.
     * Erlaubt: Startaufstellung und Ersatzspieler (nicht Kader).
     */
    private void onEinfuegenKlick() {
        int auswahl = leseAuswahl(true, true, false);
        if (auswahl > 0) {
            new SpielerEinfuegeWindow(manager, auswahl).setVisible(true);
        }
    }

    /**
     * Liest die gewählte Radio-Button-Option und prüft, ob sie für die aktuelle
     * Aktion erlaubt ist.
     *
     * @param startErlaubt  {@code true} wenn Startaufstellung für diese Aktion erlaubt ist
     * @param ersatzErlaubt {@code true} wenn Ersatzspieler für diese Aktion erlaubt ist
     * @param kaderErlaubt  {@code true} wenn Kader für diese Aktion erlaubt ist
     * @return Auswahl-Index (1, 2 oder 3) oder 0 bei fehlender/unzulässiger Auswahl
     */
    private int leseAuswahl(boolean startErlaubt, boolean ersatzErlaubt, boolean kaderErlaubt) {
        boolean start  = rbStartaufstellung.isSelected();
        boolean ersatz = rbErsatzspieler.isSelected();
        boolean kader  = rbKader.isSelected();

        if (!start && !ersatz && !kader) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Option aus!");
            return 0;
        }

        if (start  && startErlaubt)  return 1;
        if (ersatz && ersatzErlaubt) return 2;
        if (kader  && kaderErlaubt)  return 3;

        // Auswahl ist für diese Aktion nicht erlaubt
        JOptionPane.showMessageDialog(this,
            "Diese Option ist für die gewählte Aktion nicht verfügbar.\n" +
            "Bitte wählen Sie Startaufstellung oder Ersatzspieler.");
        return 0;
    }
}
