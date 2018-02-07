
package models;

/**
 *
 * @author Alessandro
 * Classe molto astratta per rappresentare un entità coordinalizzabile nello spazio bidimensionale
 */
public class ClasseCoordinalizzabile{
    private int x;
    private int y;
/**
 * 
 * @param x Intero che indica la coordinata X
 * @param y Intero che indica la coordinata Y
 */
    public ClasseCoordinalizzabile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ClasseCoordinalizzabile() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public double getDistanceFromOrigin()
    {
        return Math.sqrt(x*x + y*y);
    }
    
}
