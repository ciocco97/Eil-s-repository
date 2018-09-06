/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Utils.Coordinate;
import java.util.Random;

/**
 *
 * @author Alessandro
 */
public class Archer extends Charapter{
    
    Random rand;
    
    public Archer(int owner, int id, Coordinate coord) {
        // l'arcere ha basse statistiche, ma attacca da lontano suvvia
        super(owner, id, coord);
        rand = new Random();
        this.strength = rand.nextInt(5) + 20;
        this.defence = rand.nextInt(10) + 35;
        this.health = 150;
        this.speed = 2;
        
    }
     @Override
    public int getType()
    {
        //ritorna il fatto di essere un arcere
        return 2;
    }
    
    public Arrow throwArrow(int direction)
    {
        int power = this.strength - rand.nextInt(8);
        Arrow arrow = new Arrow (direction, power, owner,  new Coordinate(coordinate.getX(), coordinate.getY()));
        return arrow;
    }

    
    
   
    
}
