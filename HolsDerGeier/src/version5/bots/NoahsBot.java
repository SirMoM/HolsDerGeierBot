package version5.bots;

import java.util.ArrayList;
import java.util.HashMap;

import version5_1.mainGame.HolsDerGeierSpieler;
import version5_1.mainGame.MyLogger;

/**
 * @author boss
 * 
 */
public class NoahsBot extends HolsDerGeierSpieler {
	ArrayList<Integer> meineKarten = new ArrayList<Integer>();
	ArrayList<Integer> dieKartenDesGegners = new ArrayList<Integer>();
	
	int letzteKarteDesgegners; 
	
	int letzteKarteVonMir;

	/**
	 * < <b>K</b>ey, <b>V</b>alue>
	 * <p>
	 * @param K = Karte
	 * @param V = Mein Wert
	 */
	HashMap<Integer, Double> kartenBewertung = new HashMap<Integer, Double>();
	
	/**
	 * Mit dem Index 0 = meine hoeste Karte Mit <p>
	 * Mit dem Index 1 = die hoeste Karte des Gegners
	 */
	int[] dieHoechstenKarten = new int[2];

	/**
	 * Mit dem Index 0 = meine niedrigste Karte Mit <p>
	 * Mit dem Index 1 = die niedrigste Karte des Gegners
	 */
	int[] dieNiedrigstenKarten = new int[2];

	public NoahsBot() {
		super();
	}

	public int[] getDieHoechstenKarten() {
		return dieHoechstenKarten;
	}

	public void setDieHoechstenKarten() {
		System.out.println("		######################");
		int dieGroesstekarteGegner = 0;
		int meineGroesstekarte = 0;
		for (int eineKarte : dieKartenDesGegners) {
			if(eineKarte > dieGroesstekarteGegner){
				dieGroesstekarteGegner = eineKarte;
			}
		}
		
		this.dieHoechstenKarten[1] = dieGroesstekarteGegner;
		System.out.println("		Die höchste Karte meines Gegners: " + dieGroesstekarteGegner);
		
		for (int eineKarte : meineKarten) {
			if(eineKarte > meineGroesstekarte){
				meineGroesstekarte = eineKarte;
			}
		}
		
		this.dieHoechstenKarten[0] = meineGroesstekarte;
		System.out.println("		Meine höchste Karte: " + meineGroesstekarte);
		System.out.println("		######################");
	}

	public int[] getDieNiedrigstenKarten() {
		return dieNiedrigstenKarten;
	}

	public void setDieNiedrigstenKarten() {
		System.out.println("		######################");
		int dieNiedrigstekarteGegner = 15;
		int meineNiedrigestekarte = 15;
		
		for (int eineKarte : dieKartenDesGegners) {
			if(eineKarte < dieNiedrigstekarteGegner){
				dieNiedrigstekarteGegner = eineKarte;
			}
		}
		
		this.dieNiedrigstenKarten[1] = dieNiedrigstekarteGegner;
		System.out.println("		Die niedrigste Karte meines Gegners: " + dieNiedrigstekarteGegner);
		
		for (int eineKarte : meineKarten) {
			if(eineKarte < meineNiedrigestekarte){
				meineNiedrigestekarte = eineKarte;
			}
		}
		
		this.dieNiedrigstenKarten[0] = meineNiedrigestekarte;
		System.out.println("		Meine niedrigste Karte: " + meineNiedrigestekarte);
		System.out.println("		######################");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see version5.bots.HolsDerGeierSpieler#gibKarte(int)
	 */
	@Override
	public int gibKarte(int naechsteKarte) {
		int dieZuSpielendeKarte;
		int zuEvaluierendeKarte = naechsteKarte;
		
		logZug(naechsteKarte, letzteKarteVonMir, letzteKarteDesgegners);
		
//		System.out.println("		Die letzte Karte des Gegners " + letzteKarteDesgegners);
//		System.out.println("		Die letzte Karte von  Mir " + letzteKarteVonMir);
		
		
		if (meineKarten.isEmpty() == true) {
			fuellMeineKarten();
		} else if (dieKartenDesGegners.isEmpty() == true) {
			fuellDieKartenDesGegners();
		} else {
			setDieHoechstenKarten();
			setDieNiedrigstenKarten();
		}
		System.out.println("		Meine gespielte Karte: " + getDieHoechstenKarten()[0]);
		dieZuSpielendeKarte = getDieHoechstenKarten()[0];
		meineKarten.remove(dieZuSpielendeKarte - 1);
		return (int) dieZuSpielendeKarte;
	}

	private void fuellDieKartenDesGegners() {
		for (int i = 1; i < 16; i++) {
			meineKarten.add(i);
			System.out.println("		Füll die Karten G " + i);
		}
	}

	private void fuellMeineKarten() {
		for (int i = 1; i < 16; i++) {
			meineKarten.add(i);
			System.out.println("		Füll die Karten I " +i);
		}
	}

	@Override
	public void reset() {
		
		intiCardValues();
		
		if(meineKarten.isEmpty() && dieKartenDesGegners.isEmpty()){
			for(int i = 1; i < 16; i++){
				meineKarten.add(i);
				dieKartenDesGegners.add(i);
			}
		}else{
			meineKarten.clear();
			dieKartenDesGegners.clear();
			this.reset();
		}
		dieHoechstenKarten = new int[2];
		dieNiedrigstenKarten = new int[2];
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

	private void logZug(int punkteKarte, int meineKarte, int gergnerKarte) {
		MyLogger.logTable(punkteKarte, meineKarte, gergnerKarte, whoWon(punkteKarte) );
	}
	
	private void getLetzteKarten() {
		letzteKarteVonMir = getHdg().letzterZug(this.getNummer());
		
		if(getHdg().letzterZug(this.getNummer()) == 0) {
			letzteKarteDesgegners = getHdg().letzterZug(1);
		}else {
			letzteKarteDesgegners = getHdg().letzterZug(0);
		}
	}
	
	private String whoWon(int punkteKarte){
		final String winEnemy = "Enemy"; 
		final String winMe = "MyBot";
		final String tied = "Nobody";
		if (letzteKarteDesgegners != letzteKarteVonMir) {
			if (punkteKarte > 0) {
				if ( letzteKarteVonMir > letzteKarteDesgegners) {
					// I WON X POINTS
					return winMe;
				}else {
					// ENEMY WON
					return winEnemy;
 				}
			}else if ( letzteKarteVonMir < letzteKarteDesgegners) {
				// I WON X POINTS
				return winMe;
			}else {
				// ENEMY WON
				return winEnemy;
			}
		}else{
			//NOBODY WON
			return tied;
		}
	}
	
}
