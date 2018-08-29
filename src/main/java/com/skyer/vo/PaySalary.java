package com.skyer.vo;

/**
 * 工资发放记录实体类
 *
 * @author skyer
 */
public class PaySalary {

    private Integer id; // 工资发放记录
    private Integer eid; // 员工ID(谁)
    private String workDate; // 工时月(哪个月)
    private Double shouldPaySalary; // 应发薪资(应该发多少钱)
    private Double actualPaySalary; // 实发工资(实际发了多少钱)
    private String remark; // 备注
    private String payDate; // 发薪日期(哪一天发的)
    private Integer payId; // 发薪人ID(谁发的)

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

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public Double getShouldPaySalary() {
        return shouldPaySalary;
    }

    public void setShouldPaySalary(Double shouldPaySalary) {
        this.shouldPaySalary = shouldPaySalary;
    }

    public Double getActualPaySalary() {
        return actualPaySalary;
    }

    public void setActualPaySalary(Double actualPaySalary) {
        this.actualPaySalary = actualPaySalary;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    @Override
    public String toString() {
        return "PaySalary{" +
                "id=" + id +
                ", eid=" + eid +
                ", workDate='" + workDate + '\'' +
                ", shouldPaySalary=" + shouldPaySalary +
                ", actualPaySalary=" + actualPaySalary +
                ", remark='" + remark + '\'' +
                ", payDate='" + payDate + '\'' +
                ", payId=" + payId +
                '}';
    }

}
