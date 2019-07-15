package xh.mybatis.bean;

public class CheckCutElecBean {
    private int bsId;
    private String name;
    private String to_bs_time;
    private String to_bs_level;
    private String isPower;
    private String power_time;
    private String reason;
    private String suggest;

    public int getBsId() {
        return bsId;
    }

    public void setBsId(int bsId) {
        this.bsId = bsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTo_bs_time() {
        return to_bs_time;
    }

    public void setTo_bs_time(String to_bs_time) {
        this.to_bs_time = to_bs_time;
    }

    public String getTo_bs_level() {
        return to_bs_level;
    }

    public void setTo_bs_level(String to_bs_level) {
        this.to_bs_level = to_bs_level;
    }

    public String getIsPower() {
        return isPower;
    }

    public void setIsPower(String isPower) {
        this.isPower = isPower;
    }

    public String getPower_time() {
        return power_time;
    }

    public void setPower_time(String power_time) {
        this.power_time = power_time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    @Override
    public String toString() {
        return "CheckCutElecBean{" +
                "bsId=" + bsId +
                ", name='" + name + '\'' +
                ", to_bs_time='" + to_bs_time + '\'' +
                ", to_bs_level='" + to_bs_level + '\'' +
                ", isPower='" + isPower + '\'' +
                ", power_time='" + power_time + '\'' +
                ", reason='" + reason + '\'' +
                ", suggest='" + suggest + '\'' +
                '}';
    }
}

