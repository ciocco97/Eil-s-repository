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
    protected int owner, id, health, strength, defence, speed, direction;
    protected Coordinate coordinate;
    protected boolean shooting;

    
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
    public Charapter(int owner, int id, Coordinate coord)
    {
        this.id = id;
        this.coordinate = coord;
        this.owner = owner;
        this.shooting = false;
    }
    
    public void attack(Charapter charapter)
    {
        int damage = (this.strength * 10) / (charapter.defence * 2);
        charapter.health -= damage;
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
        if (arrow.getOwner() != this.owner)
            this.health -= (arrow.getPower() / this.defence);
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
    
   
    
    
    
}
