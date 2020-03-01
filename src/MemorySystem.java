import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MemorySystem {



//    ArrayList<MemoryBlock> block_list = new ArrayList<>();

//    ArrayList<MemoryBlock> hole_list = new ArrayList<>();


    public void firstFit(List<String> file_array) {
        String[] temp;
        LinkedList<MemoryBlock> block_list = new LinkedList<>();

        int total_mem = (Integer.parseInt(file_array.get(1)));
        block_list.add( new MemoryBlock(0, total_mem));


        System.out.println(total_mem);
        for (int i = 2; i < file_array.size(); i++) {
                temp = file_array.get(i).split(" ");
                System.out.println(Arrays.toString(temp));

                if (temp[0].equals("A")) {

                //    MemoryBlock empty = new MemoryBlock(0, Integer.parseInt(temp[1]), true);
                //    allocate(empty);


                //    MemoryBlock block = new MemoryBlock(temp[3].strip(), 0, Integer.parseInt(temp[6]));

                //    allocate(block);
                    MemoryBlock mb = null;
                    int processLength = Integer.parseInt(temp[6]);
                    int pid = Integer.parseInt(temp[3]);
                    int j;
                    for (j=0; j < block_list.size(); j++){
                        MemoryBlock current = block_list.get(j);
                        if(current.isEmpty() && current.getLength()>= processLength){
                            mb = current;
                            break;
                        }
                    }
                    if(mb != null){
                        MemoryBlock newBlock = new MemoryBlock(mb.getStart(), processLength, pid);
                        mb.setStart(mb.getStart()+processLength);
                        block_list.add(j, newBlock);
                    }



                } else if(temp[0].equals("D")) {

                } else {
                    for (MemoryBlock mb: block_list){
                        System.out.println(mb);
                    }
                }
               // System.out.println((temp[0]));

              //System.out.println(file_array.get(i));

            }

        for (MemoryBlock memoryBlock : block_list) {

            memoryBlock.toString();
        }


        

    }
}



