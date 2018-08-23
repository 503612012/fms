package com.skyer.vo;

/**
 * 员工实体类
 *
 * @author skyer
 */
public class Employee {

    private Integer id; // 员工表
    private String name; // 姓名
    private Integer age; // 年龄
    private Integer gender; // 0:女,1:男
    private String address; // 住址
    private String contact; // 联系方式
    private Double daySalary; // 日新
    private Double monthSalary; // 月薪
    private String createTime; // 创建时间
    private String modifyTime; // 最后修改时间
    private Integer status; // 状态(1正常,2禁用)

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
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getDaySalary() {
        return daySalary;
    }

    public void setDaySalary(Double daySalary) {
        this.daySalary = daySalary;
    }

    public Double getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(Double monthSalary) {
        this.monthSalary = monthSalary;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", address=" + address + ", contact=" + contact + ", daySalary=" + daySalary + ", monthSalary=" + monthSalary + ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", status=" + status + "]";
    }

}
