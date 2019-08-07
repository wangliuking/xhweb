package xh.mybatis.bean;

public class PowerOffRes {
    private String bsId;
    private String name;
    private String bsOffTime;
    private String bsOnTime;
    private String powerOffTime;
    private String powerOnTime;
    private String powerOffVol;
    private String powerOnVol;
    private String calcTime;
    private String finalTime;

    public String getBsId() {
        return bsId;
    }

    public void setBsId(String bsId) {
        this.bsId = bsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBsOffTime() {
        return bsOffTime;
    }

    public void setBsOffTime(String bsOffTime) {
        this.bsOffTime = bsOffTime;
    }

    public String getBsOnTime() {
        return bsOnTime;
    }

    public void setBsOnTime(String bsOnTime) {
        this.bsOnTime = bsOnTime;
    }

    public String getPowerOffTime() {
        return powerOffTime;
    }

    public void setPowerOffTime(String powerOffTime) {
        this.powerOffTime = powerOffTime;
    }

    public String getPowerOnTime() {
        return powerOnTime;
    }

    public void setPowerOnTime(String powerOnTime) {
        this.powerOnTime = powerOnTime;
    }

    public String getPowerOffVol() {
        return powerOffVol;
    }

    public void setPowerOffVol(String powerOffVol) {
        this.powerOffVol = powerOffVol;
    }

    public String getPowerOnVol() {
        return powerOnVol;
    }

    public void setPowerOnVol(String powerOnVol) {
        this.powerOnVol = powerOnVol;
    }

    public String getCalcTime() {
        return calcTime;
    }

    public void setCalcTime(String calcTime) {
        this.calcTime = calcTime;
    }

    public String getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    @Override
    public String toString() {
        return "PowerOffRes{" +
                "bsId='" + bsId + '\'' +
                ", name='" + name + '\'' +
                ", bsOffTime='" + bsOffTime + '\'' +
                ", bsOnTime='" + bsOnTime + '\'' +
                ", powerOffTime='" + powerOffTime + '\'' +
                ", powerOnTime='" + powerOnTime + '\'' +
                ", powerOffVol='" + powerOffVol + '\'' +
                ", powerOnVol='" + powerOnVol + '\'' +
                ", calcTime='" + calcTime + '\'' +
                ", finalTime='" + finalTime + '\'' +
                '}';
    }
}
