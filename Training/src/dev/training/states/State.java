package dev.training.states;

import dev.training.Game;
import java.awt.Graphics;

/**
 * Questa classe rappresenta ciò che hanno in comune gli state di manu, di 
 * impostazioni e di gioco
 */
public abstract class State {
    
    /**
     * Creiamo un oggetto-stato che è in grado di mantenere in memoria lo stato 
     * del gioco che inizialmente inizializziamo a null in modo che all'inizio 
     * (il gioco non è ancora partito) non esista uno stato.
     * Successivamente, quando sarà dverso da null, la variabile servirà a 
     * capire quale stato dovremo updatare o renderizzare.
     */
    
    private static State currentState = null;
    
    /**
     * Cambia lo stato che dovrà essere "updatato" e renderizzato
     * @param state 
     */
    public static void setState(State state) {
        currentState = state;
    }
    
    public static State getState() {
        return currentState;
    }
    
    
    /**
     * I metodi riportati sotto non hanno niente a che vedere con il metodo 
     * sopra riportato
     * Quelli sotto sono riferiti alle classi State mentre quelli sopra vengono 
     * utilizzati per tenere traccia di una variabile accessibile da qualunque 
     * altra classe
     */
    
    /**
     * La variabile di tipo Game viene aggiunta alle classi di State in modo che 
     * in tutte le fasi del gioco si possa avere l'accesso a funzionalità come 
     * l'input.
     */
    protected Game game;
    
    public State(Game game) {
        this.game = game;
    }
    
    public abstract void update();
    
    public abstract void render(Graphics g);
    
}
