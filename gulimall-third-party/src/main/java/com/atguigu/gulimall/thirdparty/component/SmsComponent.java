package com.atguigu.gulimall.thirdparty.component;

import com.atguigu.gulimall.thirdparty.util.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author FutureSoldiers
 * @create 2020-11-20 10:52
 */
@ConfigurationProperties(prefix = "alibaba.cloud.sms")
@Data
@Component
public class SmsComponent {

    private String host;
    private String path;
    private String tpl_id;
    private String appcode;


    public void sendSmsCode(String phone,String code){
//        String host = "http://dingxin.market.alicloudapi.com";
//        String path = "/dx/sendSms";
        String method = "POST";
      //  String appcode = "534f1f97159444bea31dabdce8659898";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        //querys.put("mobile", "159xxxx9999");
        querys.put("mobile", phone);
       // querys.put("param", "code:1234");
        querys.put("param", code);
        querys.put("tpl_id", "TP1711063");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
