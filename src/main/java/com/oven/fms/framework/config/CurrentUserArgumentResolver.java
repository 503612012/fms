package com.oven.fms.framework.config;

import com.oven.fms.common.constant.AppConst;
import com.oven.fms.core.user.entity.User;
import com.oven.fms.framework.annotation.CurrentUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义参数解析器
 *
 * @author Oven
 */
@Configuration
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    /**
     * 解析参数中的User对象，然后将当前用户信息放入该对象中
     */
    @Override
    @SuppressWarnings({"NullableProblems", "ConstantConditions", "UnnecessaryLocalVariable"})
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);
        User user = (User) req.getSession().getAttribute(AppConst.CURRENT_USER);
        return user;
    }

}
