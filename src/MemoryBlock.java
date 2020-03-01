public class MemoryBlock {

    String pid;
    int start;
    int length;
    boolean hole = false;

    public MemoryBlock(String pid, int start, int length) {
        this.pid = pid;
        this.start = start;
        this.length = length;
    }

    public MemoryBlock(int start, int length){
        this.start = start;
        this.length = length;
        this.hole = true;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
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

    public boolean isHole() {
        return hole;
    }

    public void setHole(boolean hole) {
        this.hole = hole;
    }

}
