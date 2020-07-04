package processScheduling;

import processScheduling.service.SchedulingService;

import java.util.Scanner;

public class application {
    private static final SchedulingService SCHEDULING = SchedulingService.getService();

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
            case "F": case "f": eventF();break;
            case "Q": case "q": exit();break;
            default: System.out.println("输入错误:");break;
        }
        select();
    }

    public static void eventA() {
        if (SCHEDULING.isHasCreated()) {
            System.out.println("进程已生成, 请先刷新(D)");
        } else {
            SCHEDULING.create();
        }
    }

    public static void eventB() {
        if (!SCHEDULING.isHasCreated()) {
            System.out.println("进程尚未生成, 请先生成进程(A)");
        } else if (SCHEDULING.isHasRun()) {
            System.out.println("程序已运行, 请先刷新进程(E)或清空(F)");
        } else {
            SCHEDULING.run("FCFS");
        }
    }

    public static void eventC() {
        if (!SCHEDULING.isHasCreated()) {
            System.out.println("进程尚未生成, 请先生成进程(A)");
        } else if (SCHEDULING.isHasRun()) {
            System.out.println("程序已运行, 请先刷新进程(E)或清空(F)");
        } else {
            SCHEDULING.run("SPF");
        }
    }

    public static void eventD() {
        if (!SCHEDULING.isHasCreated()) {
            System.out.println("进程尚未生成, 请先生成进程(A)");
        } else if (SCHEDULING.isHasRun()) {
            System.out.println("程序已运行, 请先刷新进程(E)或清空(F)");
        } else {
            SCHEDULING.run("HRRN");
        }
    }

    public static void eventE() {
        if (!SCHEDULING.isHasCreated()) {
            System.out.println("进程尚未生成, 请先生成进程(A)");
        } else if (!SCHEDULING.isHasRun()){
            System.out.println("尚未进行调度, 无需刷新");
        } else {
            SCHEDULING.reflush();
        }
    }

    public static void eventF() {
        SCHEDULING.clear();
    }

    public static void exit() {
        System.out.println("退出");
        System.exit(0);
    }


    public static void selectBox() {
        System.out.println("---------------------");
        if(!SCHEDULING.isHasCreated()) System.out.println("|A: 随机生成进程");
        if(SCHEDULING.isHasCreated() & !SCHEDULING.isHasRun()) System.out.println("|B: 使用FCFS开始调度");
        if(SCHEDULING.isHasCreated() & !SCHEDULING.isHasRun()) System.out.println("|C: 使用SPF开始调度");
        if(SCHEDULING.isHasCreated() & !SCHEDULING.isHasRun()) System.out.println("|D: 使用HRRN开始调度");
        if(SCHEDULING.isHasCreated() & SCHEDULING.isHasRun()) System.out.println("|E: 刷新进程");
        System.out.println("|F: 清空");
        System.out.println("|Q: 退出");
        System.out.println("----------------------");
    }
}
