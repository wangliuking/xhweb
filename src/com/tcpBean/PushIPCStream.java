package com.tcpBean;

public class PushIPCStream {
    private String cmdtype = "pushIPCStream";
    private String userid;
    private String serialnumber;
    private String bsid;

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

    @Override
    public String toString() {
        return "GetPowerOnTime{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", bsid='" + bsid + '\'' +
                '}';
    }
}
