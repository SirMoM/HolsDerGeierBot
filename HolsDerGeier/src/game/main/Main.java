/**
 * 
 */
package game.main;

import game.bots.*;
import game.mainGame.*;

/**
 * @author Noah Ruben
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int gewonnen = 0;
		
		IntelligentererGeier	bot1 	= new IntelligentererGeier();
		RuleThemAll 			ruleThem= new RuleThemAll();
		Mensch					mensch	= new Mensch();
		
		HolsDerGeier spiel = new HolsDerGeier();
		spiel.initBots(ruleThem, bot1);
		try {
			for (int i = 0; i < 1000; i++) {
				spiel.ganzesSpiel();
				if(spiel.punktstaende[0] > spiel.punktstaende[1]) {
					gewonnen++;
				}
				System.out.println("Von " + i + " Spielen habe ich " + gewonnen + "gewonnen");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
