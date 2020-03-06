import java.util.*;

import static java.lang.System.exit;

public class MemorySystem {

    String[] temp;
    //    LinkedList<MemoryBlock> block_list = new LinkedList<>();
    TreeSet<MemoryBlock> block_list;
    TreeSet<MemoryBlock> empty_block;

    HashMap<Integer, MemoryBlock> map_pid;


    public void deallocate(int pid) {
        if (map_pid.containsKey(pid)) {

            MemoryBlock del_block = map_pid.get(pid);
            del_block.release();
            map_pid.remove(pid);


            MemoryBlock lower = block_list.lower(del_block);

            MemoryBlock higher = block_list.higher(del_block);


            //left
            if (lower.getPid() == -1 && higher.getPid() != -1) {

                int s_start = lower.getStart();
                int new_size = lower.getLength() + del_block.getLength();
                MemoryBlock new_empty = new MemoryBlock(s_start, new_size);

                block_list.remove(lower);

                block_list.remove(del_block);

                empty_block.remove(lower);

                empty_block.remove(del_block);

                block_list.add(new_empty);
                empty_block.add(new_empty);


            }
            //right
            if (higher.getPid() == -1 && lower.getPid() != -1) {

                int s_start = del_block.getStart();
                int new_size = del_block.getLength() + higher.getLength();
                MemoryBlock new_empty = new MemoryBlock(s_start, new_size);

                block_list.remove(higher);
                block_list.remove(del_block);


                empty_block.remove(higher);
                empty_block.remove(del_block);

                block_list.add(new_empty);
                empty_block.add(new_empty);
            }

            //both
            if (higher.getPid() == -1 && lower.getPid() == -1) {
                int s_start = lower.getStart();
                int new_size = lower.getLength() + del_block.getLength() + higher.getLength();
                MemoryBlock new_empty = new MemoryBlock(s_start, new_size);
                block_list.remove(lower);
                block_list.remove(higher);
                block_list.remove(del_block);

                empty_block.remove(lower);
                empty_block.remove(higher);
                empty_block.remove(del_block);

                block_list.add(new_empty);
                empty_block.add(new_empty);


            }


        }


    }

    public void compaction() {

        // TODO if Method 1
        int len = 0;
        int new_len = 0;

        List<MemoryBlock> memz = new ArrayList<>();


        Iterator<MemoryBlock> itr = block_list.iterator();
        while (itr.hasNext()) {
            MemoryBlock mem_next = itr.next();
            if (mem_next.isEmpty()) {

                len = mem_next.getLength() + len;
                memz.add(mem_next);

            } else {
                mem_next.setStart(new_len);
                new_len += mem_next.length;


            }

        }
        block_list.removeAll(memz);
        MemoryBlock m = block_list.last();
        int start = m.getStart() + m.getLength();
        block_list.add(new MemoryBlock(start, len));

        empty_block.clear();
        empty_block.add(new MemoryBlock(start, len));


        System.out.print(" COMPACTING total length is: " + len + "\n");

        System.out.println(block_list);
    }


    public boolean ff_allocate(int pid, int processLength) {

        // TODO if Method 1


        Iterator<MemoryBlock> itr = block_list.iterator();
        while (itr.hasNext()) {

            MemoryBlock mem_next = itr.next();
            if (mem_next.isEmpty() && mem_next.getLength() >= processLength) {

                MemoryBlock newBlock = new MemoryBlock(-1, processLength, pid);
                settingMemBlock(newBlock, mem_next);

                return true;

            }
        }

        return false;
    }

    private void settingMemBlock(MemoryBlock toAdd, MemoryBlock current) {

        toAdd.setStart(current.getStart());

        empty_block.remove(current);
        current.setStart(current.getStart() + toAdd.getLength());
        current.setLength(current.getLength() - toAdd.getLength());
        empty_block.add(current);
        block_list.add(toAdd);
        map_pid.put(toAdd.pid, toAdd);
//        empty_block.clear();

    }


    public boolean b_allocate(int pid, int processLength) {

        MemoryBlock newBlock = new MemoryBlock(-1, processLength, pid);

        MemoryBlock empty = empty_block.ceiling(newBlock);

        if (empty != null) {
            settingMemBlock(newBlock, empty);
            return true;
        } else {
            return false;
        }

    }

    public boolean w_allocate(int pid, int processLength) {
        MemoryBlock newBlock = new MemoryBlock(-1, processLength, pid);


        MemoryBlock empty = empty_block.last();

        if (empty.getLength() < processLength) {
            empty = null;
        }

        if (empty != null) {
            settingMemBlock(newBlock, empty);
            return true;
        } else {
            return false;
        }


    }

    // Todo compaction as sep function inside of find fit

    public void findFit(List<String> file_array) {

        int total_mem = (Integer.parseInt(file_array.get(1)));


        boolean b_w_algo = Driver.algorithm == Driver.Type.BEST || Driver.algorithm == Driver.Type.WORST;
        Comparator comp = b_w_algo ? Comparator.comparing(MemoryBlock::getLength) : Comparator.comparing(MemoryBlock::getStart);

        block_list = new TreeSet<>(comp);
        empty_block = new TreeSet<>(comp);

        map_pid = new HashMap<>();


        block_list.add(new MemoryBlock(0, total_mem));
        empty_block.add(new MemoryBlock(0, total_mem));


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

                        }
                    }
                }

                if (Driver.algorithm == Driver.Type.BEST) {
                    if (!b_allocate(pid, processLength)) {
                        compaction();
                        if (!b_allocate(pid, processLength)) {
                            System.out.println("failed");

                        }
                    }
                }

                if (Driver.algorithm == Driver.Type.WORST) {
                    if (!w_allocate(pid, processLength)) {
                        compaction();
                        if (!w_allocate(pid, processLength)) {
                            System.out.println("failed");

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
//        for (MemoryBlock bb : tree_block){
//            System.out.println("tree block " + bb);
//        }

    }


}



