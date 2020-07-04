package storageManagement.service;

import storageManagement.entity.Partition;
import storageManagement.entity.Work;

import java.util.List;
import java.util.Scanner;

public class AlgorithmService {
    private static AlgorithmService service;
    private static Integer select = -1;
    private boolean status = false;
    private static final String[] algorithmName = new String[]{
            "首次适应算法", "循环首次适应算法", "最佳适应算法"
    };
    private final WorkService workService = WorkService.getService();
    private final PartitionService partitionService = PartitionService.getService();

    public static AlgorithmService getService() {
        if (service == null) service = new AlgorithmService();
        return service;
    }

    public boolean isStatus() {
        return status;
    }

    public String getSelect() {
        if (select == -1) return "";
        return "[已选" + algorithmName[select] + "]";
    }

    private void selectBox() {
        String border =  "----------------------------";
        String selectA = "|0: " + algorithmName[0] + (select.equals(0) ? "(已选)": "");
        String selectB = "|1: " + algorithmName[1] + (select.equals(1) ? "(已选)": "");
        String selectC = "|2: " + algorithmName[2] + (select.equals(2) ? "(已选)": "");
        String selectQ = "|3: 退出";
        System.out.println(border);
        System.out.println(selectA);
        System.out.println(selectB);
        System.out.println(selectC);
        System.out.println(selectQ);
        System.out.println(border);
    }

    public void select() {
        selectBox();
        System.out.print("请选择算法(0,1,2)或退出选择(3): ");
        Scanner in = new Scanner(System.in);
        Integer select = in.nextInt();
        while (!select.equals(0) && !select.equals(1) && !select.equals(2) && !select.equals(3)) {
            System.out.print("选择错误, 请选择算法(0,1,2)或退出选择(3): ");
            select = in.nextInt();
        }
        if (!select.equals(3)) {
            AlgorithmService.select = select;
            System.out.println("已选" + algorithmName[select]);
        }
    }

    public void run() {
        status = true;
        switch (select) {
            case 0: algorithmA();break;
            case 1: algorithmB();break;
            case 2: algorithmC();break;
            default: throw new RuntimeException("System inner error");
        }
    }

    public void resetPartition() {
        status = false;
        partitionService.reset();
    }

    public void clearAll() {
        status = false;
        partitionService.clear();
        workService.clear();
        System.out.println("清空成功");
    }

    private void algorithmA() {
        List<Work> works = workService.getWorks();
        for (Work w : works) {
            if(w.isStatus()) continue;
            Partition header = Partition.getHeader();
            boolean start = false;
            for(Partition p = header; p != header | !start; p = p.getNext()) {
                start = true;
                if (p.getFreeSize() > w.getSize()) {
                    w.distribution(p.getId());
                    p.distribution(w);
                    break;
                }
            }
        }
        partitionService.show();
        workService.show();
    }

    private void algorithmB() {
        List<Work> works = workService.getWorks();
        Partition mark = Partition.getHeader();
        for (Work w : works) {
            if(w.isStatus()) continue;
            boolean start = false;
            for(Partition p = mark.getNext(); p != mark.getNext() | !start; p = p.getNext()) {
                start = true;
                if (p.getFreeSize() > w.getSize()) {
                    w.distribution(p.getId());
                    p.distribution(w);
                    mark = p;
                    break;
                }
            }
        }
        partitionService.show();
        workService.show();
    }

    private void algorithmC() {
        List<Work> works = workService.getWorks();
        Partition header = Partition.getHeader();
        for (Work w : works) {
            if(w.isStatus()) continue;
            boolean start = false;
            Partition mark = null;
            for(Partition p = header; p != header | !start; p = p.getNext()) {
                start = true;
                if (p.getFreeSize() > w.getSize()) {
                    if (mark==null) mark = p;
                    else if (p.getFreeSize() < mark.getFreeSize()) mark = p;
                }
            }
            if (mark!= null) {
                w.distribution(mark.getId());
                mark.distribution(w);
            }
        }
        partitionService.show();
        workService.show();
    }

    public static void main(String[] args) {
        PartitionService.getService().create();
        WorkService.getService().create();
        AlgorithmService.getService().algorithmC();
    }

}
