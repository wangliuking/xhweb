package xh.mybatis.bean;

import java.io.Serializable;
import java.util.Date;

public class TalkGroupIa implements Serializable {
    private Integer id;

    private String name;

    private Integer prioritylevel;

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}