package com.tcpBean;

import java.util.List;
import java.util.Map;

/**
 * 请求当月未巡基站返回
 */
public class GetUnInspectBsListAck {
    private String cmdtype = "getuninspectbslistack";
    private String userid;
    private String serialnumber;
    private String status;
    private List<Map<String,Object>> bslist;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Map<String, Object>> getBslist() {
        return bslist;
    }

    public void setBslist(List<Map<String, Object>> bslist) {
        this.bslist = bslist;
    }

    @Override
    public String toString() {
        return "GetInspectBsListAck{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", status='" + status + '\'' +
                ", bslist=" + bslist +
                '}';
    }
}
