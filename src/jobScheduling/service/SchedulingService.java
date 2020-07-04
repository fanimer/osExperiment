package jobScheduling.service;


import jobScheduling.entity.Job;
import jobScheduling.enumerate.JobStatusEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchedulingService {

    private static SchedulingService service = null;

    private boolean hasCreated = false;

    private boolean hasRun = false;

    private List<Job> jobs = new ArrayList<>();

    private Map<Integer, Job> readyMap = new HashMap<>();

    private List<Job> readyList = new ArrayList<>();

    private int slice = 1;

    private float totalTTime = 0;
    private float totalWTTime = 0;

    public static SchedulingService getService() {
        if (service == null) service = new SchedulingService();
        return service;
    }

    public void create() {
        hasCreated = true;
        int i = 0;
        int maxSize = 10;
        while (i < maxSize) {
            jobs.add(new Job());
            i++;
        }
        show();
    }

    public void show() {
        System.out.println("-----------------------------------------------------");
        System.out.println("|id         提交时间    服务时间    优先权     状态");
        jobs.forEach(System.out::println);
        System.out.println("-----------------------------------------------------");
    }

    public void run(String select) {
        hasRun = true;
        switch (select) {
            case ("HP"): HP();break;
            case ("RR"): RR();break;
        }
    }

    public void reset() {
        hasRun = false;
        readyMap = new HashMap<>();
        readyList = new ArrayList<>();
        totalWTTime = 0;
        totalWTTime = 0;
        for (Job j : jobs) {
            j.reset();
        }
        show();
    }

    public void clear() {
        hasRun = false;
        hasCreated = false;
        readyMap = new HashMap<>();
        readyList = new ArrayList<>();
        totalWTTime = 0;
        totalWTTime = 0;
        Job.setCount(0);
        jobs = new ArrayList<>();
    }

    public void HP() {
        int overCount = 0;
        int clock = 0;
        Job curJob = null;
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("|id         提交时间    服务时间    优先权     状态           开始时间     结束时间     周转时间   带权周转时间");
        while (overCount < jobs.size()) {
            for (Job j : jobs)
                if (j.getSubmitTime() <= clock & j.getStatus() == JobStatusEnum.W) {
                    if (curJob == null) curJob = j;
                    else if (curJob.getPriority() < j.getPriority()) curJob = j;
                    readyMap.put(j.getId(), j);
                    j.start();
                }

            if (curJob != null) {
                if (curJob.getRemainingTime() == curJob.getServiceTime())
                    curJob.setStartTime(clock);
                curJob.run();
                if (curJob.getRemainingTime() == 0) {
                    curJob.finish(clock + 1);
                    float TTime = curJob.getFinishTime() - curJob.getSubmitTime();
                    this.totalTTime += TTime;
                    this.totalWTTime += TTime / curJob.getServiceTime();
                    readyMap.remove(curJob.getId());
                    overCount++;
                    if (overCount == jobs.size()) break;
                    curJob = null;
                    for (Job j : readyMap.values()) {
                        if (curJob == null) {
                            curJob = j;
                        } else if (curJob.getPriority() < j.getPriority()) {
                            curJob = j;
                        }
                    }
                }
            }
            clock++;
        }
        System.out.println("|平均周转时间：" + this.totalTTime / jobs.size() +
                        " 平均带权周转时间" + this.totalWTTime / jobs.size());
        System.out.println("-----------------------------------------------------");
    }

    public void RR() {
        int overCount = 0;
        int clock = 0;
        int mark = 0;
        Job curJob = null;
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("|id         提交时间    服务时间    优先权     状态           开始时间     结束时间     周转时间   带权周转时间");
        while (overCount < jobs.size()) {
            for (Job j : jobs)
                if (j.getSubmitTime() <= clock & j.getStatus() == JobStatusEnum.W) {
                    readyList.add(j);
                    j.start();
                }
            if (readyList.size() != 0 | curJob != null) {
                if (mark == 0 | curJob == null) {
                    curJob = readyList.get(0);
                    readyList.remove(0);
                    mark = slice;
                }
                if (curJob.getRemainingTime() == curJob.getServiceTime())
                    curJob.setStartTime(clock);
                curJob.run();
                if (curJob.getRemainingTime() == 0) {
                    mark = 0;
                    curJob.finish(clock + 1);
                    float TTime = curJob.getFinishTime() - curJob.getSubmitTime();
                    this.totalTTime += TTime;
                    this.totalWTTime += TTime / curJob.getServiceTime();
                    overCount++;
                    if (overCount == jobs.size()) break;
                    curJob = null;
                } else {
                    mark--;
                    if (mark == 0) readyList.add(curJob);
                }
            }
            clock++;
        }
        System.out.println("|平均周转时间：" + this.totalTTime / jobs.size() +
                " 平均带权周转时间" + this.totalWTTime / jobs.size());
        System.out.println("-----------------------------------------------------");
    }

    public void update(int clock) {
        for (Job j : jobs)
            if (j.getSubmitTime() <= clock & j.getStatus() == JobStatusEnum.W) {

                j.start();
            }
    }

    public static void main(String[] args) {
        SchedulingService.getService().create();
        SchedulingService.getService().RR();
    }

    public boolean isHasCreated() {
        return hasCreated;
    }

    public void setHasCreated(boolean hasCreated) {
        this.hasCreated = hasCreated;
    }

    public boolean isHasRun() {
        return hasRun;
    }

    public void setHasRun(boolean hasRun) {
        this.hasRun = hasRun;
    }

    public int getSlice() {
        return slice;
    }

    public void setSlice(int slice) {
        this.slice = slice;
    }
}

