/**
 * 
 */
package main;

import version5.bots.*;
import version5_1.mainGame.*;

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
		
		IntelligentererGeier bot1 = new IntelligentererGeier();
		IntelligentererGeier bot2 = new IntelligentererGeier();
		Mensch bot3 = new Mensch();
		Geier bot4 = new Geier();
		NoahsBot aBotToRuleThemAll = new NoahsBot();
		
		HolsDerGeier spiel = new HolsDerGeier();
		spiel.initBots(aBotToRuleThemAll, bot1);
		try {
			spiel.ganzesSpiel();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Schummler");
		}
	}

}
