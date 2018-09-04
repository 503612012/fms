package com.skyer.vo;

import lombok.Data;

/**
 * 员工实体类
 *
 * @author skyer
 */
@Data
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

}
