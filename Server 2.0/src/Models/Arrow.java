/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Utils.Coordinate;

/**
 *
 * @author Alessandro
 */
public class Arrow {
    private int direction;
    private int power;
    private int owner;
    private Coordinate coordinate;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Arrow(int direction, int power, int owner, Coordinate coordinate) {
        this.direction = direction;
        this.power = power;
        this.coordinate = coordinate;
        this.owner = owner;
    }

    public Arrow() {
    }
    public void tick()
    {
        switch (direction){
            case 0: {coordinate.setY(coordinate.getY()-1);}
            case 1: {coordinate.setY(coordinate.getY()-1); coordinate.setX(coordinate.getX()+1);}
            case 2: {coordinate.setX(coordinate.getX()+1);}
            case 3: {coordinate.setY(coordinate.getY()+1); coordinate.setX(coordinate.getX()+1);}
            case 4: {coordinate.setY(coordinate.getY()+1);}
            case 5: {coordinate.setY(coordinate.getY()+1); coordinate.setX(coordinate.getX()-1);}
            case 6: {coordinate.setY(coordinate.getX()-1);}
            case 7: {coordinate.setY(coordinate.getY()-1); coordinate.setX(coordinate.getX()-1);}
        }
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
    
}
