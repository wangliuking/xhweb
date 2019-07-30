package xh.mybatis.bean;

public class PowerOffRes {
    private String bsId;
    private String name;
    private String powerOffTime;
    private String bsOffTime;
    private String calcTime;
    private String powerOffBatTime;
    private String powerOffBatVol;
    private String bsOffBatTime;
    private String bsOffBatVol;
    private String isBsUpdate;

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

    public String getPowerOffTime() {
        return powerOffTime;
    }

    public void setPowerOffTime(String powerOffTime) {
        this.powerOffTime = powerOffTime;
    }

    public String getBsOffTime() {
        return bsOffTime;
    }

    public void setBsOffTime(String bsOffTime) {
        this.bsOffTime = bsOffTime;
    }

    public String getCalcTime() {
        return calcTime;
    }

    public void setCalcTime(String calcTime) {
        this.calcTime = calcTime;
    }

    public String getPowerOffBatTime() {
        return powerOffBatTime;
    }

    public void setPowerOffBatTime(String powerOffBatTime) {
        this.powerOffBatTime = powerOffBatTime;
    }

    public String getPowerOffBatVol() {
        return powerOffBatVol;
    }

    public void setPowerOffBatVol(String powerOffBatVol) {
        this.powerOffBatVol = powerOffBatVol;
    }

    public String getBsOffBatTime() {
        return bsOffBatTime;
    }

    public void setBsOffBatTime(String bsOffBatTime) {
        this.bsOffBatTime = bsOffBatTime;
    }

    public String getBsOffBatVol() {
        return bsOffBatVol;
    }

    public void setBsOffBatVol(String bsOffBatVol) {
        this.bsOffBatVol = bsOffBatVol;
    }

    public String getIsBsUpdate() {
        return isBsUpdate;
    }

    public void setIsBsUpdate(String isBsUpdate) {
        this.isBsUpdate = isBsUpdate;
    }

    @Override
    public String toString() {
        return "PowerOffRes{" +
                "bsId='" + bsId + '\'' +
                ", name='" + name + '\'' +
                ", powerOffTime='" + powerOffTime + '\'' +
                ", bsOffTime='" + bsOffTime + '\'' +
                ", calcTime='" + calcTime + '\'' +
                ", powerOffBatTime='" + powerOffBatTime + '\'' +
                ", powerOffBatVol='" + powerOffBatVol + '\'' +
                ", bsOffBatTime='" + bsOffBatTime + '\'' +
                ", bsOffBatVol='" + bsOffBatVol + '\'' +
                ", isBsUpdate='" + isBsUpdate + '\'' +
                '}';
    }
}
