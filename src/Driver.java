import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


public class Driver {

    public enum Type{
        FIRST,
        BEST,
        WORST
    }

    public static Type algorithm;


    public static List<String> readFile() throws IOException {
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        List<String> result = Files.readAllLines(Paths.get(String.valueOf(s)));




        if (result.get(0).equals("1")) {
            algorithm = Type.FIRST;
        }
        if (result.get(0).equals("2")) {
            algorithm = Type.BEST;
        }
        if (result.get(0).equals("3")) {
            algorithm = Type.WORST;
        }




        MemorySystem ff = new MemorySystem();
        ff.findFit(result);


        return result;
    }


    public static void main(String[] args) throws IOException {
        readFile();

    }
}


