package version5.bots;

import java.util.ArrayList;
import java.util.HashMap;

import version5_1.mainGame.HolsDerGeierSpieler;

/**
 * @author boss
 * 
 */
public class NoahsBot extends HolsDerGeierSpieler {
	ArrayList<Integer> meineKarten = new ArrayList<Integer>();
	ArrayList<Integer> dieKartenDesGegners = new ArrayList<Integer>();
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

	public int[] getDieHöchstenKarten() {
		return dieHoechstenKarten;
	}

	public void setDieHöchstenKarten() {
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
		int letzteKarteDesgegners = getHdg().letzterZug(getEnemyBotNumber());
		int letzteKarteVonMir = getHdg().letzterZug(getMyBotNumber());
		
		System.out.println("		Die letzte Karte des Gegners " + letzteKarteDesgegners);
		System.out.println("		Die letzte Karte von  Mir " + letzteKarteVonMir);
		
		
		if (meineKarten.isEmpty() == true) {
			fuellMeineKarten();
		} else if (dieKartenDesGegners.isEmpty() == true) {
			fuellDieKartenDesGegners();
		} else {
			setDieHöchstenKarten();
			setDieNiedrigstenKarten();
		}
		System.out.println("		Meine gespielte Karte: " + getDieHöchstenKarten()[0]);
		dieZuSpielendeKarte = getDieHöchstenKarten()[0];
		meineKarten.remove(dieZuSpielendeKarte - 1);
		return (int) dieZuSpielendeKarte;
	}

	private int getMyBotNumber() {
		
		return getHdg().letzterZug(0);
	}
	
	private int getEnemyBotNumber() {
		return getHdg().letzterZug(1);
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

	private void logZug() {
		
	}
	
//	private void whoWon(){
//		if (zuege[0] != zuege[1]) {
//			if (punkte > 0)
//				if (zuege[0] > zuege[1])
//					punktstaende[0] = punktstaende[0] + punkte;
//				else
//					punktstaende[1] = punktstaende[1] + punkte;
//			else if (zuege[0] < zuege[1])
//				punktstaende[0] = punktstaende[0] + punkte;
//			else
//				punktstaende[1] = punktstaende[1] + punkte;
//			punkte = 0;
//		} else
//			System.out.println("Unentschieden - Punkte wandern in die naechste Runde");
//			System.out.println("Spielstand: " + punktstaende[0] + " : "	+ punktstaende[1]);
//			System.out.println("Mein Bot: " + punktstaende[0]);
//			System.out.println("Mein Gegner: " + punktstaende[1]);
//	}
	
}
