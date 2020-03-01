import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Driver {


    public static List<String> readFile() throws IOException {
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        List<String> result = Files.readAllLines(Paths.get(String.valueOf(s)));
        if (result.get(0).equals("1")) {
//            firstFit();
            String[] temp;
            MemorySystem ff = new MemorySystem();
            ff.firstFit(result);
//
            System.out.println("ff");
        }
        if (result.get(0).equals("2")) {
//            bestFit();
            for (int i = 2; i < result.size(); i++) {
                System.out.println(result.get(i));
            }
            System.out.println("bf");
        }
        if (result.get(0).equals("3")) {
//            worstFit();
            for (int i = 2; i < result.size(); i++) {
                System.out.println(result.get(i));
                System.out.println("wf");
            }

            new MemoryBlock("asd", 1, 2);

        }
        return result;
    }


    public static void main(String[] args) throws IOException {
        readFile();

    }
}


