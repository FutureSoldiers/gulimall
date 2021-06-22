package com.atguigu.gulimall.ware.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author FutureSoldiers
 * @create 2020-10-29 15:20
 */
@EnableTransactionManagement   //开启事务
@MapperScan("com.atguigu.gulimall.ware.dao")
@Configuration
public class WareMybatisConfig {


    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置请求的页面大于最大页后操作,true调回到首页  false继续 请求 默认false
//        paginationInterceptor.setOverflow(true);
//        //设置最大单页数量
//        paginationInterceptor.setLimit(1000);
        return paginationInterceptor;
    }

}
