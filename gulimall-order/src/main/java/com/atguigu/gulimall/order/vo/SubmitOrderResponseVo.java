package com.atguigu.gulimall.order.vo;

import com.atguigu.gulimall.order.entity.OrderEntity;
import lombok.Data;

/**
 * @author FutureSoldiers
 * @create 2020-12-22 14:43
 */
@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;

    private Integer code; //错误状态码
}
