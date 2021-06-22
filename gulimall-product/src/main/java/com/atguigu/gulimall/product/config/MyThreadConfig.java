package com.atguigu.gulimall.product.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author FutureSoldiers
 * @create 2020-11-19 16:13
 */
//@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
@Configuration
public class MyThreadConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties pool){
       //线程池 7大参数
        return new ThreadPoolExecutor(
                pool.getCoreSize()  //核心线程数
                ,pool.getMaxSize() //最大连接数
                ,pool.getKeepAliveTime()
                , TimeUnit.SECONDS
                , new LinkedBlockingDeque<>(100000)
                , Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
