/**
 * 
 */
package game.bots;

import java.util.ArrayList;

import game.mainGame.HolsDerGeier;
import game.mainGame.HolsDerGeierSpieler;
import game.mainGame.MyLogger;

/**
 * @author Sir.MoM
 *
 */
public class RuleThemAll extends HolsDerGeierSpieler {

	private ArrayList<Integer> nochNichtGespielt=new ArrayList<Integer>();
	private int[] letzteZuege = new int[2];
	private HolsDerGeier HolsDerGeier = this.getHdg();

	/* (non-Javadoc)
	 * @see game.mainGame.HolsDerGeierSpieler#reset()
	 */
	@Override
	public void reset() {
		nochNichtGespielt.clear();
		for (int i=1;i<=15;i++)            
			nochNichtGespielt.add(i);

	}

	/* (non-Javadoc)
	 * @see game.mainGame.HolsDerGeierSpieler#gibKarte(int)
	 */
	@Override
	public int gibKarte(int naechsteKarte) {
		fillLetzteZuege();
		logInTabelle(naechsteKarte);
		return getZufallskarte();
	}

	private void logInTabelle(int naechsteKarte) {
		MyLogger.logTable(naechsteKarte, letzteZuege[0], letzteZuege[1], getWhoWon());

	}

	private String getWhoWon() {
		final String IWON = "GEWONNEN";
		final String ILOSE = "VERLOREN";
		final String TIED = "UNENTSCHIEDEN";
		final String NOTKNOWN = "GEWINNER KONNTE NICHT ERMITTELT WERDEN";
		
		if(letzteZuege[0] != letzteZuege[1]) {
			if (letzteZuege[0] > letzteZuege[1]) {
				return IWON;
			}else if (letzteZuege[0] < letzteZuege[1]) {
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
	
	
	private void fillLetzteZuege() {
		int meinLetzterZug = this.letzterZug();
		int derLetzteZugDesGegners = HolsDerGeier.letzterZug(getOppositeBotNumber());
		MyLogger.log("meinLetzterZug: " +  meinLetzterZug);
		MyLogger.log("derLetzteZugDesGegners: " +  derLetzteZugDesGegners);
		if(meinLetzterZug != -99 || derLetzteZugDesGegners != -99 ) {
			letzteZuege[0] = meinLetzterZug;
			letzteZuege[1] = derLetzteZugDesGegners;
		}else {
			MyLogger.log("Erste Runde");
		}
	}

	private int getOppositeBotNumber() throws NullPointerException{
		int temp = this.getNummer();
		MyLogger.log(String.valueOf(temp));
		switch (temp) {
		case 0:
			MyLogger.log("EBN: " + temp);
			return 1;
		case 1:
			MyLogger.log("EBN: " + temp);
			return 0;
//		default:
//			MyLogger.log("Die Nummer des Gegener-Bots konnte nicht bestimmt werden");
//			throw new NullPointerException();
		}
		return 0;
	}
}
