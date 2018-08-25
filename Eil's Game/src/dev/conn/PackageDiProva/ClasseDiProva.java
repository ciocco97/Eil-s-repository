package dev.conn.PackageDiProva;

import dev.training.worlds.Coordinate;
import java.io.Serializable;

public class ClasseDiProva implements Serializable{
    private Coordinate messaggio;

    public ClasseDiProva(Coordinate messaggio) { this.messaggio = messaggio; }

    public Coordinate getMessaggio() { return messaggio; }

    public void setMessaggio(Coordinate messaggio) { this.messaggio = messaggio; }
}
