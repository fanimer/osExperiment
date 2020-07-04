package jobScheduling.entity;

import jobScheduling.enumerate.JobStatusEnum;

import java.util.Random;

public class Job {
    private static int count = 0;
    private final int id;
    private int submitTime;
    private int serviceTime;
    private int priority;
    private int remainingTime;
    private Integer startTime;
    private Integer finishTime;
    private JobStatusEnum status;

    public Job() {
        count++;
        id = count;
        Random r = new Random();
        while ((submitTime = r.nextInt(20)) == 0);
        while ((serviceTime = r.nextInt(20)) == 0);
        while ((priority = r.nextInt(10)) == 0);
        reset();
    }

    public static void setCount(int count) {
        Job.count = count;
    }

    public void reset() {
        startTime = null;
        status = JobStatusEnum.W;
        remainingTime = serviceTime;
    }

    public void start() {
        this.status = JobStatusEnum.R;
    }

    public void finish(int finishTime) {
        this.finishTime = finishTime;
        status = JobStatusEnum.F;
        System.out.println(this);
    }

    public void run() {
        remainingTime--;
    }

    @Override
    public String toString() {
        return  "|" + getId() +
                "          " + getSubmitTime() +
                "          " + getServiceTime() +
                "          " + getPriority() +
                "          " + getStatus().getName() +
                (getStatus() != JobStatusEnum.F ? ""  : (
                        "          " + getStartTime() +
                        "          " + getFinishTime() +
                        "          " + (getFinishTime() - getSubmitTime()) +
                        "          " + (getFinishTime() - getSubmitTime()) / (float) getServiceTime()
                ));
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public JobStatusEnum getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public int getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(int submitTime) {
        this.submitTime = submitTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }


    public int getPriority() {
        return priority;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Integer getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Integer finishTime) {
        this.finishTime = finishTime;
    }
}
