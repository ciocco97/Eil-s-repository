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
    private float power;
    private int owner;
    private Coordinate coordinate;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public float getPower() {
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

    public Arrow(int direction, float power, int owner, Coordinate coordinate) {
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
            case 1: coordinate.setY(coordinate.getY()-1);break;
            case 2: coordinate.setY(coordinate.getY()-1); coordinate.setX(coordinate.getX()+1);break;
            case 3: coordinate.setX(coordinate.getX()+1);break;
            case 4: coordinate.setY(coordinate.getY()+1); coordinate.setX(coordinate.getX()+1);break;
            case 5: coordinate.setY(coordinate.getY()+1);break;
            case 6: coordinate.setY(coordinate.getY()+1); coordinate.setX(coordinate.getX()-1);break;
            case 7: coordinate.setX(coordinate.getX()-1);break;
            case 8: coordinate.setY(coordinate.getY()-1); coordinate.setX(coordinate.getX()-1);break;
        }
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
    
}
