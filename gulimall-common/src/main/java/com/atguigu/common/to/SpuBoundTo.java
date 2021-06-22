package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author FutureSoldiers
 * @create 2020-10-28 15:04
 */
@Data
public class SpuBoundTo {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
