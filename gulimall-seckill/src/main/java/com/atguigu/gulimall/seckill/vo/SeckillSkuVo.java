package com.atguigu.gulimall.seckill.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author FutureSoldiers
 * @create 2021-04-01 17:11
 */
@Data
public class SeckillSkuVo {

    private Long id;
    /**
     * ?id
     */
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
     * ??ɱ?۸
     */
    private Integer seckillPrice;
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

}
