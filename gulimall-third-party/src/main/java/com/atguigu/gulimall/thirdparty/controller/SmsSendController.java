package com.atguigu.gulimall.thirdparty.controller;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FutureSoldiers
 * @create 2020-11-20 14:46
 */
@RestController
@RequestMapping("/sms")
public class SmsSendController {


    @Autowired
    private SmsComponent smsComponent;


    /**
     * 第三方短信接口 提供给别的服务进行调用
     * @param phone
     * @param code
     * @return
     */
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone,@RequestParam("code") String code){
        smsComponent.sendSmsCode(phone, code);
        return R.ok();
    }
}
