package com.oven.fms.core.role.dao;

import com.oven.basic.base.dao.BaseDao;
import com.oven.basic.base.entity.ConditionAndParams;
import com.oven.fms.core.role.entity.RoleMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色-菜单关系dao层
 *
 * @author Oven
 */
@Repository
public class RoleMenuDao extends BaseDao<RoleMenu> {

    /**
     * 通过角色id查询
     *
     * @param roleId 角色id
     */
    public List<RoleMenu> getByRoleId(Integer roleId) {
        return super.getAll(ConditionAndParams.build(RoleMenu::getRoleId, roleId));
    }

    /**
     * 通过角色id和菜单id查询
     *
     * @param roleId 角色id
     * @param menuId 菜单id
     */
    public RoleMenu getByRoleIdAndMenuId(Integer roleId, Integer menuId) {
        return super.getOne(ConditionAndParams.build(RoleMenu::getRoleId, roleId).andEq(RoleMenu::getMenuId, menuId));
    }

    /**
     * 通过角色id删除
     *
     * @param roleId 角色id
     */
    public void deleteByRoleId(Integer roleId) {
        super.delete(ConditionAndParams.build(RoleMenu::getRoleId, roleId));
    }

}
