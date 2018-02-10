package dev.training.entities.creature;

import dev.training.Game;
import dev.training.Handeler;
import dev.training.gfx.Assets;
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
    boolean p, pu, pd, pl, pr;
    
    /**
     * Costruttore
     * @param game
     * @param x
     * @param y 
     */
    public Player(Handeler handeler, float x, float y) {
        super(handeler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        speed = DEFAULT_PLAYER_SPEED;
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
        
        if(handeler.getKeyManager().up)
            pu = true;
        else
            pu = false;
        if(handeler.getKeyManager().down)
            pd = true;
        else
            pd = false;
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
        if(!pd && !pu)
            g.drawImage(Assets.player, (int) (x - handeler.getGameCamera().getxOffset()), 
                    (int) (y - handeler.getGameCamera().getyOffset()), width, height, null);
        else if(pd)
            g.drawImage(Assets.pd, (int) (x - handeler.getGameCamera().getxOffset()), 
                    (int) (y - handeler.getGameCamera().getyOffset()), width, height, null);
        else if(pu)
            g.drawImage(Assets.pu, (int) (x - handeler.getGameCamera().getxOffset()), 
                    (int) (y - handeler.getGameCamera().getyOffset()), width, height, null);
            
    }
    
}
