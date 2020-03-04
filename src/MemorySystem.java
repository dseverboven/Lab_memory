import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.exit;

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

    public void compaction() {

        // TODO if Method 1
        int len = 0;
//        int last = 0;
        for (int j = 0; j < block_list.size(); j++) {
            MemoryBlock current = block_list.get(j);
            if (current.isEmpty()) {

                len = current.getLength() + len;
                block_list.remove(current);

            }
        }
        MemoryBlock m = block_list.getLast();
        int start = m.getStart() + m.getLength();
        block_list.addLast(new MemoryBlock(start, len));
        System.out.print("total legnth is: " + len + "\n");

        System.out.println(block_list);
    }

    public boolean ff_allocate(int pid, int processLength) {

        // TODO if Method 1
        for (int j = 0; j < block_list.size(); j++) {
            MemoryBlock current = block_list.get(j);
            if (current.isEmpty() && current.getLength() >= processLength) {

                settingMemBlock(pid, processLength, j, current);

                return true;

            }
        }
        return false;
    }

    private void settingMemBlock(int pid, int processLength, int j, MemoryBlock current) {
        MemoryBlock newBlock = new MemoryBlock(current.getStart(), processLength, pid);
        current.setStart(current.getStart() + processLength);
        current.setLength(current.getLength() - processLength);
        block_list.add(j, newBlock);
    }

    public boolean b_allocate(int pid, int processLength) {

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
            settingMemBlock(pid, processLength, index, best_node);
            return true;
        } else {
            return false;
//            System.out.println("Error at b_allocate");
        }

    }

    public boolean w_allocate(int pid, int processLength) {

        MemoryBlock best_node = null;
        int index = -1;
        int size = -1;
        boolean comp;

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
            settingMemBlock(pid, processLength, index, best_node);
            return true;
        } else {
            return false;
//            System.out.println("Error at w_allocate");
        }

    }

    // Todo compaction as sep function inside of find fit

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
                    if (!ff_allocate(pid, processLength)) {
                        compaction();
                        if (!ff_allocate(pid, processLength)) {
                            System.out.println("failed");
                            exit(0);
                        }
                    }
                }

                if (Driver.algorithm == Driver.Type.BEST) {
                    if (!b_allocate(pid, processLength)) {
                        compaction();
                        if (!b_allocate(pid, processLength)) {
                            System.out.println("failed");
                            exit(0);
                        }
                    }
                }

                if (Driver.algorithm == Driver.Type.WORST) {
                    if (!w_allocate(pid, processLength)) {
                        compaction();
                        if (!w_allocate(pid, processLength)) {
                            System.out.println("failed");
                            exit(0);
                        }
                    }
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



