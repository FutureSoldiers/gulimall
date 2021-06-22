package com.atguigu.gulimall.auth.feign;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.auth.vo.SocialUserVo;
import com.atguigu.gulimall.auth.vo.UserLoginVo;
import com.atguigu.gulimall.auth.vo.UserRegistVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author FutureSoldiers
 * @create 2020-11-21 11:11
 */
@FeignClient("gulimall-member")
public interface MemberFeignService {


    @PostMapping("/member/member/regist")
    R regist(@Valid UserRegistVo vo);


    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo vo);



    @PostMapping("/member/member/oauth2/login")
    R oauthLogin(@RequestBody SocialUserVo vo) throws Exception ;


}
