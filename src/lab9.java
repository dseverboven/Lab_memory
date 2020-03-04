import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Arrays;
import java.util.*;


class lab9 {
    public static void main(String[] args) throws IOException {
        HashMap<Integer, Integer> pagestuff = new HashMap<>();

        Scanner scan = new Scanner(args[0]);
        String s = scan.next();
        String[] temp;


        List<String> result = Files.readAllLines(Paths.get(String.valueOf(s)));

        int pagesize = Integer.parseInt(result.get(0));
        int num_page = Integer.parseInt(result.get(1));

        int y = num_page + 2;
        int i;
        for (i = 2; i < y; i++) {
            temp = result.get(i).split(" ");
            int num_1 = Integer.parseInt(temp[0]);
            int num_2 = Integer.parseInt(temp[1]);

            pagestuff.put(num_1, num_2);

        }

        int number_address = Integer.parseInt(result.get(i++));
        int m = i + number_address;
        for (; i < m; i++) {
            String address = result.get(i);
            if (!address.isEmpty()) {
                int hexadecimal = Integer.parseInt(address, 16);
                int p = hexadecimal / pagesize;

                int d = hexadecimal % pagesize;
                int f = pagestuff.get((p));
                int output = f * pagesize + d;
                System.out.println(Integer.toHexString(output));
            }
        }

    }
}
