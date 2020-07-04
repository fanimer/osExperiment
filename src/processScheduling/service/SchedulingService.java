package processScheduling.service;

import processScheduling.entity.Process;

import java.util.ArrayList;
import java.util.List;

public class SchedulingService {
    private boolean hasCreated = false;
    private boolean hasRun = false;
    private static SchedulingService service;
    private List<Process> processes = new ArrayList<>();

    private int clock = 0;
    private int overCount = processes.size();
    private float totalTTime = 0;
    private float totalWTTime = 0;

    public void create() {
        int processCount = 3;
        int i = 0;
        hasCreated = true;
        while (i < processCount) {
            processes.add(new Process());
            i++;
        }
        show();
    }

    public void show() {
        System.out.println("-------------------------------------------------");
        System.out.println("|id        提交时间     服务时间    所需资源   状态");
        processes.forEach(System.out::println);
        System.out.println("-------------------------------------------------");
    }

    public void run(String select) {
        clock = 0;
        overCount = processes.size();
        totalTTime = 0;
        totalWTTime = 0;
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("|id        提交时间     服务时间    所需资源   状态     开始时间   结束时间   周转时间  带权周转时间");
        switch (select) {
            case "FCFS" : FCFS();break;
            case "SPF" : SPF();break;
            case "HRRN" : HRRN();break;
        }
        System.out.println("平均周转时间：" + totalTTime/8 + "；平均带权周转时间：" + totalWTTime/8);
        System.out.println("-----------------------------------------------------------------------------------------------");
        hasRun = true;
    }

    public void processStart(Process curPro) {
        int overTime = clock + curPro.getServiceTime();
        float turnaroundTime = overTime-curPro.getSubmitTime();
        totalTTime += turnaroundTime;
        float weightedTurnoverTime = turnaroundTime / curPro.getServiceTime();
        totalWTTime += weightedTurnoverTime;
        curPro.setStatus(true);
        System.out.println(curPro + "      " +
                clock + "        " +
                overTime + "        " +
                turnaroundTime + "        " +
                weightedTurnoverTime);
        clock = overTime;
        overCount--;
    }

    public void FCFS() {
        Process curPro = null;
        while (overCount != 0) {
            for (Process p : processes)
                if (!p.isStatus())
                    if (curPro == null) curPro = p;
                    else if (curPro.getSubmitTime() > p.getSubmitTime()) curPro = p;

            if (curPro == null) break;

            if (curPro.getSubmitTime() <= clock) {
                processStart(curPro);
                curPro = null;
            } else {
                clock++;
            }
        }
    }

    public void SPF() {
        while (overCount != 0) {
            Process curPro = null;
            for (Process p : processes)
                if (!p.isStatus() & p.getSubmitTime() <= clock)
                    if (curPro == null) curPro = p;
                    else if (curPro.getServiceTime() > p.getServiceTime()) curPro = p;

            if (curPro == null) {
                clock++;
            } else {
                processStart(curPro);
                curPro = null;
            }
        }
    }

    public void HRRN() {
        while (overCount != 0) {
            Process curPro = null;
            float maxRp = 0;
            for (Process p : processes)
                if (!p.isStatus() & p.getSubmitTime() <= clock) {
                    float Rp = (clock - p.getSubmitTime()) / (float) p.getServiceTime();
                    if (Rp > maxRp | curPro == null) {
                        maxRp = Rp;
                        curPro = p;
                    }
                }
            if (curPro == null) {
                clock++;
            } else {
                processStart(curPro);
            }
        }
    }

    public void reflush() {
        hasRun = false;
        for (Process p : processes) {
            p.setStatus(false);
        }
        show();
    }

    public void clear() {
        hasCreated = false;
        hasRun = false;
        processes = new ArrayList<>();
        Process.setCount(0);
        System.out.println("清空成功");
    }

    public boolean isHasCreated() {
        return hasCreated;
    }

    public boolean isHasRun() {
        return hasRun;
    }

    public static SchedulingService getService() {
        if (service == null) service = new SchedulingService();
        return service;
    }

    public static void main(String[] args) {
        SchedulingService.getService().create();
        SchedulingService.getService().run("FCFS");
        SchedulingService.getService().reflush();
        SchedulingService.getService().run("SPF");
        SchedulingService.getService().reflush();
        SchedulingService.getService().run("HRRN");
    }

}
