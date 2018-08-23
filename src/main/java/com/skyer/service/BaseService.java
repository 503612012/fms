package com.skyer.service;

import com.skyer.constant.Constant;
import com.skyer.util.IPUtils;
import com.skyer.vo.Log;
import com.skyer.vo.User;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 基类Service
 *
 * @author skyer
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
    protected void addLog(String content, String ip, String title, int userId, String nickName) {
        Log log = new Log();
        log.setContent(content);
        log.setCreateTimeTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
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
    protected User getCurrentUser() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (User) req.getSession().getAttribute(Constant.CURRENT_USER);
    }

    /**
     * 获取当天登录用户的IP地址
     */
    protected String getCurrentUserIp() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return IPUtils.getClientIPAddr(req);
    }

}
