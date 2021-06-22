package com.atguigu.gulimall.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author FutureSoldiers
 * @create 2020-11-19 16:17
 */
@Component
@Data
@ConfigurationProperties(prefix = "gulimall.thread")
public class ThreadPoolConfigProperties {
    private Integer coreSize; //核心线程数
    private Integer maxSize;  //最大连接数
    private Integer keepAliveTime; //存活时间
}
