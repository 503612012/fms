package com.skyer.vo;

import lombok.Data;

/**
 * 工地实体类
 *
 * @author skyer
 */
@Data
public class Worksite {

    private Integer id; // 工地
    private String name; // 名称
    private String desc; // 描述
    private Integer createId; // 创建人ID
    private String createNickName; // 创建人昵称
    private String createDate; // 创建时间
    private String lastModifyDate; // 最后修改时间
    private Integer lastModifyId; // 最后修改人ID

}
