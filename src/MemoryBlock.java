public class MemoryBlock {

    int pid;
    int start;
    int length;

    @Override
    public String toString() {
        if (pid >= 0)
            return "From " + getStart() + " - " + (getStart() + getLength()) + " len: " + getLength() + " pid:" + getPid();
        else
            return "From " + getStart() + " - " + (getStart() + getLength()) + " len: " + getLength() + " is empty.";

    }


    public MemoryBlock(int start, int length, int pid) {
        this.pid = pid;
        this.start = start;
        this.length = length;
    }

    public MemoryBlock(int start, int length) {
        this.pid = -1;
        this.start = start;
        this.length = length;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;

    }

    public boolean isEmpty() {
        return getPid() < 0;
    }

    public void release() {
        setPid(-1);
    }

}
