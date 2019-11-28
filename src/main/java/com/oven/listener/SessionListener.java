package com.oven.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.oven.constant.Constant;

/**
 * session监听器
 *
 * @author Oven
 */
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // session创建
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent session) {
        // session销毁
        session.getSession().getServletContext().removeAttribute(Constant.CURRENT_USER);
        session.getSession().removeAttribute(Constant.CURRENT_USER);
    }

}
