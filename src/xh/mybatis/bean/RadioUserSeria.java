package xh.mybatis.bean;

import java.io.Serializable;
import java.util.Date;

public class RadioUserSeria implements Serializable {
    private Integer id;

    private String name;

    private Integer prioritylevel;

    private Integer bictype;

    private Integer boctype;

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

    public Integer getPrioritylevel() {
        return prioritylevel;
    }

    public void setPrioritylevel(Integer prioritylevel) {
        this.prioritylevel = prioritylevel;
    }

    public Integer getBictype() {
        return bictype;
    }

    public void setBictype(Integer bictype) {
        this.bictype = bictype;
    }

    public Integer getBoctype() {
        return boctype;
    }

    public void setBoctype(Integer boctype) {
        this.boctype = boctype;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}