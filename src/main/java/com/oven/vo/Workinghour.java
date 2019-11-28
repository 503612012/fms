package com.oven.vo;

import lombok.Data;

/**
 * 工时实体类
 *
 * @author Oven
 */
@Data
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

}
