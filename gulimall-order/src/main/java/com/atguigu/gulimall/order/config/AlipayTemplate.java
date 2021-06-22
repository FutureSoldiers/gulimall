package com.atguigu.gulimall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.gulimall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "2021000117629611";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCb3y5Igdqd9Yig3WNxWjr9JAflUgRGz4xxKEEA17U92MnJQUBq+62RXb5DyxOgzHKNY8COvQSljujFPrgi7rWmchLNn+HRogbjc8VKsnz7hcGYv4WGViSZv99n2Hm6AXAhzaDProAp1xDIEJbL0hXa41U9Z4FSHyqGyb0MbfrdBshcvhyBK5+5IMqg2lLriGHKU+X97+yrGboRNSDt4EtdgvTuwsLAxLlNXa2IXRzw6gRKyVlI66cDGvZu/NNHQpMTbXz5sCpu05bUX5zi8BizOc5F/9eHuAQ8LBNBwKAIIz0eF7Tb/HeM/EwZ5K8unD8y524hQaEdnkh/lMxkokLTAgMBAAECggEBAI2QP9fIX8XwVptLFmK/gS2hCK7EM3AgVbv7xDXAszGtb26iHaBMXdHVYtTtXjE/HZa+DwdUMiEuzsc9dDz9sequVdd8qroavsjav0ddHdfOHgrdfqaIHAurUHpfCAbgAXgh6jhUkr/tPKeHMX2BmynGKAdxtujft17dYB4m0JzleEa9yoLXjCPTCBmpOV2jGWWuWhIEQR9zV2Ia2uF4XtIUOycStCs7PrspRc95+YuVyI8RKgwa8qV+kC2JHsWoybErOfE5DsFB4v19/I/ZET2aILrIpEjbdohBH+ZeSA5ymzPP/Srqaxq3m14Jl4gbJgLfAykFL4ESSfXmPdNDkbECgYEA73OIv+kR+VBFL6ra7yO5bQQGtpuVxmqbrMqGzqnej00au3XoIU0xvlihSQ0wV1UGAAw6CUSfmwZxdqeAp6zM70N1jlMhdwSnyf9wzDZ7y9m0tfrJsd2qcmRarnTpziEobZWsrieMYo6pTIK80Dt20LDrdlSdKVZx7eMnS2PAK6sCgYEApqTs6QPOP82jYO3G6cZIq4rZjb8hydm2sCy2+rVvoNhs0AtqZjWwhQ4e00wtwtP91tSqf6u1lf3IpORlJU7BUrixGlNLV8oDCvH6Ru4xQpSPciwXl66Evpif8cfWF1skhfd7Z40JwbrRIRaT0tka4pZiQRjQ51GyUTDn9phR3XkCgYATd4EWmQwtCKmXRnymFHFuu3UQQ3XZu7EMSyrKgmTloY8azmsgyfXRU8NI/RmBABiMyHGvbzTKlauDyfpbp2DB0uort/ZwhqBde7bf404NNJXMdacQHsVMp3v30ulvKKLwxcpUHkL7gD2jSr2u8CaosLlz1U0xnyWEAYOgTWvCIQKBgGsYdwDRZ01t1N/IMBuxSKamF2aXPRnCBzx1oCrDS7gtHtEdZQXG32jDSGA3Bvm8z9eu2Nwv+AUrEjWWasJtOKT2+SqX+UW0F/yCwnuvPI0zeHTBjW/3wmvTHod+DEL6WiCWigJki66rZxdCsUHCMRsJ1p9LWYYv5M91BL5f6czRAoGBAOeW3D8MYiRxQ5/K/EunW0+ewlEpSR+fYaAtF80BnmcjIWC2l7pFiwnnUNfB7Xcu6/fkiLenPFQQ10ICY7JtzCESQXBhl4ENEgJ/1HlqIzrMEzDcs0WXEQrk2Hx94GZ+Qf+4+UYG1AY1dyO8oYyYnK0DqCDN9i0VlJeww6qFHNLR";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtSh+KreE6v7tXLenTzf8nxYF7ItbNkyLapZdzSHYoRpAINCkfYMFaG6c2iMSKsYhaRL0c1J4X9RZAj34UPCeznsnQVR/qgUyye8LjHAdndxV3eC0iM0mDH18NbbVm+Pmwn2byv1POWRUD95IFOYG9wiV8CRRRJ9Fy+aRk+o/RIEvCKRzq96wDdnSDKwCzORw21cSIfIBHwDevKJYZlLBj9BQxW5gc6c8Pe/XTiI39uYb/bUf1MSA0r1Ck75yXCC1NFtmTwrnTbA+tgltsZI63vnj6zdLRgp3NXHqKUiHhHYCcOGKP6ywfEFzfL6d7QTEK+ItJvTSXp20C7MNt2uv8QIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url = "http://内网穿透/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url = "http://member.gulimall.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    private String timeout = "30m";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+timeout+"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
