package com.oven.fms.core.role.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色-菜单关系实体类
 *
 * @author Oven
 */
@Data
@Table(name = "fms_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dbid")
    private Integer id;

    @Column(name = "role_id")
    private Integer roleId; // 角色id

    @Column(name = "menu_id")
    private Integer menuId; // 菜单id

}
