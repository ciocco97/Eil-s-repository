/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Alessandro
 */
public class Move {
    private int ID;
    private int owner;
    private int type;
    private ArrayList<Coordinate> steps;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Coordinate> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Coordinate> steps) {
        this.steps = steps;
    }

    public Move(int owner, int ID, int type, ArrayList<Coordinate> steps) {
        this.ID = ID;
        this.steps = steps;
        this.owner = owner;
        this.type = type;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    
    
    
}
