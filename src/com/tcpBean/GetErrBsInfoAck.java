package com.tcpBean;

import java.util.List;
import java.util.Map;

/**
 *  请求所有断站信息响应
 */

public class GetErrBsInfoAck {
    private String cmdtype = "geterrbsinfoack";
    private String userid;
    private String serialnumber;
    private List<Map<String,Object>> bslist;
    private String status;

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

    public List<Map<String, Object>> getBslist() {
        return bslist;
    }

    public void setBslist(List<Map<String, Object>> bslist) {
        this.bslist = bslist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GetErrBsInfoAck{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", bslist=" + bslist +
                ", status='" + status + '\'' +
                '}';
    }
}
