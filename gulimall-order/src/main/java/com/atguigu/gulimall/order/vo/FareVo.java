package com.atguigu.gulimall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author FutureSoldiers
 * @create 2020-12-23 10:32
 */
@Data
public class FareVo {
    private MemberAddressVo address;
    private BigDecimal fare;
}
