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
    protected int owner, id, health, strength, defence, speed;
    protected Coordinate coordinate;

    
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
    }
    
    public void attack(Charapter charapter)
    {
        int damage = (this.strength * 10) / (charapter.defence * 2);
        charapter.health -= damage;
    }
    public int getType()
    {
        //ritorna il tipo di personaggio, 0 è personaggio generico, non istanziato
        return 0;
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
    
    
}
