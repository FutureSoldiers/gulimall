package com.atguigu.gulimall.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author FutureSoldiers
 * @create 2020-12-07 16:22
 */
@Configuration
public class GuliFeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                //1、RquestContextHolder拿到刚进来的这个请求
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
               if (attributes!=null){
                HttpServletRequest request = attributes.getRequest(); //老请求
                if (request!=null) {
                    //同步请求数据,Cookie
                    String cookie = request.getHeader("Cookie");
                    //给新请求同步了老请求的cookie
                    requestTemplate.header("Cookie", cookie);
                }
               }
            }
        };
    }
}
