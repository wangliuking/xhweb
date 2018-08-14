package xh.mybatis.bean;

public class OptimizeChangeSheet {
    private int id;
    private String optimizeChangeTime;
    private String bsAddress;
    private String optimizeChangeType;
    private String optimizeReason;
    private String processAndResult;
    private String optimizeChangeNote;
    private String excPersion;
    private String serialNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOptimizeChangeTime() {
        return optimizeChangeTime;
    }

    public void setOptimizeChangeTime(String optimizeChangeTime) {
        this.optimizeChangeTime = optimizeChangeTime;
    }

    public String getBsAddress() {
        return bsAddress;
    }

    public void setBsAddress(String bsAddress) {
        this.bsAddress = bsAddress;
    }

    public String getOptimizeChangeType() {
        return optimizeChangeType;
    }

    public void setOptimizeChangeType(String optimizeChangeType) {
        this.optimizeChangeType = optimizeChangeType;
    }

    public String getOptimizeReason() {
        return optimizeReason;
    }

    public void setOptimizeReason(String optimizeReason) {
        this.optimizeReason = optimizeReason;
    }

    public String getProcessAndResult() {
        return processAndResult;
    }

    public void setProcessAndResult(String processAndResult) {
        this.processAndResult = processAndResult;
    }

    public String getOptimizeChangeNote() {
        return optimizeChangeNote;
    }

    public void setOptimizeChangeNote(String optimizeChangeNote) {
        this.optimizeChangeNote = optimizeChangeNote;
    }

    public String getExcPersion() {
        return excPersion;
    }

    public void setExcPersion(String excPersion) {
        this.excPersion = excPersion;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "OptimizeChangeSheet{" +
                "id=" + id +
                ", optimizeChangeTime='" + optimizeChangeTime + '\'' +
                ", bsAddress='" + bsAddress + '\'' +
                ", optimizeChangeType='" + optimizeChangeType + '\'' +
                ", optimizeReason='" + optimizeReason + '\'' +
                ", processAndResult='" + processAndResult + '\'' +
                ", optimizeChangeNote='" + optimizeChangeNote + '\'' +
                ", excPersion='" + excPersion + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
