package storageManagement;

import storageManagement.service.AlgorithmService;
import storageManagement.service.PartitionService;
import storageManagement.service.WorkService;

import java.util.Scanner;

public class application {
    private static final PartitionService PARTITION = PartitionService.getService();
    private static final WorkService WORK = WorkService.getService();
    private static final AlgorithmService ALGORITHM = AlgorithmService.getService();
    public static void main(String[] args) {
        select();
    }

    public static void select() {
        selectBox();
        System.out.print("请输入选项：");
        Scanner in = new Scanner(System.in);
        String select = in.next();
        switch (select){
            case "A": eventA();break;
            case "B": eventB();break;
            case "C": eventC();break;
            case "D": eventD();break;
            case "E": eventE();break;
            case "S": ALGORITHM.select();break;
            case "Q": exit();break;
            default: System.out.println("输入错误:");break;
            }
        select();
    }

    public static void exit() {
        System.out.println("退出");
        System.exit(0);
    }

    public static void eventA() {
        if (!PARTITION.isStatus())
            PARTITION.create();
        else System.out.println("需要先清空(E)");
    }

    public static void eventB() {
        if (!WORK.isStatus())
            WORK.create();
        else System.out.println("需要先清空(E)");
    }

    public static void eventC() {
        if (!PARTITION.isStatus()) {
            System.out.println("分区不存在, 需要先创建分区(A)");
            return;
        }
        if (!WORK.isStatus()) {
            System.out.println("作业不存在, 需要先创建作业(B)");
            return;
        }
        if (ALGORITHM.getSelect().equals("")) {
            System.out.println("未选择算法, 需要先选择算法(S)");
            return;
        }
        if (ALGORITHM.isStatus()) {
            System.out.println("已分配, 需要先回收已分配分区(D)");
            return;
        }
        ALGORITHM.run();
    }

    public static void eventD() {
        if (!ALGORITHM.isStatus()) System.out.println("尚未分配, 请先分配(C)");
        else ALGORITHM.resetPartition();
    }

    public static void eventE() {
        ALGORITHM.clearAll();
    }

    public static void selectBox() {
        String border =  "---------------------------------";
        String header =  "|  作业调度";
        String selectAlgorithm = ALGORITHM.getSelect();
        String algorithm = "   S: 算法选择 " + selectAlgorithm;
        String selectA = "|  A: 随机生成空闲分区";
        String selectB = "|  B: 随机生成作业";
        String selectC1 = "|  C: 开始分配";
        String selectC2 = "|  C: 继续分配";
        String selectD = "|  D: 回收所有资源";
        String selectE = "|  E: 清空";
        String selectQ = "|  Q: 退出";
        System.out.println(border);
        System.out.println(header + algorithm);
        // 若未生成空闲分区，则允许事件A发生
        if (!PARTITION.isStatus()) System.out.println(selectA);
        // 若未生成作业，则允许事件B发生
        if (!WORK.isStatus()) System.out.println(selectB);
        // 空闲分区&作业&算法&已回收 均存在才能发生事件C
        if(PARTITION.isStatus() & WORK.isStatus()
                & !selectAlgorithm.equals("") & !ALGORITHM.isStatus()) {
            System.out.println(!ALGORITHM.isStatus() ? selectC1 : selectC2);
        }
        // 若以分配才可以回收资源发生事件D
        if (ALGORITHM.isStatus())
            System.out.println(selectD);

        System.out.println(selectE);
        System.out.println(selectQ);
        System.out.println(border);
    }
}
