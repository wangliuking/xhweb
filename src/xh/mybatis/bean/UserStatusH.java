package xh.mybatis.bean;

import java.io.Serializable;
import java.util.Date;

public class UserStatusH implements Serializable {
    private Integer id;

    private Integer userid;

    private Integer bsid;

    private String ialist;

    private Date time;

    private Integer regstatus;

    private Integer callstatus;

    private Integer tgid;

    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getBsid() {
        return bsid;
    }

    public void setBsid(Integer bsid) {
        this.bsid = bsid;
    }

    public String getIalist() {
        return ialist;
    }

    public void setIalist(String ialist) {
        this.ialist = ialist == null ? null : ialist.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getRegstatus() {
        return regstatus;
    }

    public void setRegstatus(Integer regstatus) {
        this.regstatus = regstatus;
    }

    public Integer getCallstatus() {
        return callstatus;
    }

    public void setCallstatus(Integer callstatus) {
        this.callstatus = callstatus;
    }

    public Integer getTgid() {
        return tgid;
    }

    public void setTgid(Integer tgid) {
        this.tgid = tgid;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}