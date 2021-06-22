package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 1、整合mybatis-plus
 *   1、导入依赖
 *   2、配置
 *     1、配置数据源
 *     2、配置mybatis-plus
 *         .1使用@MapperScan 包扫描
 *         .2 告诉sql映射文件地址
 *
 *  使用逻辑删除
 *    1、配置  yml
 *       logic-delete-value: 1   删除
 *       logic-not-delete-value: 0   不删除
 *    2、配置bean(版本高可以省略)
 *    3、加上逻辑删除注解 在实体类属性上加上 	@TableLogic
 *
 *3、使用JSR303
 *    1)校验注解: 给Bean添加校验注解: javax.validation.constraints,可以定义自己的message提示
 *    2)开启校验功能 在请求参数前面加入 @Valid
 *      效果:校验错误后悔提示响应
 *    3) 给校验的bean后紧跟一个BindingResult, 就可以获取到校验的结果
 *    4)、 分组校验(多场景的复杂校验)
         1)、@NotBlank(message = "品牌名必须提交",groups = {AddGroup.class,UpdateGroup.class})
 *        给 校验注解标注什么情况需要进行校验
 *       2) @Validated({AddGroup.class})
 *       3) 默认没有指定分组的校验注解@NotBlank,在分组情况下不生效,只会在@Validated生效
 *    5)、自定义校验
 *      1)编写自定义的校验注解
 *      2)编写一个自定义的校验器  ConstraintValidator
 *      3)关联自定义的校验器和校验注解
 *      @Documented
 *      @Constraint(validatedBy = {ListValueConstraintValidator.class【可以指定不同的校验器适,配多种情况】})
 *      @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
 *      @Retention(RetentionPolicy.RUNTIME)
 *
 *
 * 5、模版引擎
 *  1)、thymeleaf-starter: 关闭缓存
 *  2)、静态资源都放在static文件夹下就可以按照路径直接访问
 *  3)、页面放在templates下,直接访问
 *  4)、前端页面修改不重启服务器实时更新
 *    1、引入dev-tools  并且关闭 thymeleaf缓存    thymeleaf:  cache: false
 *
 *    2、修改完页面 使用build下面的  Ctrl+F9 使全部更新   或者 Ctrl+shift+F9 使当前页面更新  代码配置 推荐重启服务器
 *
 * 6、整合rdis
 *  1)、引入data-redis-starter
 *  2)、简单配置redis的host等信息
 *  3)、使用springBoot自动配置好的StringRedisTemplate来操作redis
 *      redis->> Map
 *
 * 7、整合redisson作为分布式锁等功能框架
 *   1)、引入依赖
 *   <dependency>
 *             <groupId>org.redisson</groupId>
 *             <artifactId>redisson</artifactId>
 *             <version>3.12.0</version>
 *         </dependency>
 *   2)、配置redission
 *        MyRedissonConfig给容器中配置一个RedissonClient实例即可
 *   3)、使用
 *       参照文档做.
 *
 *8、整合SpringCache简化缓存开发
 *    1)、引入依赖
 *       spring-boot-starter-cache、spring-boot-starter-data-redis
 *    2)、写配置
 *          (1)、自动配置了哪些
 *              CacheAutoConfiguration会导入 RedisCacheConfiguration
 *              自动配好了缓存管理器RedisCacheManager
 *          (2)、配置使用redis作为缓存
 *             spring.cache.type=redis
 *    3)、测试使用缓存
 *        @Cacheable:   触发将数据保存到缓存的操作
 *        @CacheEvicf:  触发将数据从缓存删除的操作
 *        @CachePut:    不影响方法执行更新缓存
 *        @Caching:     组合以上多个操作
 *        @CacheConfig: 在类级别共享缓存的相同配置
 *        1)、开启缓存功能
 *        2)、只需要使用注解就能完成缓存操作
 *    4)、原理:
 *    CacheAutoConfiguration -> RedisCacheConfiguration -> 自动配置了RedisCacheManager->
 *    自动配置了RedisCacheManager->初始化所有的缓存->每个缓存决定使用什么配置
 *    ->如果redisCacheConfiguration有就用已有的,没有就用默认配置的
 *    ->想改缓存的配置,只需要给容器中放一个RedisCacheConfiguration即可
 *    ->就会应用到当前RedisCacheManager
 *
 */
@EnableRedisHttpSession //开启redis Session
@EnableCaching   //开启缓存
@EnableFeignClients(basePackages = "com.atguigu.gulimall.product.feign")
@MapperScan("com.atguigu.gulimall.product.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
