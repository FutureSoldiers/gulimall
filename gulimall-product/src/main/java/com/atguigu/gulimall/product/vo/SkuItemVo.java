package com.atguigu.gulimall.product.vo;

import com.atguigu.gulimall.product.entity.SkuImagesEntity;
import com.atguigu.gulimall.product.entity.SkuInfoEntity;
import com.atguigu.gulimall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2020-11-18 16:33
 */
@Data
public class SkuItemVo {

    //1、sku基本信息获取
    SkuInfoEntity info;

    boolean hasStock;

    //2、sku的图片信息
    List<SkuImagesEntity> images;

    //3、获取spu的销售属性组合.
    List<SkuItemSaleAttrVo> saleAttr;

    //4、 获取spu的介绍
    SpuInfoDescEntity desp;

    //5、获取spu的规格参数信息
    List<SpuItemAttrGroupVo> groupAttrs;

    SeckillInfoVo seckillInfoVo; //当前商品的秒杀优惠信息



}
