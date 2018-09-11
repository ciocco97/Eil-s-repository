/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;

/**
 *
 * @author Alessandro
 */
public class Attack {
    private ArrayList steps;
    private int idAttacker, idDefender;
    private int owner;

    public Attack(ArrayList steps, int idAttacker, int idDefender, int owner) {
        this.steps = steps;
        this.idAttacker = idAttacker;
        this.idDefender = idDefender;
        this.owner = owner;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public ArrayList getSteps() {
        return steps;
    }

    public void setSteps(ArrayList steps) {
        this.steps = steps;
    }

    public int getIdAttacker() {
        return idAttacker;
    }

    public void setIdAttacker(int idAttacker) {
        this.idAttacker = idAttacker;
    }

    public int getIdDefender() {
        return idDefender;
    }

    public void setIdDefender(int idDefender) {
        this.idDefender = idDefender;
    }
    
    
}
