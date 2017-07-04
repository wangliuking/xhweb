package xh.mybatis.bean;

import java.io.Serializable;
import java.util.Date;

public class RadioUserBusiness_E implements Serializable {
    private Integer id;

    private String name;

    private Integer ssid;

    private String ssname;

    private Integer dispatchpriority;

    private Integer pceenabled;

    private Integer tgenabled;

    private Integer mgenabled;

    private Integer mgemergencyenabled;

    private Integer dispatchpreempt;

    private Integer allsitesallowed;

    private Integer calledpreempt;

    private Integer usergroup;

    private Integer emergindcallenabled;

    private Integer emerggroupcallenabled;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSsid() {
        return ssid;
    }

    public void setSsid(Integer ssid) {
        this.ssid = ssid;
    }

    public String getSsname() {
        return ssname;
    }

    public void setSsname(String ssname) {
        this.ssname = ssname == null ? null : ssname.trim();
    }

    public Integer getDispatchpriority() {
        return dispatchpriority;
    }

    public void setDispatchpriority(Integer dispatchpriority) {
        this.dispatchpriority = dispatchpriority;
    }

    public Integer getPceenabled() {
        return pceenabled;
    }

    public void setPceenabled(Integer pceenabled) {
        this.pceenabled = pceenabled;
    }

    public Integer getTgenabled() {
        return tgenabled;
    }

    public void setTgenabled(Integer tgenabled) {
        this.tgenabled = tgenabled;
    }

    public Integer getMgenabled() {
        return mgenabled;
    }

    public void setMgenabled(Integer mgenabled) {
        this.mgenabled = mgenabled;
    }

    public Integer getMgemergencyenabled() {
        return mgemergencyenabled;
    }

    public void setMgemergencyenabled(Integer mgemergencyenabled) {
        this.mgemergencyenabled = mgemergencyenabled;
    }

    public Integer getDispatchpreempt() {
        return dispatchpreempt;
    }

    public void setDispatchpreempt(Integer dispatchpreempt) {
        this.dispatchpreempt = dispatchpreempt;
    }

    public Integer getAllsitesallowed() {
        return allsitesallowed;
    }

    public void setAllsitesallowed(Integer allsitesallowed) {
        this.allsitesallowed = allsitesallowed;
    }

    public Integer getCalledpreempt() {
        return calledpreempt;
    }

    public void setCalledpreempt(Integer calledpreempt) {
        this.calledpreempt = calledpreempt;
    }

    public Integer getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(Integer usergroup) {
        this.usergroup = usergroup;
    }

    public Integer getEmergindcallenabled() {
        return emergindcallenabled;
    }

    public void setEmergindcallenabled(Integer emergindcallenabled) {
        this.emergindcallenabled = emergindcallenabled;
    }

    public Integer getEmerggroupcallenabled() {
        return emerggroupcallenabled;
    }

    public void setEmerggroupcallenabled(Integer emerggroupcallenabled) {
        this.emerggroupcallenabled = emerggroupcallenabled;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}