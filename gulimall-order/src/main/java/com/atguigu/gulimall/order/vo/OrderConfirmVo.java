package com.atguigu.gulimall.order.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author FutureSoldiers
 * @create 2020-12-07 15:06
 */
public class OrderConfirmVo {

    //收货地址
    @Setter @Getter
    List<MemberAddressVo> address;

    //所有选中的购物项
    @Setter @Getter
    List<OrderItemVo> items;

    //发票记录


    //优惠卷信息
    @Setter @Getter
    private Integer integration;

    @Setter @Getter
    Map<Long,Boolean> stocks;

    //防重令牌
    @Setter @Getter
    String orderToken;

    public Integer getCount(){
        Integer i = 0;
        if (items!=null){
            for (OrderItemVo item : items) {
                i+=item.getCount();
            }
        }
        return i;
    }

    private BigDecimal total; //订单价格

    public BigDecimal getTotal(){
        BigDecimal sum = new BigDecimal("0");
        if (items!=null){
            for (OrderItemVo item : items) {
                BigDecimal multiply = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
               sum =  sum.add(multiply);
            }
        }
          return sum;
    }

 //   BigDecimal payPrice; //应付价格

    public BigDecimal getPayPrice(){
        return getTotal();
    }



}
