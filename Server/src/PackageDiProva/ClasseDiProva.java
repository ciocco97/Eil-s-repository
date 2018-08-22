package PackageDiProva;

import java.io.Serializable;

public class ClasseDiProva implements Serializable{
    private String messaggio;

    public ClasseDiProva(String messaggio) { this.messaggio = messaggio; }

    public String getMessaggio() { return messaggio; }

    public void setMessaggio(String messaggio) { this.messaggio = messaggio; }
}
