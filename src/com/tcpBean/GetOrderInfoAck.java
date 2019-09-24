package com.tcpBean;

import java.util.List;

public class GetOrderInfoAck {
    private String cmdtype = "getorderinfoack";
    private String userid;
    private String serialnumber;
    private String bsname;
    private String bsid;
    private String bsposition;
    private String dispatchtime;
    private String dispatchman;
    private String powerofftime;
    private String remarka;
    private String workman;
    private String prostate;
    private String genontime;
    private String genv;
    private String geni;
    private String address;
    private String auditor1;
    private String auditor2;
    private String audittime1;
    private String audittime2;
    private List<String> genonpiclist;
    private String remarkgenon;
    private String powerontime;
    private String genofftime;
    private String removegentime;
    private List<String> genoffpiclist;
    private String remarkgenoff;
    private String gen_on_pic;
    private String gen_off_pic;
    private String handlepower;
    private String ack;

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

    public String getBsname() {
        return bsname;
    }

    public void setBsname(String bsname) {
        this.bsname = bsname;
    }

    public String getBsid() {
        return bsid;
    }

    public void setBsid(String bsid) {
        this.bsid = bsid;
    }

    public String getBsposition() {
        return bsposition;
    }

    public void setBsposition(String bsposition) {
        this.bsposition = bsposition;
    }

    public String getDispatchtime() {
        return dispatchtime;
    }

    public void setDispatchtime(String dispatchtime) {
        this.dispatchtime = dispatchtime;
    }

    public String getDispatchman() {
        return dispatchman;
    }

    public void setDispatchman(String dispatchman) {
        this.dispatchman = dispatchman;
    }

    public String getPowerofftime() {
        return powerofftime;
    }

    public void setPowerofftime(String powerofftime) {
        this.powerofftime = powerofftime;
    }

    public String getRemarka() {
        return remarka;
    }

    public void setRemarka(String remarka) {
        this.remarka = remarka;
    }

    public String getWorkman() {
        return workman;
    }

    public void setWorkman(String workman) {
        this.workman = workman;
    }

    public String getProstate() {
        return prostate;
    }

    public void setProstate(String prostate) {
        this.prostate = prostate;
    }

    public String getGenontime() {
        return genontime;
    }

    public void setGenontime(String genontime) {
        this.genontime = genontime;
    }

    public String getGenv() {
        return genv;
    }

    public void setGenv(String genv) {
        this.genv = genv;
    }

    public String getGeni() {
        return geni;
    }

    public void setGeni(String geni) {
        this.geni = geni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getGenonpiclist() {
        return genonpiclist;
    }

    public void setGenonpiclist(List<String> genonpiclist) {
        this.genonpiclist = genonpiclist;
    }

    public String getRemarkgenon() {
        return remarkgenon;
    }

    public void setRemarkgenon(String remarkgenon) {
        this.remarkgenon = remarkgenon;
    }

    public String getPowerontime() {
        return powerontime;
    }

    public void setPowerontime(String powerontime) {
        this.powerontime = powerontime;
    }

    public String getGenofftime() {
        return genofftime;
    }

    public void setGenofftime(String genofftime) {
        this.genofftime = genofftime;
    }

    public String getRemovegentime() {
        return removegentime;
    }

    public void setRemovegentime(String removegentime) {
        this.removegentime = removegentime;
    }

    public List<String> getGenoffpiclist() {
        return genoffpiclist;
    }

    public void setGenoffpiclist(List<String> genoffpiclist) {
        this.genoffpiclist = genoffpiclist;
    }

    public String getRemarkgenoff() {
        return remarkgenoff;
    }

    public void setRemarkgenoff(String remarkgenoff) {
        this.remarkgenoff = remarkgenoff;
    }

    public String getGen_on_pic() {
        return gen_on_pic;
    }

    public void setGen_on_pic(String gen_on_pic) {
        this.gen_on_pic = gen_on_pic;
    }

    public String getGen_off_pic() {
        return gen_off_pic;
    }

    public void setGen_off_pic(String gen_off_pic) {
        this.gen_off_pic = gen_off_pic;
    }

    public String getHandlepower() {
        return handlepower;
    }

    public void setHandlepower(String handlepower) {
        this.handlepower = handlepower;
    }

    public String getAuditor1() {
        return auditor1;
    }

    public void setAuditor1(String auditor1) {
        this.auditor1 = auditor1;
    }

    public String getAuditor2() {
        return auditor2;
    }

    public void setAuditor2(String auditor2) {
        this.auditor2 = auditor2;
    }

    public String getAudittime1() {
        return audittime1;
    }

    public void setAudittime1(String audittime1) {
        this.audittime1 = audittime1;
    }

    public String getAudittime2() {
        return audittime2;
    }

    public void setAudittime2(String audittime2) {
        this.audittime2 = audittime2;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    @Override
    public String toString() {
        return "GetOrderInfoAck{" +
                "cmdtype='" + cmdtype + '\'' +
                ", userid='" + userid + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", bsname='" + bsname + '\'' +
                ", bsid='" + bsid + '\'' +
                ", bsposition='" + bsposition + '\'' +
                ", dispatchtime='" + dispatchtime + '\'' +
                ", dispatchman='" + dispatchman + '\'' +
                ", powerofftime='" + powerofftime + '\'' +
                ", remarka='" + remarka + '\'' +
                ", workman='" + workman + '\'' +
                ", prostate='" + prostate + '\'' +
                ", genontime='" + genontime + '\'' +
                ", genv='" + genv + '\'' +
                ", geni='" + geni + '\'' +
                ", address='" + address + '\'' +
                ", auditor1='" + auditor1 + '\'' +
                ", auditor2='" + auditor2 + '\'' +
                ", audittime1='" + audittime1 + '\'' +
                ", audittime2='" + audittime2 + '\'' +
                ", genonpiclist=" + genonpiclist +
                ", remarkgenon='" + remarkgenon + '\'' +
                ", powerontime='" + powerontime + '\'' +
                ", genofftime='" + genofftime + '\'' +
                ", removegentime='" + removegentime + '\'' +
                ", genoffpiclist=" + genoffpiclist +
                ", remarkgenoff='" + remarkgenoff + '\'' +
                ", gen_on_pic='" + gen_on_pic + '\'' +
                ", gen_off_pic='" + gen_off_pic + '\'' +
                ", handlepower='" + handlepower + '\'' +
                ", ack='" + ack + '\'' +
                '}';
    }
}
