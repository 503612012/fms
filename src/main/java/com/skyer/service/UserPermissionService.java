package com.skyer.service;

import com.skyer.mapper.UserPermissionMapper;
import com.skyer.vo.UserPermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 用户-权限服务层
 *
 * @author skyer
 */
@Service
public class UserPermissionService extends BaseService {

    @Resource
    private UserPermissionMapper userPermissionMapper;

    /**
     * 获取一个用户的所有权限
     */
    public Set<String> findPermissionByUserName(Integer userId) {
        return userPermissionMapper.findPermissionByUserName(userId);
    }

    /**
     * 删除该用户的所有权限
     *
     * @param userId 用户ID
     */
    public void deleteUserPermission(Integer userId) {
        userPermissionMapper.deleteUserPermission(userId);
    }

    /**
     * 判断一个用户有没有某个权限
     *
     * @param userId 用户ID
     * @param menuId 目录ID
     */
    public long hasPermission(Integer userId, Integer menuId) {
        return userPermissionMapper.hasPermission(userId, menuId);
    }

    /**
     * 添加
     */
    public void insert(UserPermission entity) {
        userPermissionMapper.insert(entity);
    }

}
