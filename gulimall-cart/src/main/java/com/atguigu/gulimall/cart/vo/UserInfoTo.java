package com.atguigu.gulimall.cart.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author FutureSoldiers
 * @create 2020-11-29 10:44
 */
@ToString
@Data
public class UserInfoTo {

    private Long userId;
    private String userKey; //一定封装

    private boolean tempUser = false;
}
