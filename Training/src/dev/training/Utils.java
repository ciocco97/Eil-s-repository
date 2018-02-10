package dev.training;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    
    /**
     * Metodo generico per ottenere ciò che è scritto in un file.
     * @param path il percorso del file
     * @return file sottoforma di stringa
     */
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
    
    /**
     * Metodo generico per convertire una stringa in un numero.
     * @param number stringa da convertire in numero
     * @return il numero rappresentato dalla stringa o 0 in caso di errore
     */
    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
}
