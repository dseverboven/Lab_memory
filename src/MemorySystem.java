import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemorySystem {

    ArrayList<MemoryBlock> block_list = new ArrayList<>();

    ArrayList<MemoryBlock> hole_list = new ArrayList<>();

    public void allocate(MemoryBlock to_allocate){
        block_list.add(to_allocate);
        System.out.println(block_list.size() + (" SIZE"));

    }

    public void firstFit(List<String> file_array) {
        String[] temp;


        int total_mem = (Integer.parseInt(file_array.get(1)));

        int new_mem;

        System.out.println(total_mem);
        for (int i = 2; i < file_array.size(); i++) {
                temp = file_array.get(i).split(" ");
                System.out.println(Arrays.toString(temp));

                if (temp[0].equals("A")) {
                    MemoryBlock block = new MemoryBlock(temp[3].strip(), 0, Integer.parseInt(temp[6]));

                    allocate(block);


                }
                System.out.println((temp[0]));

              System.out.println(file_array.get(i));

            }

        for (MemoryBlock memoryBlock : block_list) {

            memoryBlock.toString();
        }


        

    }
}



