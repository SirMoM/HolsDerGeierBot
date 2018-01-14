/**
 * 
 */
package game.bots;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	private HashMap<Integer, Double> meineKartenBewertung = new HashMap<Integer, Double>();
	private HashMap<Integer, Double> dieKartenBewertungDesGegners = new HashMap<Integer, Double>();

	/* (non-Javadoc)
	 * @see game.mainGame.HolsDerGeierSpieler#reset()
	 */
	@Override
	public void reset() {
		meineKartenBewertung.clear();
		nochNichtGespielt.clear();
		meineKarten.clear();
		dieKartenDesGegners.clear();
		diePunktekarten.clear();
		dieKartenBewertungDesGegners.clear();

		intiCardValues();

		//init nochNichtGespielt, meineKarten, dieKartenDesGegners
		for (int i=1;i<=15;i++) {           
			nochNichtGespielt.add(i);
			meineKarten.add(i);
			dieKartenDesGegners.add(i);
		}

		//init diePunktekarten
		for (int i = -5; i < 0; i++) {
			diePunktekarten.add(i);
		}
		for (int i = 1; i < 11; i++) {
			diePunktekarten.add(i);
			
		setDieKartenBewertungDesGegners();
		}
	}

	/* (non-Javadoc)
	 * @see game.mainGame.HolsDerGeierSpieler#gibKarte(int)
	 */
	@Override
	public int gibKarte(int pointsThisRound) {
		zug++;		
		if(zug > 1) {
			fillLetzteZuege();
			logInTabelle(letztePunktekarte); 
			deleteLastCards();
		}

		if(zug == 15) {
			MyLogger.logTable(getVerbleibendePunktekarte(), getVerbleibendeMeineKarte(), getVerbleibendeGegnerKarte(), getWhoWon(getVerbleibendeMeineKarte(), getVerbleibendeGegnerKarte()));
		}
		letztePunktekarte = pointsThisRound;
		MyLogger.log("######" + (zug - 1) + "######");



		return useCardValueToCalculateTheCardToPlay(pointsThisRound);
	}

	private int useCardValueToCalculateTheCardToPlay(int cardToEvaluate) {
		Double dieBewertungDesGegners = dieKartenBewertungDesGegners.get(cardToEvaluate);
		Double meineBewertungDieserKarte = meineKartenBewertung.get(cardToEvaluate);
		System.out.println("dieBewertungDesGegners: " + dieBewertungDesGegners + "\t meineBewertungDieserKarte: " + meineBewertungDieserKarte);
		
		if(getWhoWon(letzteZuege[0], letzteZuege[1]) == "3") {
			int resultingPoints = cardToEvaluate + letztePunktekarte;
			
			if(resultingPoints > 15) {
				return getHighestCardLeft();
			}else if(resultingPoints < -5) {
				return getLowestCardLeft();
			}else {
				return meineKartenBewertung.get(cardToEvaluate).intValue();
			}
		}	
		
		return meineKartenBewertung.get(cardToEvaluate).intValue();
	}

	private int getLowestCardLeft() {
		return meineKarten.get(0);
	}

	private int getHighestCardLeft() {
		return meineKarten.get(meineKarten.size() - 1);
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
		final String IWON = "1";
		final String ILOSE = "0";
		final String TIED = "3";
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
			return 1;
		} else {
			return 0;
		}
	}
	private void intiCardValues() {
		meineKartenBewertung.put(-5, (double) 11);
		meineKartenBewertung.put(-4, (double) 10);
		meineKartenBewertung.put(-3, (double) 9);
		meineKartenBewertung.put(-2, (double) 8);
		meineKartenBewertung.put(-1, (double) 7);
		meineKartenBewertung.put( 1, (double) 1);
		meineKartenBewertung.put( 2, (double) 2);
		meineKartenBewertung.put( 3, (double) 3);
		meineKartenBewertung.put( 4, (double) 4);
		meineKartenBewertung.put( 5, (double) 5);
		meineKartenBewertung.put( 6, (double) 6);
		meineKartenBewertung.put( 7, (double) 12);
		meineKartenBewertung.put( 8, (double) 13);
		meineKartenBewertung.put( 9, (double) 14);
		meineKartenBewertung.put(10, (double) 15);
	}

	public HashMap<Integer, Double> getDieKartenBewertungDesGegners() {
		return dieKartenBewertungDesGegners;
	}

	public void setDieKartenBewertungDesGegners() {
		String stringToParse;
		try {
			BufferedReader readFromCsv = new BufferedReader(new FileReader(MyLogger.GESPIELTEZUEGE));
			
			while((stringToParse = readFromCsv.readLine()) != null) {
				MyLogger.log("Line read:" + stringToParse);
				String[] zeileSplit = stringToParse.split(";");
				int punktekarte =  Integer.valueOf(zeileSplit[0].trim());
				int meineKarte = Integer.valueOf(zeileSplit[1].trim());
				int gegnerKarte = Integer.valueOf(zeileSplit[2].trim());
				int winOrLoss = Integer.valueOf(zeileSplit[3].trim());
				
				if(dieKartenBewertungDesGegners.get(punktekarte) == null) {
					dieKartenBewertungDesGegners.put(punktekarte, Double.valueOf(gegnerKarte));
				}else {
					Double altePunktekartenBewertung = dieKartenBewertungDesGegners.get(punktekarte);
					Double neuePunktekartenBewertung = (altePunktekartenBewertung + gegnerKarte) / 2; 
					dieKartenBewertungDesGegners.put(punktekarte, neuePunktekartenBewertung);
				}
			}
		} catch (Exception e) {
			MyLogger.log("setDieKartenBewertungDesGegners", e);
			e.printStackTrace();
		}
		
		//dieKartenBewertungDesGegners.put()
	}
}
