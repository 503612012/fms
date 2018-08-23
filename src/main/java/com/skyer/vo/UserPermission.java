package com.skyer.vo;

/**
 * 用户-权限实体类
 *
 * @author skyer
 */
public class UserPermission {

    private Integer id; // 用户权限表
    private Integer userId; // 用户ID
    private Integer menuId; // 目录ID

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "UserPermission [id=" + id + ", userId=" + userId + ", menuId=" + menuId + "]";
    }

}
