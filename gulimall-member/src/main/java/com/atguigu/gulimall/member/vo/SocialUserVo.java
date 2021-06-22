package com.atguigu.gulimall.member.vo;

import lombok.Data;

/**
 * @author FutureSoldiers
 * @create 2020-11-23 12:18
 */
@Data
public class SocialUserVo {
    private String access_token;
    private String remind_in;
    private long expires_in;
    private String uid;
    private String isRealName;
}
