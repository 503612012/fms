package com.oven.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 权限工具类（判断一个用户有没有某个权限）
 *
 * @author Oven
 */
public class AuthUtils {

    /**
     * 判断一个用户有没有某个权限
     *
     * @param permissionCode 权限代码
     * @return 有true，没有false
     */
    public static boolean hasPermission(String permissionCode) {
        Subject subject = SecurityUtils.getSubject();
        return subject.isPermitted(permissionCode);
    }

}
