package volleyball;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * UI-Helfer zum defensiven Laden optionaler Ressourcen.
 */
public final class UiResources {

    private UiResources() {
        // Utility-Klasse: keine Instanzierung.
    }

    /**
     * Setzt ein Label-Icon nur dann, wenn die Resource im Classpath vorhanden ist.
     *
     * @param label        Ziel-Label
     * @param resourcePath Classpath-Pfad, z. B. /image/logo_final.png
     */
    public static void setIconIfPresent(JLabel label, String resourcePath) {
        URL location = UiResources.class.getResource(resourcePath);
        if (location != null) {
            label.setIcon(new ImageIcon(location));
        }
    }
}
