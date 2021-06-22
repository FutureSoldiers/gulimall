package com.atguigu.common.to.mq;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author FutureSoldiers
 * @create 2021-04-03 16:01
 */
@Data
public class SeckillOrderTo {

    private String OrderSn; //订单号

    private Long promotionSessionId;  //活动场次id

    private Long skuId; //商品id
    private BigDecimal seckillPrice; //秒杀价格

    private Integer num;  //购买数量
    private Long memberId; //会员id


}
