package com.skyer.controller.base;

import com.skyer.constant.Constant;
import com.skyer.enumerate.ResultEnum;
import com.skyer.util.ResultInfo;
import com.skyer.vo.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 基类Controller
 *
 * @author skyer
 */
public abstract class BaseController {

    /**
     * 请求成功
     *
     * @param data 请求成功返回的内容
     */
    protected Object success(Object data) {
        ResultInfo<Object> resultInfo = new ResultInfo<>();
        resultInfo.setCode(ResultEnum.SUCCESS.getCode());
        resultInfo.setData(data);
        return resultInfo;
    }

    /**
     * 请求失败
     *
     * @param msg 失败信息
     */
    protected Object fail(Integer code, String msg) {
        ResultInfo<Object> resultInfo = new ResultInfo<>();
        resultInfo.setCode(code);
        resultInfo.setData(msg);
        return resultInfo;
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

}
