package com.oven.service;

import com.oven.constant.Constant;
import com.oven.util.IPUtils;
import com.oven.vo.Log;
import com.oven.vo.User;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 基类Service
 *
 * @author Oven
 */
@Service
public abstract class BaseService {

    @Resource
    private LogService logService;

    /**
     * 添加日志
     *
     * @param content  日志内容
     * @param ip       操作者IP
     * @param title    日志标题
     * @param userId   操作用户ID
     * @param nickName 操作用户用户名
     */
    void addLog(String content, String ip, String title, int userId, String nickName) {
        Log log = new Log();
        log.setContent(content);
        log.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        log.setIp(ip);
        log.setTitle(title);
        log.setUserId(userId);
        log.setNickName(nickName);
        logService.insert(log);
    }

    /**
     * 获取当天登录用户
     *
     * @return 当前登录的用户
     */
    User getCurrentUser() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (User) req.getSession().getAttribute(Constant.CURRENT_USER);
    }

    /**
     * 获取当天登录用户的IP地址
     */
    String getCurrentUserIp() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return IPUtils.getClientIPAddr(req);
    }

}
