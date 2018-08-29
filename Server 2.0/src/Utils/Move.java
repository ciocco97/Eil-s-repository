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

    public Move(int owner, int ID, ArrayList<Coordinate> steps) {
        this.ID = ID;
        this.steps = steps;
        this.owner = owner;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
    
    
}
