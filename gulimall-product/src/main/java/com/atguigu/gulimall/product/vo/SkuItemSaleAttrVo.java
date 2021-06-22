package com.atguigu.gulimall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2020-11-18 18:48
 */
@Data
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}
