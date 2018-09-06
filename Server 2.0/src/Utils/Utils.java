package Utils;

import Models.Charapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import server.Game;



public class Utils {
    
    private static final int GUERIERO_FULL_1 = 30;
    private static final int GUERIERO_HALF_1 = 31;
    private static final int GUERIERO_LOW_1 = 32;
    private static final int ARCERE_FULL_1 = 33;
    private static final int ARCERE_HALF_1 = 34;
    private static final int ARCERE_LOW_1 = 35;
    private static final int KING_FULL_1 = 90;
    private static final int KING_HALF_1 = 91;
    private static final int KING_LOW_1 = 92;
    
    
    private static final int GUERIERO_FULL_2 = 101;
    private static final int GUERIERO_HALF_2 = 102;
    private static final int GUERIERO_LOW_2 = 103;
    private static final int ARCERE_FULL_2 = 110;
    private static final int ARCERE_HALF_2 = 111;
    private static final int ARCERE_LOW_2 = 112;
    private static final int KING_FULL_2 = 190;
    private static final int KING_HALF_2 = 191;
    private static final int KING_LOW_2 = 192;
    
    
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
        return buffer;
    }
    
    public static int getIdFromCharapter(Charapter charapter, int upperBound)
    {
        //fa parte del primo team
        if (charapter.getId() <=upperBound)
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
            else if (charapter.getType() == 2)
            {
                if (charapter.getHealth() > 50)
                    return ARCERE_FULL_1;
                else if (charapter.getHealth() > 25)
                    return ARCERE_HALF_1;
                else
                    return ARCERE_LOW_1;
            }
            else if (charapter.getType() == 3)
            {
                if (charapter.getHealth() > 50)
                    return KING_FULL_1;
                else if (charapter.getHealth() > 25)
                    return KING_HALF_1;
                else
                    return KING_LOW_1;
            }
        }
        //fa parte del secondo team
        else
        {
            if (charapter.getType() == 1){
                if (charapter.getHealth() > 50)
                    return GUERIERO_FULL_2;
                else if (charapter.getHealth() > 25)
                    return GUERIERO_HALF_2;
                else
                    return GUERIERO_LOW_2;
            }
            else if (charapter.getType() == 2)
            {
                if (charapter.getHealth() > 50)
                    return ARCERE_FULL_2;
                else if (charapter.getHealth() > 25)
                    return ARCERE_HALF_2;
                else
                    return ARCERE_LOW_2;
            }
            else if (charapter.getType() == 3)
            {
                if (charapter.getHealth() > 50)
                    return KING_FULL_2;
                else if (charapter.getHealth() > 25)
                    return KING_HALF_2;
                else
                    return KING_LOW_2;
            }
        }
        return 0;
    }
    
}
