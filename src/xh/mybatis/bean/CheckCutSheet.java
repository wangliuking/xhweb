package xh.mybatis.bean;

public class CheckCutSheet {
    private int id;
    private String bsStationNote;
    private String faultNote;
    private String department;
    private String checkTime;
    private String suggestion;
    private String draftingPerson;
    private String checkPerson;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBsStationNote() {
        return bsStationNote;
    }

    public void setBsStationNote(String bsStationNote) {
        this.bsStationNote = bsStationNote;
    }

    public String getFaultNote() {
        return faultNote;
    }

    public void setFaultNote(String faultNote) {
        this.faultNote = faultNote;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getDraftingPerson() {
        return draftingPerson;
    }

    public void setDraftingPerson(String draftingPerson) {
        this.draftingPerson = draftingPerson;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    @Override
    public String toString() {
        return "CheckCutSheet{" +
                "id=" + id +
                ", bsStationNote='" + bsStationNote + '\'' +
                ", faultNote='" + faultNote + '\'' +
                ", department='" + department + '\'' +
                ", checkTime='" + checkTime + '\'' +
                ", suggestion='" + suggestion + '\'' +
                ", draftingPerson='" + draftingPerson + '\'' +
                ", checkPerson='" + checkPerson + '\'' +
                '}';
    }
}
