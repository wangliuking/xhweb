package xh.mybatis.bean;

import java.io.Serializable;
import java.util.Date;

public class RadioDispatchBusiness implements Serializable {
    private Integer id;

    private String name;

    private Integer monitoron;

    private Integer pcpreempt;

    private Integer callpriority;

    private Integer allmute;

    private Integer allmutetimeout;

    private Integer pttpriority;

    private Date time;

    private Integer usergroup;

    private Integer emergindcallenabled;

    private Integer emerggroupcallenabled;

    private Integer prohibittone;

    private Integer sidetone;

    private Integer patchgroupnum;

    private Integer msgroupnum;

    private Integer apbnum;

    private Integer calledpreempt;

    private Integer inboundcall;

    private Integer inboundptt;

    private Integer instanttransmit;

    private Integer patchpc;

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

    public Integer getMonitoron() {
        return monitoron;
    }

    public void setMonitoron(Integer monitoron) {
        this.monitoron = monitoron;
    }

    public Integer getPcpreempt() {
        return pcpreempt;
    }

    public void setPcpreempt(Integer pcpreempt) {
        this.pcpreempt = pcpreempt;
    }

    public Integer getCallpriority() {
        return callpriority;
    }

    public void setCallpriority(Integer callpriority) {
        this.callpriority = callpriority;
    }

    public Integer getAllmute() {
        return allmute;
    }

    public void setAllmute(Integer allmute) {
        this.allmute = allmute;
    }

    public Integer getAllmutetimeout() {
        return allmutetimeout;
    }

    public void setAllmutetimeout(Integer allmutetimeout) {
        this.allmutetimeout = allmutetimeout;
    }

    public Integer getPttpriority() {
        return pttpriority;
    }

    public void setPttpriority(Integer pttpriority) {
        this.pttpriority = pttpriority;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public Integer getProhibittone() {
        return prohibittone;
    }

    public void setProhibittone(Integer prohibittone) {
        this.prohibittone = prohibittone;
    }

    public Integer getSidetone() {
        return sidetone;
    }

    public void setSidetone(Integer sidetone) {
        this.sidetone = sidetone;
    }

    public Integer getPatchgroupnum() {
        return patchgroupnum;
    }

    public void setPatchgroupnum(Integer patchgroupnum) {
        this.patchgroupnum = patchgroupnum;
    }

    public Integer getMsgroupnum() {
        return msgroupnum;
    }

    public void setMsgroupnum(Integer msgroupnum) {
        this.msgroupnum = msgroupnum;
    }

    public Integer getApbnum() {
        return apbnum;
    }

    public void setApbnum(Integer apbnum) {
        this.apbnum = apbnum;
    }

    public Integer getCalledpreempt() {
        return calledpreempt;
    }

    public void setCalledpreempt(Integer calledpreempt) {
        this.calledpreempt = calledpreempt;
    }

    public Integer getInboundcall() {
        return inboundcall;
    }

    public void setInboundcall(Integer inboundcall) {
        this.inboundcall = inboundcall;
    }

    public Integer getInboundptt() {
        return inboundptt;
    }

    public void setInboundptt(Integer inboundptt) {
        this.inboundptt = inboundptt;
    }

    public Integer getInstanttransmit() {
        return instanttransmit;
    }

    public void setInstanttransmit(Integer instanttransmit) {
        this.instanttransmit = instanttransmit;
    }

    public Integer getPatchpc() {
        return patchpc;
    }

    public void setPatchpc(Integer patchpc) {
        this.patchpc = patchpc;
    }
}