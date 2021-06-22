package com.atguigu.gulimall.order.vo;

import lombok.Data;

import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2021-03-16 15:22
 */
@Data
public class WareSkuLockVo {

    private String orderSn; //订单号

    private List<OrderItemVo> locks;//需要锁住的所有库存信息
}
