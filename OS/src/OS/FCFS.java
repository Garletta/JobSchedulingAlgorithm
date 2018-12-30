package OS;

import java.util.ArrayList;
import java.util.Comparator;

public class FCFS {

    private ArrayList<JCB> jobsOfFCFS;      //保存传参

    public FCFS(ArrayList<JCB> jobs) {
        super();
        jobsOfFCFS = jobs;
    }

    public ArrayList<JCB> FCFSAlgorithm() {
        //没有作业
        if(jobsOfFCFS.isEmpty()) {
            return jobsOfFCFS;
        }
        //按照作业的到达时间排序
        jobsOfFCFS.sort(new Comparator<JCB>() {
            @Override
            public int compare(JCB o1, JCB o2) {
                if(o1.getArriveTime() < o2.getArriveTime())
                    return -1;
                else if(o1.getArriveTime() > o2.getArriveTime())
                    return 1;
                else return 0;
            }
        });
        //用先来先服务算法调度作业
        for(int i = 0;i < jobsOfFCFS.size();i++) {
            if(i == 0) {    //第一个作业的开始时间就是其到达时间，等待时间为0
                jobsOfFCFS.get(0).setStartTime(jobsOfFCFS.get(0).getArriveTime());
                jobsOfFCFS.get(0).setWaitTime(0);
            } else {
                //如果下一个作业的到达时间大于上一个作业的完成时间（即下一个作业还没到达），就用它的到达时间作为它的开始时间
                //否则，下一个作业已到达，就用上一个作业的完成时间作为它的开始时间
                if (jobsOfFCFS.get(i - 1).getCompleteTime() < jobsOfFCFS.get(i).getArriveTime()) {
                    jobsOfFCFS.get(i).setStartTime(jobsOfFCFS.get(i).getArriveTime());
                } else {
                    jobsOfFCFS.get(i).setStartTime(jobsOfFCFS.get(i - 1).getCompleteTime());
                }
                //等待时间是开始时间减去到达时间
                jobsOfFCFS.get(i).setWaitTime(jobsOfFCFS.get(i).getStartTime() - jobsOfFCFS.get(i).getArriveTime());
            }
            //完成时间是开始时间加上服务时间
            jobsOfFCFS.get(i).setCompleteTime(jobsOfFCFS.get(i).getStartTime() + jobsOfFCFS.get(i).getServeTime());
            //周转时间是完成时间减去到达时间
            jobsOfFCFS.get(i).setCyclingTime(jobsOfFCFS.get(i).getCompleteTime() - jobsOfFCFS.get(i).getArriveTime());
        }
        return jobsOfFCFS;
    }
}
