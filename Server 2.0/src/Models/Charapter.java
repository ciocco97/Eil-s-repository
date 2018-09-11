/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Utils.Coordinate;

/**
 *
 * @author aless
 */
public class Charapter {
    
    private static final String KING_ID = "3";
    private static final String ARCHER_ID = "2";
    private static final String SOLDIER_ID = "1";
    
    protected float health, strength, defence;
    protected int owner, direction, speed, id;
    protected Coordinate coordinate;
    protected boolean shooting;

    
    public int getId() {
        return id;
    }

    public float getHealth() {
        return health;
    }

    public float getStrength() {
        return strength;
    }

    public float getDefence() {
        return defence;
    }
    public Charapter(int owner, int id, Coordinate coord)
    {
        this.id = id;
        this.coordinate = coord;
        this.owner = owner;
        this.shooting = false;
    }
    
    public boolean attack(Charapter charapter)
    {
        if (charapter.getType().equals(SOLDIER_ID))
            charapter.shooting = true;
        float damage = (this.strength * 10) / (charapter.defence);
        charapter.health -= damage;
        if (charapter.health < 0)
            return true;
        return false;
    }
    public String getType()
    {
        //ritorna il tipo di personaggio, 0 è personaggio generico, non istanziato
        return "0";
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coord) {
        this.coordinate = coord;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getSpeed() {
        return speed;
    }

    /**
     *
     * @param direction
     * @return
     */
    public Arrow throwArrow(int direction)
    {
        return null;
    }
    public boolean hit(Arrow arrow)
    {
        this.health -= (arrow.getPower() * 10)/(this.defence * 2);
        if (this.health < 0)
            return true;
        return false;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public boolean isNear(Charapter charap)
    {
        int x = this.coordinate.getX();
        int y = this.coordinate.getY();
        int X = charap.coordinate.getX();
        int Y = charap.coordinate.getY();
        
        if (x == X && y==Y+1)
            return true;
        if (x == X && y==Y-1)
            return true;
        if (x == X+1 && y==Y-1)
            return true;
        if (x == X+1 && y==Y)
            return true;
        if (x == X+1 && y==Y+1)
            return true;
        if (x == X-1 && y==Y-1)
            return true;
        if (x == X-1 && y==Y+1)
            return true;
        if (x == X-1 && y==Y)
            return true;
        return false;
    }
    
   
    
    
    
}
