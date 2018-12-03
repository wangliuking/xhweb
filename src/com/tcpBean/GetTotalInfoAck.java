package com.tcpBean;

/**
 * 返回统计信息
 */
public class GetTotalInfoAck {
    private String cmdtype = "gettotalinfoack";
    private String userid;
    private String serialnumber;
    private String bsnum;
    private String errbsnum;
    private String inspectbsnum;
    private String uninspectbsnum;
    private String appnum;

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

    public String getBsnum() {
        return bsnum;
    }

    public void setBsnum(String bsnum) {
        this.bsnum = bsnum;
    }

    public String getErrbsnum() {
        return errbsnum;
    }

    public void setErrbsnum(String errbsnum) {
        this.errbsnum = errbsnum;
    }

    public String getInspectbsnum() {
        return inspectbsnum;
    }

    public void setInspectbsnum(String inspectbsnum) {
        this.inspectbsnum = inspectbsnum;
    }

    public String getUninspectbsnum() {
        return uninspectbsnum;
    }

    public void setUninspectbsnum(String uninspectbsnum) {
        this.uninspectbsnum = uninspectbsnum;
    }

    public String getAppnum() {
        return appnum;
    }

    public void setAppnum(String appnum) {
        this.appnum = appnum;
    }

    @Override
    public String toString() {
        return "GetTotalInfoAck{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", bsnum='" + bsnum + '\'' +
                ", errbsnum='" + errbsnum + '\'' +
                ", inspectbsnum='" + inspectbsnum + '\'' +
                ", uninspectbsnum='" + uninspectbsnum + '\'' +
                ", appnum='" + appnum + '\'' +
                '}';
    }
}
