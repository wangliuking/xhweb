package xh.mybatis.bean;

import java.io.Serializable;
import java.util.Date;

public class MultiGroup implements Serializable{
	private Integer id;

    private Integer cMultigroupid;
	
    private String mMultigroupalias;

    private String mSecuritygroup;

    private String mConsoletgmgcapabilityprofilealias;

    private String mTgmgcapabilityprofilealias;

    private String mTgmgvalidsitesprofilealias;

    private Boolean mPreemptcapable;

    private Boolean mInterruptorwaitmode;

    private Boolean mExtendedbandchannelscanbeassigned;

    private Boolean mGroupenable;

    private String eName;

    private String eAlias;

    private Integer eMscid;

    private Long eVpnid;

    private Integer eSaid;

    private Integer eIaid;

    private Integer eVaid;

    private Boolean ePreempt;

    private Integer eRadiotype;

    private Boolean eEnabled;

    private String eDirectdial;

    private Integer interruptwait;

    private Integer pdttype;

    private Integer nptype;

    private Date time;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getcMultigroupid() {
		return cMultigroupid;
	}

	public void setcMultigroupid(Integer cMultigroupid) {
		this.cMultigroupid = cMultigroupid;
	}

	public String getmMultigroupalias() {
        return mMultigroupalias;
    }

    public void setmMultigroupalias(String mMultigroupalias) {
        this.mMultigroupalias = mMultigroupalias == null ? null : mMultigroupalias.trim();
    }

    public String getmSecuritygroup() {
        return mSecuritygroup;
    }

    public void setmSecuritygroup(String mSecuritygroup) {
        this.mSecuritygroup = mSecuritygroup == null ? null : mSecuritygroup.trim();
    }

    public String getmConsoletgmgcapabilityprofilealias() {
        return mConsoletgmgcapabilityprofilealias;
    }

    public void setmConsoletgmgcapabilityprofilealias(String mConsoletgmgcapabilityprofilealias) {
        this.mConsoletgmgcapabilityprofilealias = mConsoletgmgcapabilityprofilealias == null ? null : mConsoletgmgcapabilityprofilealias.trim();
    }

    public String getmTgmgcapabilityprofilealias() {
        return mTgmgcapabilityprofilealias;
    }

    public void setmTgmgcapabilityprofilealias(String mTgmgcapabilityprofilealias) {
        this.mTgmgcapabilityprofilealias = mTgmgcapabilityprofilealias == null ? null : mTgmgcapabilityprofilealias.trim();
    }

    public String getmTgmgvalidsitesprofilealias() {
        return mTgmgvalidsitesprofilealias;
    }

    public void setmTgmgvalidsitesprofilealias(String mTgmgvalidsitesprofilealias) {
        this.mTgmgvalidsitesprofilealias = mTgmgvalidsitesprofilealias == null ? null : mTgmgvalidsitesprofilealias.trim();
    }

    public Boolean getmPreemptcapable() {
        return mPreemptcapable;
    }

    public void setmPreemptcapable(Boolean mPreemptcapable) {
        this.mPreemptcapable = mPreemptcapable;
    }

    public Boolean getmInterruptorwaitmode() {
        return mInterruptorwaitmode;
    }

    public void setmInterruptorwaitmode(Boolean mInterruptorwaitmode) {
        this.mInterruptorwaitmode = mInterruptorwaitmode;
    }

    public Boolean getmExtendedbandchannelscanbeassigned() {
        return mExtendedbandchannelscanbeassigned;
    }

    public void setmExtendedbandchannelscanbeassigned(Boolean mExtendedbandchannelscanbeassigned) {
        this.mExtendedbandchannelscanbeassigned = mExtendedbandchannelscanbeassigned;
    }

    public Boolean getmGroupenable() {
        return mGroupenable;
    }

    public void setmGroupenable(Boolean mGroupenable) {
        this.mGroupenable = mGroupenable;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName == null ? null : eName.trim();
    }

    public String geteAlias() {
        return eAlias;
    }

    public void seteAlias(String eAlias) {
        this.eAlias = eAlias == null ? null : eAlias.trim();
    }

    public Integer geteMscid() {
        return eMscid;
    }

    public void seteMscid(Integer eMscid) {
        this.eMscid = eMscid;
    }

    public Long geteVpnid() {
        return eVpnid;
    }

    public void seteVpnid(Long eVpnid) {
        this.eVpnid = eVpnid;
    }

    public Integer geteSaid() {
        return eSaid;
    }

    public void seteSaid(Integer eSaid) {
        this.eSaid = eSaid;
    }

    public Integer geteIaid() {
        return eIaid;
    }

    public void seteIaid(Integer eIaid) {
        this.eIaid = eIaid;
    }

    public Integer geteVaid() {
        return eVaid;
    }

    public void seteVaid(Integer eVaid) {
        this.eVaid = eVaid;
    }

    public Boolean getePreempt() {
        return ePreempt;
    }

    public void setePreempt(Boolean ePreempt) {
        this.ePreempt = ePreempt;
    }

    public Integer geteRadiotype() {
        return eRadiotype;
    }

    public void seteRadiotype(Integer eRadiotype) {
        this.eRadiotype = eRadiotype;
    }

    public Boolean geteEnabled() {
        return eEnabled;
    }

    public void seteEnabled(Boolean eEnabled) {
        this.eEnabled = eEnabled;
    }

    public String geteDirectdial() {
        return eDirectdial;
    }

    public void seteDirectdial(String eDirectdial) {
        this.eDirectdial = eDirectdial == null ? null : eDirectdial.trim();
    }

    public Integer getInterruptwait() {
        return interruptwait;
    }

    public void setInterruptwait(Integer interruptwait) {
        this.interruptwait = interruptwait;
    }

    public Integer getPdttype() {
        return pdttype;
    }

    public void setPdttype(Integer pdttype) {
        this.pdttype = pdttype;
    }

    public Integer getNptype() {
        return nptype;
    }

    public void setNptype(Integer nptype) {
        this.nptype = nptype;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}