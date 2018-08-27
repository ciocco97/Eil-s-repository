/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author aless
 */
public class Charapter {
    protected int id, health, strength, defence;

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefence() {
        return defence;
    }
    public Charapter(int id)
    {
        this.id = id;
    }
    
    public void attack(Charapter charapter)
    {
        int damage = (this.strength * 10) / charapter.defence;
        charapter.health -= damage;
    }
    public int getType()
    {
        //ritorna il tipo di personaggio, 0 è personaggio generico, non istanziato
        return 0;
    }
    
    
}
