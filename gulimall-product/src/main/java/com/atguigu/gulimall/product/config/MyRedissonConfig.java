package com.atguigu.gulimall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FutureSoldiers
 * @create 2020-11-09 15:23
 */
@Configuration
public class MyRedissonConfig {

    /**
     * 所有对Redisson的使用都是通过RedissonClient对象
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        //1、创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.119.148:6379");

        //2、根据Config创建出RedissonClient示例
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;


    }
}
