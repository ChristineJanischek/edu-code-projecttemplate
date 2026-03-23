package volleyball;

public class VolleyballspielerTeamManager {
	//Deklaration und Initialisierung eines statischen Arrays in Java
	private String[] spieler = {"Armin", "Batu", "Kai", "Sven", "Paul", "Milan"};
	private String[] ersatz = {"Chris", "Dennis", "Emin", "Goran", "Luca", "Nico"};
	
	
	//Default Konstruktor
	public VolleyballspielerTeamManager() {
		
	}
	
	//Getter und Setter
	public void setSpieler(String[] pSpieler) {
		this.spieler = pSpieler;
	}
	
	public void setErsatz(String[] pErsatz) {
		this.ersatz = pErsatz;
	}	
	
	
	//Weitere Methoden (Verhaltensweisen)
	
	public String[] tausche(String[] pSpieler,int pVon, int pNach) {
		
		//Parkt das Element an der Stelle pNach im Zwischenspeicher
		String zwischenspeicher = pSpieler[pNach];
		//Das Element an der Stelle pVon wird an die Stelle pNach geschrieben
		pSpieler[pNach] = pSpieler[pVon];
		//Nimmt das geparkte Element im Zwischenspeicher und schreibt es an die Stelle pVon
		pSpieler[pVon] = zwischenspeicher;
		//Gibt das Spielerarray zurück
		return pSpieler;
	}
	
	public String[] einfuegen(String[] pSpieler, String pNeuerSpieler, int pStelle) {
		//String[] neueListeErzeugen(String [] liste)
		String[] neueListe = neueListeErzeugen(pSpieler);
		//Deklarieren und Initialisieren der stelle mit dem Index als Ganzzahl (int)
		int stelle = pStelle;
		//Deklarieren und Initialisieren von spielername als Zeichenkette (String)
		String spielername = pNeuerSpieler;
		//Deklarieren und Initialisieren der anfangswert des Arrays liste als Ganzzahl (int) -1
		int anfangswert = neueListe.length-1;
		//Durchlaufe die liste von laenge (i) bis stelle, Schrittweite -1
		for(int i = anfangswert; i >= stelle ; i-- ) {
			//liste[i] = liste[i-1] schiebt Elemente von links nach rechts
			neueListe[i] = neueListe[i-1];
			//Prüfung: i == stelle --> Einfügestelle erreicht?
			if(i == stelle) {
				//JA-Fall: liste[i-1] = spielername
				neueListe[i-1] = spielername;
			}
		}	
		//aktualisierte Liste zurückgeben
		return neueListe;
	}
	
	private String[] neueListeErzeugen(String[] pListe) {
		String[] neueListe = new String[pListe.length+1];
		for(int i = 0;i < pListe.length;i++ ) {
			neueListe[i] = pListe[i];
		}
				
		return neueListe;
	}
	
	public String[] holeSpielerliste(int pAuswahl) {
		//Deklaration und Initialisierung eines noch leeren lokalen Spielerarrays
		String[] mSpieler = null;
		//Prüft in welchem Array getauscht werden soll
		if(pAuswahl == 1) {
			//Deklaration und Initialisierung des lokalen Spielerarrays mit der Spielerliste
			mSpieler = spieler;
		}else if(pAuswahl == 2) {
			//Deklaration und Initialisierung des lokalen Spielerarrays mit der Eratzspielerliste
			mSpieler = ersatz;
		}
		//Gibt das Spielerarray zurück
		
		return mSpieler;
	}
	
	public String zeigeStartaufstellung() {
		String ergebnis = "";
		for(int i=0; i < spieler.length;i++) {
			//Für die Ausgabe auf der Konsole
			//System.out.println(spieler[i]);
			//Erzeugt die Zweichenkette mit Spielern (untereinenander) 
			//z.B. für eine Ausgabe
			ergebnis += spieler[i]+"\n";
		}
		
		return ergebnis;
	}
	
	public String zeigeErsatzspieler() {
		String ergebnis = "";
		for(int i=0; i < ersatz.length;i++) {
			//Für die Ausgabe auf der Konsole
			//System.out.println(ersatz[i]);
			//Erzeugt die Zweichenkette mit Spielern (untereinenander) 
			//z.B. für eine Ausgabe
			ergebnis += ersatz[i]+"\n";
		}
		
		return ergebnis;		
	}
	
	public String zeigeKader() {
		//ermittelt die anzahl der Elemente in spielerliste
		int laenge_spieler = spieler.length;
		//ermittelt die anzahl der Elemente in ersatzspielerliste
		int laenge_ersatz = ersatz.length;
		
		//addiert beide angaben um den Umfang für die kaderliste zu erhalten
		int laenge_kader = laenge_spieler + laenge_ersatz;
		
		String ergebnis = "";
		
		//erzeugt eine neue kaderliste mit dem ermittelten Umfang (Anzahl an Elementen)
		String[] kader = new String[laenge_kader];
		
		//fügt die spieler der reihe nach in die neue kaderliste ein
		for(int i = 0; i < laenge_spieler; i++ ) {
			//Spieler einfuegen
			kader[i] = spieler[i];
		}
		
		//hängt alle ersatzspieler der reihe nach hinten an die kaderliste an
		for(int i = 0; i < laenge_ersatz; i++ ) {
			//Spieler einfuegen			
			kader[i + laenge_spieler] = ersatz[i];
		}
		
		//erzeugt einen Ausgabe-String mit allen Kadermitgliedern
		//und gibt diesen String zurück
		for(int i=0; i < kader.length;i++) {
			//Für die Ausgabe auf der Konsole
			System.out.println(kader[i]);
			ergebnis += kader[i]+"\n";
		}
		return ergebnis;
		
	}
	
}
