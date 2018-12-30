package OS;

public class JCB {

    private int jobSeq;         //作业序号
    private String jobName;     //作业名
    private double arriveTime;  //到达时间
    private double serveTime;   //服务时间
    private double prior;       //优先级

    private double startTime;   //开始时间
    private double waitTime;    //等待时间
    private double completeTime;//结束时间
    private double cyclingTime; //周转时间

    public JCB(int jobSeq, String jobName, double arriveTime, double serveTime, double prior) {
        this.jobSeq = jobSeq;
        this.jobName = jobName;
        this.arriveTime = arriveTime;
        this.serveTime = serveTime;
        this.prior = prior;
    }

    public JCB(int jobSeq, String jobName, double arriveTime, double serveTime, double prior, double startTime, double waitTime, double completeTime, double cyclingTime) {
        this.jobSeq = jobSeq;
        this.jobName = jobName;
        this.arriveTime = arriveTime;
        this.serveTime = serveTime;
        this.prior = prior;
        this.startTime = startTime;
        this.waitTime = waitTime;
        this.completeTime = completeTime;
        this.cyclingTime = cyclingTime;
    }

    public int getJobSeq() {
        return jobSeq;
    }

    public String getJobName() {
        return jobName;
    }

    public double getArriveTime() {
        return arriveTime;
    }

    public double getServeTime() {
        return serveTime;
    }

    public double getPrior() {
        return prior;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public double getCompleteTime() {
        return completeTime;
    }

    public double getCyclingTime() {
        return cyclingTime;
    }

    public void setJobSeq(int jobSeq) {
        this.jobSeq = jobSeq;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setArriveTime(double arriveTime) {
        this.arriveTime = arriveTime;
    }

    public void setServeTime(double serveTime) {
        this.serveTime = serveTime;
    }

    public void setPrior(double prior) {
        this.prior = prior;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public void setCompleteTime(double completeTime) {
        this.completeTime = completeTime;
    }

    public void setCyclingTime(double cyclingTime) {
        this.cyclingTime = cyclingTime;
    }
}
