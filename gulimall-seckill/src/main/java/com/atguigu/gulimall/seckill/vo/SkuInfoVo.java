package com.atguigu.gulimall.seckill.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author FutureSoldiers
 * @create 2021-04-02 9:45
 */
@Data
public class SkuInfoVo {
    private Long skuId;
    /**
     * spuId
     */
    private Long spuId;
    /**
     * sku???
     */
    private String skuName;
    /**
     * sku????????
     */
    private String skuDesc;
    /**
     * ????????id
     */
    private Long catalogId;
    /**
     * Ʒ??id
     */
    private Long brandId;
    /**
     * Ĭ??ͼƬ
     */
    private String skuDefaultImg;
    /**
     * ???
     */
    private String skuTitle;
    /**
     * ?????
     */
    private String skuSubtitle;
    /**
     * ?۸
     */
    private BigDecimal price;
    /**
     * ????
     */
    private Long saleCount;
}
