package com.atguigu.gulimall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FutureSoldiers
 * @create 2020-11-02 16:01
 * 1、导入依赖
 * 2、 编写配置 给容器中注入一个RestHighLevelClient
 */
@Configuration
public class GulimallElasticSearchConfig {

    public static final RequestOptions
            COMMON_OPTIONS;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();

        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient esRestClient() {

        RestClientBuilder builder = null;
        builder= RestClient.builder(new HttpHost("192.168.119.148", 9200, "http"));
        RestHighLevelClient client = new RestHighLevelClient(builder);

        //官网 文档写法
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("192.168.119.148",9200,"http")));

        return client;
    }


}
