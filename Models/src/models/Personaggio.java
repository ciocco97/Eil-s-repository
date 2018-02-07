
package models;

/**
 *Classe che rappresenta un personaggio generico coordinalizzabile
 * @author Alessandro
 */
public class Personaggio extends ClasseCoordinalizzabile{
    private int vita;
    private int attacco;
    private int difesa;

    public Personaggio(int vita, int attacco, int difesa) {
        this.vita = vita;
        this.attacco = attacco;
        this.difesa = difesa;
    }
    
    public Personaggio()
    {
        this.vita=0;
        this.attacco=0;
        this.difesa=0;
    }

    public void initParametri()
    {}

    public int getVita() {
        return vita;
    }

    public void setVita(int vita) {
        this.vita = vita;
    }

    public int getAttacco() {
        return attacco;
    }

    public void setAttacco(int attacco) {
        this.attacco = attacco;
    }

    public int getDifesa() {
        return difesa;
    }

    public void setDifesa(int difesa) {
        this.difesa = difesa;
    }
    
    
}
