package com.example.bootproject.config;

import com.example.bootproject.system.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //아틀라스 패턴(ant-style patterns)이라고 불리는 문자열 패턴을 사용
        //?: 한 문자와 일치
        //*: 모든 문자열과 일치
        //**: 모든 하위 경로를 포함하여 일치
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/*/create*/**", "*/update*/**", "*/delete*/**");

    }
    //스프링 부트의 기본 리소스 핸들러를 유지 - properties와 동시에 사용 가능
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
//    }

}