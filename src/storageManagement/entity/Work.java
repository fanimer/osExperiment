package storageManagement.entity;

import java.util.Random;

public class Work {
    private static int count = 0;
    private int id;
    private int size;
    private int partitionId;
    private boolean status;

    public Work() {
        count++;
        id = count;
        Random r = new Random();
        size = r.nextInt(100);
        status = false;
    }

    public static void setCount(int count) {
        Work.count = count;
    }

    public void distribution(int partitionId) {
        this.partitionId = partitionId;
        status = true;
    }

    @Override
    public String toString() {
        return "|" + getId() +
                "     " + getSize() +
                "        " + getPartitionId() +
                "           " + isStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(int partitionId) {
        this.partitionId = partitionId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
