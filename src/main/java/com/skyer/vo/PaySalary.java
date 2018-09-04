package com.skyer.vo;

import lombok.Data;

/**
 * 工资发放记录实体类
 *
 * @author skyer
 */
@Data
public class PaySalary {

    private Integer id; // 工资发放记录
    private Integer eid; // 员工ID(谁)
    private String workDate; // 工时月(哪个月)
    private Double shouldPaySalary; // 应发薪资(应该发多少钱)
    private Double actualPaySalary; // 实发工资(实际发了多少钱)
    private String remark; // 备注
    private String payDate; // 发薪日期(哪一天发的)
    private Integer payId; // 发薪人ID(谁发的)

}
