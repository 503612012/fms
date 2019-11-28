package com.oven.vo;

import lombok.Data;

/**
 * 用户实体类
 *
 * @author Oven
 */
@Data
public class User {

    private Integer id; // 主键
    private String userName; // 用户名
    private String password; // 密码
    private String nickName; // 昵称
    private Integer status; // 状态(1正常,2禁用)
    private String contact; // 联系方式
    private String createTime; // 创建时间
    private String modifyTime; // 最后修改时间

}
