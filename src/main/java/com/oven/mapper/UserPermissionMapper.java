package com.oven.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.oven.vo.Menu;
import com.oven.vo.UserPermission;

/**
 * UserPermissionMapper接口
 *
 * @author Oven
 */
public interface UserPermissionMapper {

    /**
     * 获取一个用户的所有权限
     */
    Set<String> findPermissionByUserName(Integer userId);

    /**
     * 根据用户ID获取一个目录的所有子目录
     *
     * @param pid    父ID
     * @param userId 用户ID
     */
    List<Menu> getChildrenByPid(@Param("pid") Integer pid, @Param("userId") Integer userId);

    /**
     * 删除该用户的所有权限
     *
     * @param userId 用户ID
     */
    void deleteUserPermission(Integer userId);

    /**
     * 判断一个用户有没有某个权限
     *
     * @param userId 用户ID
     * @param menuId 目录ID
     */
    long hasPermission(@Param("userId") Integer userId, @Param("menuId") Integer menuId);

    /**
     * 添加
     */
    void insert(UserPermission entity);

}
