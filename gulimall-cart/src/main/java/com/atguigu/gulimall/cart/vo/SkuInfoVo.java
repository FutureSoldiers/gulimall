package com.atguigu.gulimall.cart.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author FutureSoldiers
 * @create 2020-11-29 15:58
 */
@Data
public class SkuInfoVo {

    /**
     * skuId
     */
    @TableId
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
