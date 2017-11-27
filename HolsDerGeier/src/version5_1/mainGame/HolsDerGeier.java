package version5_1.mainGame;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * 
 */

public class HolsDerGeier {
	/** Die Geier- und Maeusekarten <p>
	 *	<b> Die Punkte Katen </b> 
	 */
	private ArrayList<Integer> nochZuVergebendeGeierKarten = new ArrayList<Integer>();

	/**
	 * Die von den Bots gespielten Karten [n x 1-15] 2 Dimensional d.h. für 2 bots!
	 */
	private ArrayList<ArrayList<Integer>> gespielteKarten = new ArrayList<ArrayList<Integer>>();

	/** Punktestaende */
	private int punkte;
	private int[] punktstaende = new int[2];

	/** Die Bot die am Spiel teilnehmen sollen */
	private HolsDerGeierSpieler[] bots;

	/**
     * 
     */
	public HolsDerGeier() {
		gespielteKarten.add(new ArrayList<Integer>());
		gespielteKarten.add(new ArrayList<Integer>());
	}

	/**
	 * Neu laden der Karten bzw. zurücksetzen!
	 */
	private void ladeSpiel() {
		// Geier- und Maeusekarten
		nochZuVergebendeGeierKarten.clear();
		for (int i = -5; i <= 10; i++)
			if (i != 0) {
				nochZuVergebendeGeierKarten.add(i);
			}
	}

	/**
	 * Spiele zufaellig die naechste Geier- bzw. Maeusekarte
	 */
	private int spieleNaechsteKarte() {
		int nochNichtVergeben = nochZuVergebendeGeierKarten.size();
		int index = (int) (Math.random() * nochNichtVergeben);
		int ret = nochZuVergebendeGeierKarten.remove(index);
		return ret;
	}

	/**
	 * Hier kann nach dem letzten Zug gefragt werden. Aber diese Methode ist so
	 * eigentlich nicht wirklich gelungen.
	 */
	public int letzterZug(int nummerBot) {
		if (gespielteKarten.get(nummerBot).size() > 0)
			return gespielteKarten.get(nummerBot).get(gespielteKarten.get(nummerBot).size() - 1);
		else
			return -99;
	}

	/**
	 * Setzt das ganze Spiel zurück
	 */
	private void reset() {
		punkte = 0;
		for (int i = 0; i < gespielteKarten.size(); i++)
			gespielteKarten.get(i).clear();
		ladeSpiel();
		for (int i = 0; i < punktstaende.length; i++)
			punktstaende[i] = 0;
		for (int i = 0; i < bots.length; i++)
			bots[i].reset();

	}

	public void initBots(HolsDerGeierSpieler bot1, HolsDerGeierSpieler bot2) {
		// Hier wird das Bot Array bestückt
		bots = new HolsDerGeierSpieler[2];
		bots[0] = bot1;
		bots[1] = bot2;
		bot1.register(this, 0);
		bot2.register(this, 1);
	}

	/**
	 * Starte ein neues Spiel
	 */
	public void naechstesSpiel() {
		if (bots == null)
			System.out.println("Noch keine Sieler angemeldet!");
		else {
			System.out.println("===============");
			System.out.println("= NEUES SPIEL, ES STEHT 0:0 =");
			System.out.println("===============");
			reset();
		}
	}

	/**
	 * Der naechste Spielzug wird ausgefuehrt. Dazu wird: - Neue Geier- oder
	 * Maeusekarte ermittelt - Zufaellig eine Karte vom Computer gespielt - Die
	 * Spieler werden gefragt nach den Karten gefragt - Ausgewertet und der
	 * Punktestand gefuehrt
	 */
	public void naechsterZug() throws Exception {
		if (!nochZuVergebendeGeierKarten.isEmpty()) {

			// naechste Geier- Maeusekarte
			int naechsteKarte = spieleNaechsteKarte();
			punkte += naechsteKarte;

			int[] zuege = new int[2];

			// die Zuege der beiden Spieler
			for (int i = 0; i < bots.length; i++) {
				zuege[i] = bots[i].gibKarte(naechsteKarte);

				// Sicher ist sicher: Haben Sie diese Karten schon einmal
				// gespielt?
				// Wenn ja: Jetzt ist aber Schluss
				// Wenn nein: Ich merke mit die Karte
				if (gespielteKarten.get(i).contains(zuege[i]))
					throw new Exception(
							"GESCHUMMELT: Diese Karte wurde bereits gespielt "
									+ i + " " + zuege[i]);

				if ((zuege[i] < 1) || (zuege[i] > 15))
					throw new Exception(
							"GESCHUMMELT: Diese Karte gibt es gar nicht");
			}

			// Alle Zuege fertig, dann eintragen (erst hier wg. Methode
			// naechsterZug
			for (int i = 0; i < bots.length; i++)
				gespielteKarten.get(i).add(zuege[i]);

			// So sieht der aktuelle Zug aus
			System.out.println("Ausgespielte Karte: " + naechsteKarte);
			System.out.println("Zug erster Spieler: " + zuege[0]);
			System.out.println("Zug zweiter Spieler: " + zuege[1]);

			// Wer kriegt die Punkte?

			// Lösung: Es muss zwischen Maeuse- (nachesteKarte>0) und
			// Geierkarten ((nachesteKarte<0) unterschieden werden.
			if (zuege[0] != zuege[1]) {
				if (punkte > 0)
					if (zuege[0] > zuege[1])
						punktstaende[0] = punktstaende[0] + punkte;
					else
						punktstaende[1] = punktstaende[1] + punkte;
				else if (zuege[0] < zuege[1])
					punktstaende[0] = punktstaende[0] + punkte;
				else
					punktstaende[1] = punktstaende[1] + punkte;
				punkte = 0;
			} else
				System.out.println("Unentschieden - Punkte wandern in die naechste Runde");
				System.out.println("Spielstand: " + punktstaende[0] + " : "	+ punktstaende[1]);
				System.out.println("Mein Bot: " + punktstaende[0]);
				System.out.println("Mein Gegner: " + punktstaende[1]);
		} else
			System.out.println("Spiel ist zu Ende. Sie muessen zuerst die Methode neues Siel aufrufen");

	}

	/**
	 * Hier kann ein vollstaendiges Spiel durchgefuehrt werden!
	 */
	public void ganzesSpiel() throws Exception {
		if (nochZuVergebendeGeierKarten.isEmpty())
			naechstesSpiel();
		while (!nochZuVergebendeGeierKarten.isEmpty()) {
			naechsterZug();
		}
	}

}
