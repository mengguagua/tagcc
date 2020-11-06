package com.gcc.tagcc.webConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    /**
     * 添加拦截器以及拦截url
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 所有请求都拦截，但jwt逻辑实际只鉴权有 @UserLoginToken 注解的方法
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
}
