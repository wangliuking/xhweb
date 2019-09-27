package com.tcpBean;

public class ReceiveTable {
    private String cmdtype = "receivetable";
    private String userid;
    private String serialnumber;
    private String handleusername;

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

    public String getHandleusername() {
        return handleusername;
    }

    public void setHandleusername(String handleusername) {
        this.handleusername = handleusername;
    }

    @Override
    public String toString() {
        return "ReceiveTable{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", handleusername='" + handleusername + '\'' +
                '}';
    }
}
