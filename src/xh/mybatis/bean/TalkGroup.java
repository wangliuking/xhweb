package xh.mybatis.bean;

import java.io.Serializable;
import java.util.Date;

public class TalkGroup implements Serializable {
	private Integer id;

    private Integer talkgroupid;
    
    private String mTalkgroupalias;

    private String mSecuritygroup;

    private String mConsoletgmgcapabilityprofilealias;

    private String mTgmgcapabilityprofilealias;

    private String mTgmgvalidsitesprofilealias;

    private Boolean mPreemptcapable;

    private Boolean mExtendedbandchannelscanbeassigned;

    private Boolean mTalkgroupregroupable;

    private Boolean mGroupenable;

    private String eName;

    private String eAlias;

    private Integer eMscid;

    private Long eVpnid;

    private Integer eSaid;

    private Integer eIaid;

    private Integer eVaid;

    private Integer ePreempt;

    private Integer eRadiotype;

    private Integer eRegroupable;

    private Integer eEnabled;

    private String eDirectdial;

    private String saname;

    private String ianame;

    private Date time;
    

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTalkgroupid() {
		return talkgroupid;
	}

	public void setTalkgroupid(Integer talkgroupid) {
		this.talkgroupid = talkgroupid;
	}

	public String getmTalkgroupalias() {
        return mTalkgroupalias;
    }

    public void setmTalkgroupalias(String mTalkgroupalias) {
        this.mTalkgroupalias = mTalkgroupalias == null ? null : mTalkgroupalias.trim();
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

    public Boolean getmExtendedbandchannelscanbeassigned() {
        return mExtendedbandchannelscanbeassigned;
    }

    public void setmExtendedbandchannelscanbeassigned(Boolean mExtendedbandchannelscanbeassigned) {
        this.mExtendedbandchannelscanbeassigned = mExtendedbandchannelscanbeassigned;
    }

    public Boolean getmTalkgroupregroupable() {
        return mTalkgroupregroupable;
    }

    public void setmTalkgroupregroupable(Boolean mTalkgroupregroupable) {
        this.mTalkgroupregroupable = mTalkgroupregroupable;
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

    public Integer getePreempt() {
        return ePreempt;
    }

    public void setePreempt(Integer ePreempt) {
        this.ePreempt = ePreempt;
    }

    public Integer geteRadiotype() {
        return eRadiotype;
    }

    public void seteRadiotype(Integer eRadiotype) {
        this.eRadiotype = eRadiotype;
    }

    public Integer geteRegroupable() {
        return eRegroupable;
    }

    public void seteRegroupable(Integer eRegroupable) {
        this.eRegroupable = eRegroupable;
    }

    public Integer geteEnabled() {
        return eEnabled;
    }

    public void seteEnabled(Integer eEnabled) {
        this.eEnabled = eEnabled;
    }

    public String geteDirectdial() {
        return eDirectdial;
    }

    public void seteDirectdial(String eDirectdial) {
        this.eDirectdial = eDirectdial == null ? null : eDirectdial.trim();
    }

    public String getSaname() {
        return saname;
    }

    public void setSaname(String saname) {
        this.saname = saname == null ? null : saname.trim();
    }

    public String getIaname() {
        return ianame;
    }

    public void setIaname(String ianame) {
        this.ianame = ianame == null ? null : ianame.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}