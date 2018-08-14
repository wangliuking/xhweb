package xh.mybatis.bean;

public class SystemChangeSheet {
    private int id;
    private String systemChangeStartTime;
    private String systemChangeExcTime;
    private String systemChangeType;
    private String versionOld;
    private String versionNew;
    private String solutionNewVersion;
    private String processAndResult;
    private String systemChangeNote;
    private String excPerson;
    private String supervisePersion;
    private String serialNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSystemChangeStartTime() {
        return systemChangeStartTime;
    }

    public void setSystemChangeStartTime(String systemChangeStartTime) {
        this.systemChangeStartTime = systemChangeStartTime;
    }

    public String getSystemChangeExcTime() {
        return systemChangeExcTime;
    }

    public void setSystemChangeExcTime(String systemChangeExcTime) {
        this.systemChangeExcTime = systemChangeExcTime;
    }

    public String getSystemChangeType() {
        return systemChangeType;
    }

    public void setSystemChangeType(String systemChangeType) {
        this.systemChangeType = systemChangeType;
    }

    public String getVersionOld() {
        return versionOld;
    }

    public void setVersionOld(String versionOld) {
        this.versionOld = versionOld;
    }

    public String getVersionNew() {
        return versionNew;
    }

    public void setVersionNew(String versionNew) {
        this.versionNew = versionNew;
    }

    public String getSolutionNewVersion() {
        return solutionNewVersion;
    }

    public void setSolutionNewVersion(String solutionNewVersion) {
        this.solutionNewVersion = solutionNewVersion;
    }

    public String getProcessAndResult() {
        return processAndResult;
    }

    public void setProcessAndResult(String processAndResult) {
        this.processAndResult = processAndResult;
    }

    public String getSystemChangeNote() {
        return systemChangeNote;
    }

    public void setSystemChangeNote(String systemChangeNote) {
        this.systemChangeNote = systemChangeNote;
    }

    public String getExcPerson() {
        return excPerson;
    }

    public void setExcPerson(String excPerson) {
        this.excPerson = excPerson;
    }

    public String getSupervisePersion() {
        return supervisePersion;
    }

    public void setSupervisePersion(String supervisePersion) {
        this.supervisePersion = supervisePersion;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "SystemChangeSheet{" +
                "id=" + id +
                ", systemChangeStartTime='" + systemChangeStartTime + '\'' +
                ", systemChangeExcTime='" + systemChangeExcTime + '\'' +
                ", systemChangeType='" + systemChangeType + '\'' +
                ", versionOld='" + versionOld + '\'' +
                ", versionNew='" + versionNew + '\'' +
                ", solutionNewVersion='" + solutionNewVersion + '\'' +
                ", processAndResult='" + processAndResult + '\'' +
                ", systemChangeNote='" + systemChangeNote + '\'' +
                ", excPerson='" + excPerson + '\'' +
                ", supervisePersion='" + supervisePersion + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
