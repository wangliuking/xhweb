package xh.mybatis.bean;

import java.io.Serializable;
import java.util.Date;

public class RadioDispatch implements Serializable {
    private Integer id;

    private String name;

    private String alias;

    private Integer mscid;

    private Long vpnid;

    private Integer usertype;

    private String password;

    private Integer vaid;

    private Integer said;

    private Integer masterid;

    private Integer fullduple;

    private Integer ambienceinitiation;

    private Integer clir;

    private Integer cliroverride;

    private String saname;

    private Integer supervisorstatus;

    private Integer dispatchertype;

    private Integer code;

    private Integer dispatchnum;

    private String dialstring;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public Integer getMscid() {
        return mscid;
    }

    public void setMscid(Integer mscid) {
        this.mscid = mscid;
    }

    public Long getVpnid() {
        return vpnid;
    }

    public void setVpnid(Long vpnid) {
        this.vpnid = vpnid;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getVaid() {
        return vaid;
    }

    public void setVaid(Integer vaid) {
        this.vaid = vaid;
    }

    public Integer getSaid() {
        return said;
    }

    public void setSaid(Integer said) {
        this.said = said;
    }

    public Integer getMasterid() {
        return masterid;
    }

    public void setMasterid(Integer masterid) {
        this.masterid = masterid;
    }

    public Integer getFullduple() {
        return fullduple;
    }

    public void setFullduple(Integer fullduple) {
        this.fullduple = fullduple;
    }

    public Integer getAmbienceinitiation() {
        return ambienceinitiation;
    }

    public void setAmbienceinitiation(Integer ambienceinitiation) {
        this.ambienceinitiation = ambienceinitiation;
    }

    public Integer getClir() {
        return clir;
    }

    public void setClir(Integer clir) {
        this.clir = clir;
    }

    public Integer getCliroverride() {
        return cliroverride;
    }

    public void setCliroverride(Integer cliroverride) {
        this.cliroverride = cliroverride;
    }

    public String getSaname() {
        return saname;
    }

    public void setSaname(String saname) {
        this.saname = saname == null ? null : saname.trim();
    }

    public Integer getSupervisorstatus() {
        return supervisorstatus;
    }

    public void setSupervisorstatus(Integer supervisorstatus) {
        this.supervisorstatus = supervisorstatus;
    }

    public Integer getDispatchertype() {
        return dispatchertype;
    }

    public void setDispatchertype(Integer dispatchertype) {
        this.dispatchertype = dispatchertype;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getDispatchnum() {
        return dispatchnum;
    }

    public void setDispatchnum(Integer dispatchnum) {
        this.dispatchnum = dispatchnum;
    }

    public String getDialstring() {
        return dialstring;
    }

    public void setDialstring(String dialstring) {
        this.dialstring = dialstring == null ? null : dialstring.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}