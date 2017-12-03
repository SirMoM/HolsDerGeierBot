/**
 * 
 */
package game.bots;

import java.util.ArrayList;

import game.mainGame.HolsDerGeierSpieler;

/**
 * @author Noah Ruben
 *
 */
public class TestBot extends HolsDerGeierSpieler {

	private ArrayList<Integer> nochNichtGespielt=new ArrayList<Integer>();

	/**
	 * 
	 */
	public TestBot() {
		super();
	}

	/* (non-Javadoc)
	 * @see version5_1.mainGame.HolsDerGeierSpieler#reset()
	 */
	@Override
	public void reset() {
		nochNichtGespielt.clear();
		for (int i=1;i<=15;i++)            
			nochNichtGespielt.add(i); 
	}

	/* (non-Javadoc)
	 * @see version5_1.mainGame.HolsDerGeierSpieler#gibKarte(int)
	 */
	@Override
	public int gibKarte(int naechsteKarte) {
		
		System.out.println("mein bot: " + getHdg().letzterZug(this.getNummer()));
		System.out.println("1: " + getHdg().letzterZug(1));
		
		int nochVorhanden= nochNichtGespielt.size();            
		int index=(int) (Math.random()*nochVorhanden);
		int ret= nochNichtGespielt.remove(index);
		return ret;
	}

}
