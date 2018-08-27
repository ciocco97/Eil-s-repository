package Utils;

import Models.Charapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import server.Game;



public class Utils {
    
    private static final int ARCERE_FULL_1 = 20;
    private static final int ARCERE_HALF_1 = 21;
    private static final int ARCERE_LOW_1 = 22;
    private static final int GUERIERO_FULL_1 = 23;
    private static final int GUERIERO_HALF_1 = 24;
    private static final int GUERIERO_LOW_1 = 25;
    
    private static final int ARCERE_FULL_2 = 40;
    private static final int ARCERE_HALF_2 = 41;
    private static final int ARCERE_LOW_2 = 42;
    private static final int GUERIERO_FULL_2 = 43;
    private static final int GUERIERO_HALF_2 = 44;
    private static final int GUERIERO_LOW_2 = 45;
    
    public static String loadFileAsStrig(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append("\n");
            
            bufferedReader.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
                
        return stringBuilder.toString();
    }
    
    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        
    }/**
     * Metodo che codifica in una stringa la mappa attuale del game che gli viene passato
     * @param game Game che possiede la mappa da codificare
     * @return Stringa che rappresenta la mappa codificata
     */
    public static String mapToString(Game game)
    {
        int width=game.getWidth();
        int height=game.getHeight();
        int [][] map = game.getMap();
        String buffer = new String();
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++)
            {
                buffer += map[i][j];
                buffer +=" ";
            }
            buffer +="-";
        }
        return buffer;
    }
    /**
     * Metodo che decodifica la stringa ricevuta in ingrasso, può essere una lista (mossa) o una coordinata che indiza un azione
     * @param buffer Stringa che rappresenta il dato codificato
     * @return 
     */
    public static Object getDataFromString(String buffer)
    {   //se è una lista 
        if (buffer.startsWith("["))
        {
            ArrayList list = new ArrayList<Coordinate>();
            //tolgo le parentesi quadre dai buffer
            buffer = buffer.substring(1, buffer.length()-1);
            buffer = " " + buffer;
            //spezzo nei vari componenti che sono divisi da virgole
            String[] splits = buffer.split(",");
            for (String couple : splits)
            {   //spezzo le coordinate divise da spazi
                String[] integers = couple.split(" ");
                int x = Integer.parseInt(integers[1]);
                int y = Integer.parseInt(integers[2]);
                list.add(new Coordinate(x,y));    
            }
            return list;
        
        }
        else
        {
            String core = buffer.substring(1, buffer.length());
            String integers[] = core.split(" ");
            int x = Integer.parseInt(integers[0]);
            int y = Integer.parseInt(integers[1]);
            return new Coordinate(x,y);
        }
    }
    
    public static int getIdFromCharapter(Charapter charapter)
    {
        //fa parte del primo team
        if (charapter.getId() <=10)
        {
            //gueriero
            if (charapter.getType() == 1){
                if (charapter.getHealth() > 50)
                    return GUERIERO_FULL_1;
                else if (charapter.getHealth() > 25)
                    return GUERIERO_HALF_1;
                else
                    return GUERIERO_LOW_1;
            }
            else
            {
                if (charapter.getHealth() > 50)
                    return ARCERE_FULL_1;
                else if (charapter.getHealth() > 25)
                    return ARCERE_HALF_1;
                else
                    return ARCERE_LOW_1;
            } 
        }
        //fa parte del secondo team
        else
        {
            System.out.println("secondo team");
            if (charapter.getType() == 1){
                if (charapter.getHealth() > 50)
                    return GUERIERO_FULL_2;
                else if (charapter.getHealth() > 25)
                    return GUERIERO_HALF_2;
                else
                    return GUERIERO_LOW_2;
            }
            else
            {
                if (charapter.getHealth() > 50)
                    return ARCERE_FULL_2;
                else if (charapter.getHealth() > 25)
                    return ARCERE_HALF_2;
                else
                    return ARCERE_LOW_2;
            }
        }
        
    }
}
