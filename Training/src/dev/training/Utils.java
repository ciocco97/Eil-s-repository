package dev.training;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    
    public static String loadFileAsStrig(String path) {
        StringBuilder sb = new StringBuilder();
        System.out.println("Path: " + path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine()) != null)
                sb.append(line).append("\n");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }
    
    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
}
