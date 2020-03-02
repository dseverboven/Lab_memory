import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MemorySystem {

    String[] temp;
    LinkedList<MemoryBlock> block_list = new LinkedList<>();


    public void deallocate(int pid) {
        {
            int j;
            for (j = 0; j < block_list.size(); j++) {
                MemoryBlock current = block_list.get(j);
                if (current.getPid() == pid) {
                    break;
                }
            }
//                if (j == block_list.size()) {
//                    continue;
//                }
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


        }
    }

    public void ff_allocate(int pid, int processLength) {

        // TODO if Method 1
        for (int j = 0; j < block_list.size(); j++) {
            MemoryBlock current = block_list.get(j);
            if (current.isEmpty() && current.getLength() >= processLength) {

                MemoryBlock newBlock = new MemoryBlock(current.getStart(), processLength, pid);
                current.setStart(current.getStart() + processLength);
                current.setLength(current.getLength() - processLength);
                block_list.add(j, newBlock);

                break;
            }
        }
    }

    public void b_allocate(int pid, int processLength) {

        MemoryBlock best_node = null;
        int index = -1;
        int size = -1;

        // TODO if Method 1
        for (int j = 0; j < block_list.size(); j++) {

            MemoryBlock current = block_list.get(j);
            boolean fits = current.isEmpty() && current.getLength() >= processLength;
            if (fits && (size == -1 || current.getLength() < size)) {

                size = current.getLength();
                index = j;
                best_node = current;

            }
        }

        if (index != -1 && best_node != null) {
            MemoryBlock newBlock = new MemoryBlock(best_node.getStart(), processLength, pid);
            best_node.setStart(best_node.getStart() + processLength);
            best_node.setLength(best_node.getLength() - processLength);
            block_list.add(index, newBlock);
        } else {
            System.out.println("Error at b_allocate");
        }

    }

    public void w_allocate(int pid, int processLength) {

        MemoryBlock best_node = null;
        int index = -1;
        int size = -1;

        // TODO if Method 1
        for (int j = 0; j < block_list.size(); j++) {

            MemoryBlock current = block_list.get(j);
            boolean fits = current.isEmpty() && current.getLength() >= processLength;
            if (fits && (size == -1 || current.getLength() > size)) {

                size = current.getLength();
                index = j;
                best_node = current;

            }
        }

        if (index != -1 && best_node != null) {
            MemoryBlock newBlock = new MemoryBlock(best_node.getStart(), processLength, pid);
            best_node.setStart(best_node.getStart() + processLength);
            best_node.setLength(best_node.getLength() - processLength);
            block_list.add(index, newBlock);
        } else {
            System.out.println("Error at b_allocate");
        }

    }


    public void findFit(List<String> file_array) {

        int total_mem = (Integer.parseInt(file_array.get(1)));
        block_list.add(new MemoryBlock(0, total_mem));


        System.out.println(total_mem);
        for (int i = 2; i < file_array.size(); i++) {
            temp = file_array.get(i).split(" ");
            if (temp[0].equals("A")) {


                int processLength = Integer.parseInt(temp[6]);
                int pid = Integer.parseInt(temp[3]);

                System.out.println("Allocating " + pid);

                if (Driver.algorithm == Driver.Type.FIRST) {
                    ff_allocate(pid, processLength);
                }

                if (Driver.algorithm == Driver.Type.BEST) {
                    b_allocate(pid, processLength);
                }

                if (Driver.algorithm == Driver.Type.WORST) {
                    w_allocate(pid, processLength);
                }

                print();

            } else if (temp[0].equals("D")) {

                int pid = Integer.parseInt(temp[3]);
                System.out.println("Deallocating " + pid);
                deallocate(pid);
                print();
            } else {
                //print();test.txt
            }


        }
    }


    public void print() {
        System.out.println();
        for (MemoryBlock mb : block_list) {
            System.out.println(mb);
        }
    }


}



