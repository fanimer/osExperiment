package processScheduling.entity;

import java.util.Random;

public class Process {
    private static int count = 0;
    private final int id;
    private int submitTime;
    private int serviceTime;
    private int resource;
    private boolean status;

    public Process() {
        count++;
        id = count;
        Random r = new Random();
        submitTime = r.nextInt(20);
        while ((submitTime = r.nextInt(20))==0);
        while ((serviceTime = r.nextInt(20))==0);
        while ((resource = r.nextInt(10))==0);
        status = false;
    }

    @Override
    public String toString() {
        return  "|" + getId() +
                "          " + getSubmitTime() +
                "          " + getServiceTime() +
                "          " + getResource() +
                "          " + isStatus();
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static void setCount(int count) {
        Process.count = count;
    }

    public int getId() {
        return id;
    }

    public int getSubmitTime() {
        return submitTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getResource() {
        return resource;
    }

    public boolean isStatus() {
        return status;
    }
}
