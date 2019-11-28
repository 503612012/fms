package com.oven.vo;

import lombok.Data;

/**
 * 日志实体类
 *
 * @author Oven
 */
@Data
public class Log {

    private Integer id; // 日志表
    private String title; // 操作标题
    private String content; // 操作内容
    private Integer userId; // 用户ID
    private String nickName; // 用户昵称
    private String createTime; // 操作时间
    private String ip; // 操作IP

}
