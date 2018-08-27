/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Random;

/**
 *
 * @author Alessandro
 */
public class Gueriero extends Charapter{
    
    public Gueriero(int id) {
        // l'arcere ha basse statistiche, ma attacca da lontano suvvia
        super(id);
        Random rand = new Random();
        this.strength = rand.nextInt(20) + 40;
        this.defence = rand.nextInt(10) + 35;
        this.health = 100;
    }
    @Override
    public int getType()
    {
        //ritorna il fatto di essere un gueriero
        return 1;
    }
    
}
