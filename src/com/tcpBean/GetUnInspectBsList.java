package com.tcpBean;

/**
 * 请求当月未巡基站
 */
public class GetUnInspectBsList {
    private String cmdtype = "getuninspectbslist";
    private String userid;
    private String serialnumber;

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

    @Override
    public String toString() {
        return "GetInspectBsList{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                '}';
    }
}