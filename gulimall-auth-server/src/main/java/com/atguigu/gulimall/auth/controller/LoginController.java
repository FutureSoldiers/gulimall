package com.atguigu.gulimall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.atguigu.common.constant.AuthServerConstant;
import com.atguigu.common.exception.BizCodeEnume;
import com.atguigu.common.utils.R;
import com.atguigu.common.vo.MemberRespVo;
import com.atguigu.gulimall.auth.feign.MemberFeignService;
import com.atguigu.gulimall.auth.feign.ThirdPartFeignService;
import com.atguigu.gulimall.auth.vo.UserLoginVo;
import com.atguigu.gulimall.auth.vo.UserRegistVo;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author FutureSoldiers
 * @create 2020-11-20 9:52
 */
@Controller
public class LoginController {

    @Autowired
    private ThirdPartFeignService thirdPartFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    private MemberFeignService memberFeignService;

    /**
     * 发送一个请求直接跳转到一个页面.
     * SpringMVC viewController: 将请求和页面映射过来.
     *
     * @return
     */

    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone) {
        //TODO 1、接口防刷.
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(redisCode)) {
            long l = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - l < 60000) {
                //60秒内不能再发
                return R.error(BizCodeEnume.SMS_CODE_EXCEPTION.getCode(), BizCodeEnume.SMS_CODE_EXCEPTION.getMsg());
            }
        }

        //2、验证码的再次校验.  redis 存key-phone value-code sms:code:11554221552 -> 44554

        String code = UUID.randomUUID().toString().substring(0, 5);
        String subString = code+"_" + System.currentTimeMillis();
        //redis缓存验证码 防止同一个手机号 60秒内再次发送验证码，
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, subString, 10, TimeUnit.MINUTES);

        thirdPartFeignService.sendCode(phone, "code:" + code);

        return R.ok();
    }

    /**
     * //TODO 重定向携带数据, 利用session原理. 将数据放在session中,只要跳到下一个页面取出这个数据后,session里面的数据就会删掉
     * RedirectAttributes attributes 模拟重定向  携带数据(模拟Session)
     * <p>
     * //TODO 分布式下的session问题
     *
     * @param vo
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/regist")
    public String regist(@Valid UserRegistVo vo, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
//            Map<String,String> errors = new HashMap<>();
//            result.getFieldErrors().stream().map((fieldError)->{
//                String field = fieldError.getField();
//                String defaultMessage = fieldError.getDefaultMessage();
//                errors.put(field,defaultMessage);
//                return errors;
//            });
            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            attributes.addFlashAttribute("errors", errors);
            //校验出错,转发到注册页
            return "redirect:http://auth.gulimall.com/reg.html";
        }
        //1、校验验证码
        @NotEmpty(message = "验证码必须填写") String code = vo.getCode();
        String s = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if (!StringUtils.isEmpty(s)) {
            if (code.equals(s.split("_")[0])) {
                //删除验证码 令牌机制
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                //验证码通过. //真正注册
                R r = memberFeignService.regist(vo);
                if (r.getCode() == 0) {

                    return "redirect:http://auth.gulimall.com/login.html";

                } else {

                    //失败
                    //成功
                    Map<String, String> errors = new HashMap<>();
                    errors.put("msg", r.getData("msg",new TypeReference<String>() {
                    }));
                    attributes.addFlashAttribute("errors", errors);
                    return "redirect:http://auth.gulimall.com/reg.html";
                }
            } else {
                Map<String, String> errors = new HashMap<>();
                errors.put("code", "验证码错误");
                attributes.addFlashAttribute("errors", errors);
                //验证码错误,转发到注册页
                return "redirect:http://auth.gulimall.com/reg.html";
            }
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("code", "验证码错误");
            attributes.addFlashAttribute("errors", errors);
            //校验出错,转发到注册页
            return "redirect:http://auth.gulimall.com/reg.html";
        }

    }

    @GetMapping("/login.html")
    public String loginPage(HttpSession session){
        Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
        if (attribute==null){
          //没登录
            return "login";
        }else {
            return "redirect:http://gulimall.com";
        }
    }

    @PostMapping("/login")
    public String login(UserLoginVo vo, RedirectAttributes redirectAttributes, HttpSession session){

        //远程登录
        R login = memberFeignService.login(vo);
        if (login.getCode()==0){

            MemberRespVo data = login.getData("data", new TypeReference<MemberRespVo>() {
            });
            //成功放到session中
            session.setAttribute(AuthServerConstant.LOGIN_USER,data);
            return "redirect:http://gulimall.com";

        }else {
            Map<String,String> errors = new HashMap<>();
            errors.put("msg",login.getData("msg",new TypeReference<String>(){}));
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }

}
