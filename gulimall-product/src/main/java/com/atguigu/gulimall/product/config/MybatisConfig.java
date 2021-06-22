package com.atguigu.gulimall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.sun.org.apache.regexp.internal.RE;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author FutureSoldiers
 * @create 2020-10-25 16:54
 */
@Configuration
@EnableTransactionManagement //开启事务
@MapperScan("com.atguigu.gulimall.product.dao")
public class MybatisConfig {

    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置请求的页面大于最大页后操作,true调回到首页  false继续 请求 默认false
        paginationInterceptor.setOverflow(true);
        //设置最大单页数量
        paginationInterceptor.setLimit(1000);
        return paginationInterceptor;
    }

 }
