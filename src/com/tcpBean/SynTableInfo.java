package com.tcpBean;

public class SynTableInfo {
    private String cmdtype = "syntableinfo";
    private String userid;
    private String serialnumber;
    private String type;
    private String time;
    private String num;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "SynTableInfo{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
