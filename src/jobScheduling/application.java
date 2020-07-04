package jobScheduling;


import jobScheduling.service.SchedulingService;

import java.util.Scanner;

public class application {

    private static SchedulingService SCHEDULING = SchedulingService.getService();

    public static void main(String[] args) {
        select();
    }

    public static void select() {
        selectBox();
        System.out.print("请输入选项：");
        Scanner in = new Scanner(System.in);
        String select = in.next();
        switch (select){
            case "A": case "a": eventA();break;
            case "B": case "b": eventB();break;
            case "C": case "c": eventC();break;
            case "D": case "d": eventD();break;
            case "E": case "e": eventE();break;
            case "Q": case "q": exit();break;
            case "S": case "s": eventS();break;
            default: System.out.println("输入错误:");break;
        }
        select();
    }

    public static void eventS() {
        System.out.print("请选择时间片大小：(1~5)");
        Scanner in = new Scanner(System.in);
        int select = in.nextInt();
        switch (select) {
            case 1: case 2: case 3: case 4: case 5:
                System.out.println("已选" + select);
                SCHEDULING.setSlice(select);
                break;
            default: System.out.println("超出范围，请重试。");
                eventS();
                break;
        }
    }


    public static void eventA() {
        if (SCHEDULING.isHasCreated()) {
            System.out.println("作业已生成, 请先刷新(D)");
        } else {
            SCHEDULING.create();
        }
    }

    public static void eventB() {
        if (!SCHEDULING.isHasCreated()) {
            System.out.println("作业尚未生成, 请先生成作业(A)");
        } else if (SCHEDULING.isHasRun()) {
            System.out.println("程序已运行, 请先刷新作业(D)或清空(E)");
        } else {
            SCHEDULING.run("HP");
        }
    }

    public static void eventC() {
        if (!SCHEDULING.isHasCreated()) {
            System.out.println("作业尚未生成, 请先生成作业(A)");
        } else if (SCHEDULING.isHasRun()) {
            System.out.println("程序已运行, 请先刷新作业(D)或清空(E)");
        } else {
            SCHEDULING.run("RR");
        }
    }

    public static void eventD() {
        if (!SCHEDULING.isHasCreated()) {
            System.out.println("任务尚未生成, 请先生成任务(A)");
        } else if (!SCHEDULING.isHasRun()){
            System.out.println("尚未进行调度, 无需刷新");
        } else {
            SCHEDULING.reset();
        }
    }

    public static void eventE() {
        SCHEDULING.clear();
    }

    public static void exit() {
        System.out.println("退出");
        System.exit(0);
    }

    public static void selectBox() {
        System.out.println("-------------------------------------------------------------------");
        System.out.println("| 作业调度    S: 选择时间片[已选slice=" + SCHEDULING.getSlice() + "]");
        if (!SCHEDULING.isHasCreated())System.out.println("|A: 随机创建作业");
        if (SCHEDULING.isHasCreated() & !SCHEDULING.isHasRun()) System.out.println("|B:使用抢占式优先级算法调度");
        if (SCHEDULING.isHasCreated() & !SCHEDULING.isHasRun()) System.out.println("|C:使用RR算法调度");
        if (SCHEDULING.isHasRun() | SCHEDULING.isHasCreated()) System.out.println("|D: 重置作业");
        System.out.println("|E: 清空");
        System.out.println("|Q: 退出");
        System.out.println("-------------------------------------------------------------------");
    }
}
