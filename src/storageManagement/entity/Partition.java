package storageManagement.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Partition {
    private static int count = 0;
    private static Partition header;
    private int id;
    private int size;
    private int freeSize;
    private int address;
    private Partition next;
    private List<Work> works;

    public Partition() {
        count++;
        id=count;
        Random r = new Random();
        size = r.nextInt(100);
        address = r.nextInt(100);
        reset();
    }

    public static Partition getHeader() {
        return header;
    }

    public static void setHeader(Partition header) {
        Partition.header = header;
    }

    @Override
    public String toString() {
        return "|" + getId() +
                "     " + getSize() +
                "           " + getFreeSize() +
                "           " + getAddress() +
                "             " + worksShow();
    }

    public StringBuilder worksShow() {
        StringBuilder s = new StringBuilder();
        for(Work w: works) {
            s.append(w.getId()).append("|");
        }
        return s;
    }


    public void distribution(Work work) {
        works.add(work);
        freeSize -= work.getSize();
    }

    public void reset() {
        freeSize = size;
        works = new ArrayList<>();
    }

    public static void clear() {
        count = 0;
        header = null;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Partition.count = count;
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

    public int getFreeSize() {
        return freeSize;
    }

    public void setFreeSize(int freeSize) {
        this.freeSize = freeSize;
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public Partition getNext() {
        return next;
    }

    public void setNext(Partition next) {
        this.next = next;
    }
}
