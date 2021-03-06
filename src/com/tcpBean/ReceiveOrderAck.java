package com.tcpBean;

public class ReceiveOrderAck {
    private String cmdtype = "receiveorderack";
    private String userid;
    private String serialnumber;
    private String handlepower;
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
        return "ReceiveOrderAck{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", handlepower='" + handlepower + '\'' +
                ", handleusername='" + handleusername + '\'' +
                '}';
    }
}
