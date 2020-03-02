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
        block_list.add(new MemoryBlock(0, total_mem));


        System.out.println(total_mem);
        for (int i = 2; i < file_array.size(); i++) {
            temp = file_array.get(i).split(" ");
            //    System.out.println(Arrays.toString(temp));

            if (temp[0].equals("A")) {

                //       System.out.println("in if A" + Arrays.toString(temp));
                MemoryBlock node = null;
                int processLength = Integer.parseInt(temp[6]);
                int pid = Integer.parseInt(temp[3]);
                int j;

                // TODO if Method 1
                for (j = 0; j < block_list.size(); j++) {
                    MemoryBlock current = block_list.get(j);
                    if (current.isEmpty() && current.getLength() >= processLength) {
                        node = current;
                        break;
                    }
                }
                // TODO else if Method 2
                if (node != null) {
                    MemoryBlock newBlock = new MemoryBlock(node.getStart(), processLength, pid);
                    node.setStart(node.getStart() + processLength);
                    node.setLength(node.getLength() - processLength);
                    block_list.add(j, newBlock);
                }


            } else if (temp[0].equals("D")) {
                int j;
                for (j = 0; j < block_list.size(); j++) {
                    MemoryBlock current = block_list.get(j);
                    if (current.getPid() == Integer.parseInt(temp[3])) {
                        break;
                    }
                }
                if (j == block_list.size()) {
                    continue;
                }
                block_list.get(j).release();
                if (j == 0) {
                    if (block_list.size() == 1 || !block_list.get(1).isEmpty()) {
                        block_list.get(j).release();
                    } else {
                        block_list.get(0).setLength(block_list.get(0).getLength() + block_list.get(1).getLength());
                        block_list.get(j).release();
                        block_list.remove(1);
                    }
                } else if (j == block_list.size() - 1) {
                    if (!block_list.get(j - 1).isEmpty()) {
                        block_list.get(j).release();
                    } else {
                        block_list.get(j - 1).setLength(block_list.get(j - 1).getLength() + block_list.get(j).getLength());
                        block_list.remove(j);
                    }
                } else if (block_list.get(j - 1).isEmpty() && block_list.get(j + 1).isEmpty()) {
                    block_list.get(j - 1).setLength(block_list.get(j - 1).getLength()
                        + block_list.get(j).getLength() + block_list.get(j + 1).getLength());
                    block_list.remove(j + 1);
                    block_list.remove(j);
                } else if (!block_list.get(j - 1).isEmpty() && !block_list.get(j + 1).isEmpty()) {
                    block_list.get(j).release();
                } else if (block_list.get(j - 1).isEmpty()) {
                    block_list.get(j - 1).setLength(block_list.get(j - 1).getLength() + block_list.get(j).getLength());
                    block_list.remove(j);
                } else {
                    block_list.get(j).setLength(block_list.get(j).getLength() + block_list.get(j + 1).getLength());
                    block_list.get(j).release();
                    block_list.remove(j + 1);
                }


            } else {
                for (MemoryBlock mb : block_list) {
                    System.out.println(mb);
                }
            }

            // System.out.println((temp[0]));

            //System.out.println(file_array.get(i));

        }

//        for (MemoryBlock memoryBlock : block_list) {
//
//            memoryBlock.toString();
//        }


    }
}



