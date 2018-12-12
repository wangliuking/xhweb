package com.tcpBean;

public class GetPowerOnTimeAck {
    private String cmdtype = "getpowerontimeack";
    private String userid;
    private String serialnumber;
    private String bsid;
    private String powerontime;
    private String genofftime;
    private String ack;

    public String getCmdtype() {
        return cmdtype;
    }

    public void setCmdtype(String cmdtype) {
        this.cmdtype = cmdtype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getBsid() {
        return bsid;
    }

    public void setBsid(String bsid) {
        this.bsid = bsid;
    }

    public String getPowerontime() {
        return powerontime;
    }

    public void setPowerontime(String powerontime) {
        this.powerontime = powerontime;
    }

    public String getGenofftime() {
        return genofftime;
    }

    public void setGenofftime(String genofftime) {
        this.genofftime = genofftime;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    @Override
    public String toString() {
        return "GetPowerOnTimeAck{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", bsid='" + bsid + '\'' +
                ", powerontime='" + powerontime + '\'' +
                ", genofftime='" + genofftime + '\'' +
                ", ack='" + ack + '\'' +
                '}';
    }
}
