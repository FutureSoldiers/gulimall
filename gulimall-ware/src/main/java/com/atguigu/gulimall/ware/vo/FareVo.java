package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author FutureSoldiers
 * @create 2020-12-17 10:00
 */
@Data
public class FareVo {
    private MemberAddressVo address;
    private BigDecimal fare;
}
