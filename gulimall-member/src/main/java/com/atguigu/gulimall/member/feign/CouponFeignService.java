package com.atguigu.gulimall.member.feign;

        import com.atguigu.common.utils.R;
        import org.springframework.cloud.openfeign.FeignClient;
        import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author FutureSoldiers
 * @create 2020-10-16 14:52
 *  这是一个声明式的远程调用
 */
@FeignClient("gulimall-coupon")   //@FeignClient声明这是一个远程服务    ("gulimall-coupon")需要调用的服务名
public interface CouponFeignService {

    //直接把调用的方法名 和映射路径完整的写过来
    @RequestMapping("/coupon/coupon/member/list")
    public R membercoupons();
}
