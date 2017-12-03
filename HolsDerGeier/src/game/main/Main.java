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
		
		MyLogger.createLoggingFiles();
		
		IntelligentererGeier	bot1 = new IntelligentererGeier();
		IntelligentererGeier	bot2 = new IntelligentererGeier();
		Mensch 					bot3 = new Mensch();
		Geier 					bot4 = new Geier();
		NoahsBot 				aBotToRuleThemAll = new NoahsBot();
		TestBot 				xBot = new TestBot();
		RuleThemAll 			ruleThem = new RuleThemAll();
		
		HolsDerGeier spiel = new HolsDerGeier();
		spiel.initBots(ruleThem, bot1);
		try {
			spiel.ganzesSpiel();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Schummler");
		}
	}

}
