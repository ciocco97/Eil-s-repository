
package models;

/**
 * 
 * @author Alessandro
 */
public class Character extends CoordEntity{
    private int health;
    private int power;
    private int defense;
    private int status;

    public Character()
    {
        this.health=0;
        this.power=0;
        this.defense=0;
    }

    public void initParametri()
    {}

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
    
    
}
