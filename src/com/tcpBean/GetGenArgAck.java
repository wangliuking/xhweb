package com.tcpBean;

public class GetGenArgAck {
    private String cmdtype = "getgenargack";
    private String userid;
    private String serialnumber;
    private String bsid;
    private String genv;
    private String geni;
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

    public String getGenv() {
        return genv;
    }

    public void setGenv(String genv) {
        this.genv = genv;
    }

    public String getGeni() {
        return geni;
    }

    public void setGeni(String geni) {
        this.geni = geni;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    @Override
    public String toString() {
        return "GetGenArgAck{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", bsid='" + bsid + '\'' +
                ", genv='" + genv + '\'' +
                ", geni='" + geni + '\'' +
                ", ack='" + ack + '\'' +
                '}';
    }
}
