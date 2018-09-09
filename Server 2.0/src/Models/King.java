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
public class King extends Charapter{
    
    Random rand;
    
    public King(int owner, int id, Coordinate coord) {
        super(owner, id, coord);
        this.health = 500;
        rand = new Random();
        this.strength = 0;
        this.defence = rand.nextInt(10) + 35;
    }
    
    @Override
    public int getType()
    {
        return 3;
    }
    
}
