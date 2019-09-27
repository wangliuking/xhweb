package com.tcpBean;

public class ReceiveTableAck {
    private String cmdtype = "receivetableack";
    private String userid;
    private String serialnumber;
    private String handleusername;
    private String handlepower;

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

    public String getHandlepower() {
        return handlepower;
    }

    public void setHandlepower(String handlepower) {
        this.handlepower = handlepower;
    }

    public String getHandleusername() {
        return handleusername;
    }

    public void setHandleusername(String handleusername) {
        this.handleusername = handleusername;
    }

    @Override
    public String toString() {
        return "ReceiveTableAck{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", handleusername='" + handleusername + '\'' +
                ", handlepower='" + handlepower + '\'' +
                '}';
    }
}
