package com.atguigu.gulimall.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2020-12-07 15:11
 */
@Data
public class OrderItemVo {
    private Long skuId;
    private String title;
    private String image;
    private List<String> skuAttr;
    private BigDecimal price;
    private Integer count;
    private BigDecimal totalPrice;
  //  private boolean hasStock;  //查询库存状态
    private BigDecimal weight; //商品重量
}
