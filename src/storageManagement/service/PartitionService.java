package storageManagement.service;

import storageManagement.entity.Partition;

public class PartitionService {
    private static PartitionService service;
    private boolean status = false;

    public static PartitionService getService() {
        if (service == null) service = new PartitionService();
        return service;
    }

    public boolean isStatus() {
        return status;
    }


    // 清空分区
    public void clear() {
        status = false;
        Partition.clear();
    }

    // 重置分区
    public void reset() {
        Partition header = Partition.getHeader();
        boolean start = false;
        for(Partition p = header; p != header | !start; p = p.getNext()) {
            start = true;
            p.reset();
        }
        show();
    }

    // 创建分区
    public void create() {
        int i = 0;
        Partition.setHeader(new Partition());
        Partition p = Partition.getHeader();
        while (++i < 8) {
            Partition q = new Partition();
            p.setNext(q);
            p = q;
        }
        p.setNext(Partition.getHeader());
        status = true;
        show();
    }

    // 展示分区
    public void show() {
        System.out.println("-------------------------------------------------------");
        System.out.println("ID  原始空间大小   剩余空间大小   首地址        分配的作业");
        Partition header = Partition.getHeader();
        boolean start = false;
        for(Partition p = header; p != header | !start; p = p.getNext()) {
            start = true;
            System.out.println(p);
        }
        System.out.println("-------------------------------------------------------");
    }

    public static void main(String[] args) {
        PartitionService.getService().create();
    }
}
