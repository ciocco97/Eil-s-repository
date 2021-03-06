package dev.training.entities.creature;

import dev.training.Game;
import dev.training.gfx.Assets;
import java.awt.Graphics;

public class Player extends Creature{
    
    /**
     * La variabile game ci consente l'accesso al KeyManager e a tutto ciò che 
     * riguarda l'input
     */
    private final int DEFAULT_PLAYER_SPEED = 20;
    
    public Player(Game game, float x, float y) {
        super(game, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        speed = DEFAULT_PLAYER_SPEED;
    }

    /**
     * Modificare le variabili contenute nell'istanza del Player
     */
    @Override
    public void update() {
//        getInput();
//        move();
//        game.getGameCamera().centerOnEntity(this);
    }
    
    private void getInput() {
        xMove = 0;
        yMove = 0;
        
        if(game.getKeyManager().up)
            yMove = -speed;
        if(game.getKeyManager().down)
            yMove = speed;
        if(game.getKeyManager().left)
            xMove = -speed;
        if(game.getKeyManager().right)
            xMove = speed;
    }

    /**
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
        g.drawImage(Assets.player, (int) (x - game.getGameCamera().getxOffset()), 
                (int) (y - game.getGameCamera().getyOffset()), width, height, null);
    }
    
}
