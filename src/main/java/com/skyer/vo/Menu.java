package com.skyer.vo;

import lombok.Data;

/**
 * 目录实体类
 *
 * @author skyer
 */
@Data
public class Menu {

    private Integer id; // 目录表
    private String menuCode; // 目录编码
    private String menuName; // 目录名称
    private Integer pid; // 父Id
    private Integer sort; // 排序值
    private String url; // 目录链接
    private String iconCls; // 图标
    private String type; // 1目录,2按钮
    private String createTime; // 创建时间
    private String modifyTime; // 最后修改时间
    private Integer status; // 状态(1正常,2禁用)

}
