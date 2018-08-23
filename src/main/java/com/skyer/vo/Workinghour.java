package com.skyer.vo;

/**
 * 工时实体类
 *
 * @author skyer
 */
public class Workinghour {

    private Integer id;
    private Integer eid;
    private String ename;
    private String date;
    private double score;
    private double daySalary;
    private double extraSalary;
    private Integer worksiteId;
    private String extraDesc;
    private String remark;
    private Integer inputId;
    private String inputNickName;
    private String inputDate;
    private String lastModifyDate;
    private Integer lastModifyOperatorId;

    // 非数据库属性
    private String worksiteName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getDaySalary() {
        return daySalary;
    }

    public void setDaySalary(double daySalary) {
        this.daySalary = daySalary;
    }

    public double getExtraSalary() {
        return extraSalary;
    }

    public void setExtraSalary(double extraSalary) {
        this.extraSalary = extraSalary;
    }

    public Integer getWorksiteId() {
        return worksiteId;
    }

    public void setWorksiteId(Integer worksiteId) {
        this.worksiteId = worksiteId;
    }

    public String getExtraDesc() {
        return extraDesc;
    }

    public void setExtraDesc(String extraDesc) {
        this.extraDesc = extraDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getInputId() {
        return inputId;
    }

    public void setInputId(Integer inputId) {
        this.inputId = inputId;
    }

    public String getInputNickName() {
        return inputNickName;
    }

    public void setInputNickName(String inputNickName) {
        this.inputNickName = inputNickName;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getLastModifyOperatorId() {
        return lastModifyOperatorId;
    }

    public void setLastModifyOperatorId(Integer lastModifyOperatorId) {
        this.lastModifyOperatorId = lastModifyOperatorId;
    }

    public String getWorksiteName() {
        return worksiteName;
    }

    public void setWorksiteName(String worksiteName) {
        this.worksiteName = worksiteName;
    }

    @Override
    public String toString() {
        return "Workinghour{" +
                "id=" + id +
                ", eid=" + eid +
                ", ename='" + ename + '\'' +
                ", date='" + date + '\'' +
                ", score=" + score +
                ", daySalary=" + daySalary +
                ", extraSalary=" + extraSalary +
                ", worksiteId=" + worksiteId +
                ", extraDesc='" + extraDesc + '\'' +
                ", remark='" + remark + '\'' +
                ", inputId=" + inputId +
                ", inputNickName='" + inputNickName + '\'' +
                ", inputDate='" + inputDate + '\'' +
                ", lastModifyDate='" + lastModifyDate + '\'' +
                ", lastModifyOperatorId=" + lastModifyOperatorId +
                '}';
    }

}
