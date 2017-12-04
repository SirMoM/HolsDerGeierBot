/**
 * 
 */
package game.bots;

import java.util.ArrayList;
import java.util.HashMap;

import game.mainGame.*;

/**
 * @author Sir.MoM
 *
 */
public class RuleThemAll extends HolsDerGeierSpieler {

	private ArrayList<Integer> nochNichtGespielt = new ArrayList<Integer>();
	private ArrayList<Integer> meineKarten = new ArrayList<Integer>();
	private ArrayList<Integer> dieKartenDesGegners = new ArrayList<Integer>();
	private ArrayList<Integer> diePunktekarten = new ArrayList<Integer>();
	private int[] letzteZuege = new int[2];
	private int letztePunktekarte;
	private int zug = 0;
	private HashMap<Integer, Double> kartenBewertung;

	/* (non-Javadoc)
	 * @see game.mainGame.HolsDerGeierSpieler#reset()
	 */
	@Override
	public void reset() {
		nochNichtGespielt.clear();
		meineKarten.clear();
		dieKartenDesGegners.clear();
		for (int i=1;i<=15;i++) {           
			nochNichtGespielt.add(i);
			meineKarten.add(i);
			dieKartenDesGegners.add(i);
		}
		
		for (int i = -5; i < 0; i++) {
			diePunktekarten.add(i);
		}
		
		for (int i = 1; i < 11; i++) {
			diePunktekarten.add(i);
		}
	}

	/* (non-Javadoc)
	 * @see game.mainGame.HolsDerGeierSpieler#gibKarte(int)
	 */
	@Override
	public int gibKarte(int naechsteKarte) {
		zug++;		
		if(zug > 1) {
			fillLetzteZuege();
			logInTabelle(letztePunktekarte); 
			deleteLastCards();
		}
		
		if(zug == 15) {
			MyLogger.logTable(getVerbleibendePunktekarte(), getVerbleibendeMeineKarte(), getVerbleibendeGegnerKarte(), getWhoWon(getVerbleibendeMeineKarte(), getVerbleibendeGegnerKarte()));
		}
		letztePunktekarte = naechsteKarte;
		MyLogger.log("######" + (zug - 1) + "######");
		
		
		System.out.println(naechsteKarte);
		int x = kartenBewertung.get(naechsteKarte).intValue();
		System.out.println(naechsteKarte + " " + x);
		return x;
	}

	private void deleteLastCards() {
		for (int i = 0; i < meineKarten.size(); i++) {
			int eineKarte = meineKarten.get(i);
			if(eineKarte == letzteZuege[this.getNummer()]) {
				meineKarten.remove(meineKarten.indexOf(eineKarte));
				MyLogger.log(eineKarte + " wurde aus meineKarten enfernt");
			}
		}

		for (int i = 0; i < dieKartenDesGegners.size(); i++) {
			int eineKarte = dieKartenDesGegners.get(i);
			if(eineKarte == letzteZuege[getOppositeBotNumber()]) {
				dieKartenDesGegners.remove(dieKartenDesGegners.indexOf(eineKarte));
				MyLogger.log(eineKarte + " wurde aus dieKartenDesGegners enfernt");
			}
		}
	}

	private int getVerbleibendeGegnerKarte() {
		int temp = 0;
		for (Integer eineKarte : dieKartenDesGegners) {
			temp = eineKarte;
		}
		MyLogger.log("Die verbleibenden Karte des Gegners: " + temp);
		return temp;
	}

	private int getVerbleibendeMeineKarte() {
		int temp = 0;
		for (Integer eineKarte : meineKarten) {
			temp = eineKarte;
		}
		MyLogger.log("Die meine verbleibenden Karte: " + temp);
		return temp;
	}

	private int getVerbleibendePunktekarte() {
		int temp = 0;
		for (Integer eineKarte : diePunktekarten) {
			temp = eineKarte;
		}
		MyLogger.log("Die verbleibenden Punktekarte: " + temp);
		return temp;
	}

	private void logInTabelle(int naechsteKarte) {
		MyLogger.logTable(naechsteKarte, letzteZuege[0], letzteZuege[1], getWhoWon(letzteZuege[0], letzteZuege[1]));

	}

	private String getWhoWon(int kartenWert1, int kartenWert2) {
		final String IWON = "GEWONNEN";
		final String ILOSE = "VERLOREN";
		final String TIED = "UNENTSCHIEDEN";
		final String NOTKNOWN = "GEWINNER KONNTE NICHT ERMITTELT WERDEN";
		
		if(kartenWert1 != kartenWert2) {
			if (kartenWert1 > kartenWert2) {
				return IWON;
			}else if (kartenWert1 < kartenWert2) {
				return ILOSE;
			}
		}else{
			return TIED;
		}
		return NOTKNOWN;
	}

	private int getZufallskarte() {
		int nochVorhanden = nochNichtGespielt.size();            
		int index = (int) (Math.random()*nochVorhanden);
		int ret = nochNichtGespielt.remove(index);
		return ret;
	}
	
	
	private void fillLetzteZuege(){
		if (zug != 1) {
			int meinLetzterZug = this.letzterZug();
			int derLetzteZugDesGegners = getHdg().letzterZug(getOppositeBotNumber());
			MyLogger.log("meinLetzterZug " + zug + ": " +  meinLetzterZug);
			MyLogger.log("derLetzteZugDesGegners " + zug + ": " +  derLetzteZugDesGegners);
			letzteZuege[0] = meinLetzterZug;
			letzteZuege[1] = derLetzteZugDesGegners;
		}else {
			MyLogger.log("Erste Runde");
		}
	}

	private int getOppositeBotNumber(){
		if (this.getNummer() == 0) {
			//MyLogger.log(this.getClass().getSimpleName() + " with this number "+ this.getNummer());
			//MyLogger.log("EBN: " + 1);
			return 1;
		} else {
			//MyLogger.log(this.getClass().getSimpleName() + " with this number "+ this.getNummer());
			//MyLogger.log("EBN: " + 0);
			return 0;
		}
	}
	private void intiCardValues() {
		kartenBewertung.put(-5, (double) 11);
		kartenBewertung.put(-4, (double) 10);
		kartenBewertung.put(-3, (double) 9);
		kartenBewertung.put(-2, (double) 8);
		kartenBewertung.put(-1, (double) 7);
		kartenBewertung.put( 1, (double) 1);
		kartenBewertung.put( 2, (double) 2);
		kartenBewertung.put( 3, (double) 3);
		kartenBewertung.put( 4, (double) 4);
		kartenBewertung.put( 5, (double) 5);
		kartenBewertung.put( 6, (double) 6);
		kartenBewertung.put( 7, (double) 12);
		kartenBewertung.put( 8, (double) 13);
		kartenBewertung.put( 9, (double) 14);
		kartenBewertung.put(10, (double) 15);
	}
}
