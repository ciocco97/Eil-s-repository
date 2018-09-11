/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Utils.*;
import java.util.Random;

/**
 *
 * @author Alessandro
 */
public class Soldier extends Charapter{
    
    private static final String SOLDIER_ID = "1";
    
    public Soldier(int owner, int id,Coordinate coord) {
        // l'arcere ha basse statistiche, ma attacca da lontano suvvia
        super(owner, id, coord);
        Random rand = new Random();
        this.strength = rand.nextInt(20) + 40;
        this.defence = rand.nextInt(10) + 35;
        this.health = Utils.SOLDIER_HEALTH;
        this.speed = 1;
    }
    @Override
    public String getType()
    {
        //ritorna il fatto di essere un gueriero
        return SOLDIER_ID;
    }
    
}
