
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
 * Costruttore della classe che inizializza le coordinate passate come parametro
 * @param x Intero che indica la coordinata X
 * @param y Intero che indica la coordinata Y
 */
    public ClasseCoordinalizzabile(int x, int y) {
        this.x = x;
        this.y = y;
    }
/**
 * Costruttore vuoto per inizializzare le coordinate nell'origine degli assi
 */
    public ClasseCoordinalizzabile() {
        this.x = 0;
        this.y = 0;
    }
/**
 * 
 * @return Intero che rappresenta la coordinata X
 */
    public int getX() {
        return x;
    }
/**
 * @param x Intero che rappresenta la nuova coordinata X 
 */
    public void setX(int x) {
        this.x = x;
    }
/**
 * 
 * @return Intero che rappresenta la coordinata Y
 */
    public int getY() {
        return y;
    }
/**
 * @param y Intero che rappresenta la nuova coordinata Y
 */
    public void setY(int y) {
        this.y = y;
    }
/**
 * Metodo che ritorna la distanza dell'entità dall'origine degli assi
 * @return Double che rappresenta la distanza
 */
    public double getDistanceFromOrigin()
    {
        return Math.sqrt(x*x + y*y);
    }
    
}
