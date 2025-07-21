package com.oven.fms.framework.config;

import com.oven.fms.framework.interceptor.SecurityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 拦截器配置
 *
 * @author Oven
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor).excludePathPatterns(
                "/**/*.js",
                "/**/*.css",
                "/**/*.ico",
                "/**/*.woff",
                "/**/*.woff2",
                "/**/*.gif",
                "/**/*.png",
                "/**/*.jpg",
                "/**/*.jpeg",
                "/**/*.properties"
        );
    }

}
