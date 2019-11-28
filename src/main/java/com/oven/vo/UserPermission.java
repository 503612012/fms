package com.oven.vo;

import lombok.Data;

/**
 * 用户-权限实体类
 *
 * @author Oven
 */
@Data
public class UserPermission {

    private Integer id; // 用户权限表
    private Integer userId; // 用户ID
    private Integer menuId; // 目录ID

}