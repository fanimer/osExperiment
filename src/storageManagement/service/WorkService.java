package storageManagement.service;

import storageManagement.entity.Work;

import java.util.ArrayList;
import java.util.List;

public class WorkService {
    private static WorkService service;
    private boolean status = false;
    private List<Work> works = new ArrayList<>();

    public List<Work> getWorks() {
        return works;
    }

    public static WorkService getService() {
        if(service == null) service = new WorkService();
        return service;
    }

    public void clear() {
        status = false;
        Work.setCount(0);
        works = new ArrayList<>();
    }

    public boolean isStatus() {
        return status;
    }

    public void create() {
        int i = 0;
        while (i < 10) {
            works.add(new Work());
            i++;
        }
        status = true;
        show();
    }

    public void show() {
        System.out.println("--------------------------------");
        System.out.println("|ID  作业大小    对应区块     状态");
        for(Work w : works) {
            System.out.println(w);
        }
        System.out.println("--------------------------------");
    }

    public static void main(String[] args) {
        WorkService.getService().create();
    }
}
