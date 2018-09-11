package Utils;

import Models.Charapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import server.Game;



public class Utils {
    
    public static final String SOLDIER_ID = "1";
    public static final String ARCHER_ID = "2";
    public static final String KING_ID = "3";
  
    private static final String FULL_HEALTH = "0";
    private static final String HALF_HEALTH = "1";
    private static final String LOW_HEALTH = "2";
    
    private static final String ON_ATTACK = "1";
    private static final String NOT_ON_ATTACK = "0";
    
    public static final String UP = "1";
    public static final String UP_RIGHT = "2";
    public static final String RIGHT = "3";
    public static final String DOWN_RIGHT = "4";
    public static final String DOWN = "5";
    public static final String DOWN_LEFT = "6";
    public static final String LEFT = "7";
    public static final String UP_LEFT = "8";
    
    public static final int ARCHER_HEALTH = 100;
    public static final int SOLDIER_HEALTH = 150;
    public static final int KING_HEALTH = 300;
    
    public static final int ATTACK_POWER = 10;
    public static final int DEFENCE_POWER = 2;
    public static final int ARROW_POWER = 15;
    
    
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
        
        ArrayList list = decodeMovement(buffer.substring(1));
        if (buffer.startsWith("a"))
            list.add(new Coordinate(-2,0)); //uso l'ultima coordinata per riconoscere il tipo di movimento, o di attacco o di spostamento
        else if (buffer.startsWith("m")){
            list.add(new Coordinate(-1,0));

        }
        else if (buffer.startsWith("f")){
            return buffer;
        }
        return list;
    }
    
    public static int getIdFromCharapter(Charapter charapter, int upperBound)
    {
        String data = "";
        //fa parte del primo team
        if (charapter.getId() <=upperBound)
            data+="1";
        else
            data+="2";
        
        data+=charapter.getType();
        if (charapter.getHealth() > 50)
            data+=FULL_HEALTH;
        else if (charapter.getHealth() > 25)
            data+=HALF_HEALTH;
        else
            data+=LOW_HEALTH;
        if (charapter.isShooting())
            data+=ON_ATTACK;
        else
            data+=NOT_ON_ATTACK;
        return Utils.parseInt(data);
    } 
    public static ArrayList<Coordinate> decodeMovement(String data)
    {
            ArrayList list = new ArrayList<Coordinate>();
            //tolgo le parentesi quadre dai buffer
            data = data.substring(1, data.length()-1);
            data = " " + data;
            //spezzo nei vari componenti che sono divisi da virgole
            String[] splits = data.split(",");
            for (String couple : splits)
            {   //spezzo le coordinate divise da spazi
                String[] integers = couple.split(" ");
                int x = Integer.parseInt(integers[1]);
                int y = Integer.parseInt(integers[2]);
                list.add(new Coordinate(x,y));    
            }
            return list;
        
    }
       
    
}
