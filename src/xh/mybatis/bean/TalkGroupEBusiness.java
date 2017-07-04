package xh.mybatis.bean;

import java.io.Serializable;
import java.util.Date;

public class TalkGroupEBusiness implements Serializable {
    private Integer id;

    private String name;

    private Integer messagetransmission;

    private Integer busyoverride;

    private Integer tgenabled;

    private Integer emergencycall;

    private Integer emergencyatnvs;

    private Integer dispatchpriority;

    private Integer prioritymonitor;

    private Integer inactivitytime;

    private Integer callswithoutmgeg;

    private Integer audiointerrupt;

    private Integer tgdatapreempt;

    private Integer usergroup;

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

    public Integer getMessagetransmission() {
        return messagetransmission;
    }

    public void setMessagetransmission(Integer messagetransmission) {
        this.messagetransmission = messagetransmission;
    }

    public Integer getBusyoverride() {
        return busyoverride;
    }

    public void setBusyoverride(Integer busyoverride) {
        this.busyoverride = busyoverride;
    }

    public Integer getTgenabled() {
        return tgenabled;
    }

    public void setTgenabled(Integer tgenabled) {
        this.tgenabled = tgenabled;
    }

    public Integer getEmergencycall() {
        return emergencycall;
    }

    public void setEmergencycall(Integer emergencycall) {
        this.emergencycall = emergencycall;
    }

    public Integer getEmergencyatnvs() {
        return emergencyatnvs;
    }

    public void setEmergencyatnvs(Integer emergencyatnvs) {
        this.emergencyatnvs = emergencyatnvs;
    }

    public Integer getDispatchpriority() {
        return dispatchpriority;
    }

    public void setDispatchpriority(Integer dispatchpriority) {
        this.dispatchpriority = dispatchpriority;
    }

    public Integer getPrioritymonitor() {
        return prioritymonitor;
    }

    public void setPrioritymonitor(Integer prioritymonitor) {
        this.prioritymonitor = prioritymonitor;
    }

    public Integer getInactivitytime() {
        return inactivitytime;
    }

    public void setInactivitytime(Integer inactivitytime) {
        this.inactivitytime = inactivitytime;
    }

    public Integer getCallswithoutmgeg() {
        return callswithoutmgeg;
    }

    public void setCallswithoutmgeg(Integer callswithoutmgeg) {
        this.callswithoutmgeg = callswithoutmgeg;
    }

    public Integer getAudiointerrupt() {
        return audiointerrupt;
    }

    public void setAudiointerrupt(Integer audiointerrupt) {
        this.audiointerrupt = audiointerrupt;
    }

    public Integer getTgdatapreempt() {
        return tgdatapreempt;
    }

    public void setTgdatapreempt(Integer tgdatapreempt) {
        this.tgdatapreempt = tgdatapreempt;
    }

    public Integer getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(Integer usergroup) {
        this.usergroup = usergroup;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}