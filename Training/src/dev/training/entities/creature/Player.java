package dev.training.entities.creature;

import dev.training.Game;
import dev.training.Handeler;
import dev.training.gfx.Assets;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Classe che rappresenta l'avatar del giocatore
 */
public class Player extends Creature{
    
    /**
     * La variabile game ci consente l'accesso al KeyManager e a tutto ciò che 
     * riguarda l'input
     */
    private final int DEFAULT_PLAYER_SPEED = 20;
    
    /**
     * Costruttore
     * @param game
     * @param x
     * @param y 
     */
    public Player(Handeler handeler, float x, float y) {
        super(handeler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        speed = DEFAULT_PLAYER_SPEED;
        
        bounds.x = (int) (249 * 0.29);
        bounds.y = (int) (72 * 0.29);
        bounds.width = (int) (493 * 0.29);
        bounds.height = (int) (402 * 0.29);
    }

    /**
     * Modificare le variabili contenute nell'istanza del Player
     */
    @Override
    public void update() {
        getInput();
        move();
        handeler.getGameCamera().centerOnEntity(this);
    }
    
    /**
     * Metodo che sfrutta l'input per poi muovere il Player rispetto 
     * all'origine.
     */
    private void getInput() {
        xMove = 0;
        yMove = 0;
        
        if(handeler.getKeyManager().up)
            yMove = -speed;
        if(handeler.getKeyManager().down)
            yMove = speed;
        if(handeler.getKeyManager().left)
            xMove = -speed;
        if(handeler.getKeyManager().right)
            xMove = speed;
        
    }

    /**
     * Disegna tramite un oggetto Graphics il player su Canvas del display.
     * Si "disegna" sullo schermo
     * La funzione drawImage richiede 6 parametri:
     * Il primo è la BufferedImage da "disegnare"
     * Il secondo e il terzo rappresentano le coordinate da cui partire per 
     * disegnare la BufferedImage
     * Il terzo e il quarto servono per il resize della BufferedImage
     * L'ultimo non viene utilizzato
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, (int) (x - handeler.getGameCamera().getxOffset()), 
                (int) (y - handeler.getGameCamera().getyOffset()), width, height, null);
        
        g.setColor(Color.red);
        /*g.fillRect((int) (x + bounds.x - handeler.getGameCamera().getxOffset()), 
                (int) (y + bounds.y - handeler.getGameCamera().getyOffset()), 
                bounds.width, bounds.height);*/
            
    }
    
}
