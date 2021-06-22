package com.atguigu.gulimall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author FutureSoldiers
 * @create 2021-04-02 16:17
 */
@Data
public class SeckillInfoVo {
    private Long promotionId;
    /**
     * ?????id
     */
    private Long promotionSessionId;
    /**
     * ??Ʒid
     */
    private Long skuId;

    /**
     * 商品秒杀随机码
     */
    private String randomCode;
    /**
     * ??ɱ?۸
     */
    private BigDecimal seckillPrice;
    /**
     * ??ɱ????
     */
    private Integer seckillCount;
    /**
     * ÿ???޹?????
     */
    private Integer seckillLimit;
    /**
     * ???
     */
    private Integer seckillSort;

    //当前商品秒杀的开始时间
    private Long startTime;

    //当前商品秒杀的结束时间
    private Long endTime;

}
