package com.skyer.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.skyer.constant.Constant;
import com.skyer.enumerate.ResultEnum;
import com.skyer.service.UserService;
import com.skyer.vo.User;

/**
 * 自定义realm
 *
 * @author skyer
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 为当限前登录的用户授予角色和权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(null);
        info.setStringPermissions(userService.findPermission(user.getId()));
        return info;
    }

    /**
     * 验证当前登录的用户
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        // 查询用户信息
        User user = userService.findByUserName(userName);

        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException(ResultEnum.NO_THIS_USER.getValue());
        }

        Md5Hash md5 = new Md5Hash(password, Constant.MD5_SALT);
        // 密码错误
        if (!md5.toString().equals(user.getPassword())) {
            throw new IncorrectCredentialsException(ResultEnum.PASSWORD_WRONG.getValue());
        }

        // 账号锁定
        if (user.getStatus().equals(2)) {
            throw new LockedAccountException(ResultEnum.USER_DISABLE.getValue());
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }

}
