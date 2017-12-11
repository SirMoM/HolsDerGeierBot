package game.mainGame;


public abstract class HolsDerGeierSpieler {

    private int nummer;
    private HolsDerGeier hdg;

    
    public int getNummer() {
    	return this.nummer;
    }
    public HolsDerGeier getHdg()  {
        return hdg;
    }

    public int letzterZug() {
        return hdg.letzterZug(this.nummer);
    }
    
    public void register(HolsDerGeier hdg, int nummer) {
        this.hdg=hdg;
        this.nummer=nummer;
    }
    
    public abstract void reset();    
    public abstract int gibKarte(int naechsteKarte);
        
}
