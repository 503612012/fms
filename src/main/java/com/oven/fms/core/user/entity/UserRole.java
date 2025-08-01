package com.oven.fms.core.user.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户-角色关系实体类
 *
 * @author Oven
 */
@Data
@Table(name = "fms_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dbid")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId; // 用户id

    @Column(name = "role_id")
    private Integer roleId; // 角色id

}
