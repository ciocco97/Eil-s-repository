package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import server.Game;

public class Utils {
    
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
}
