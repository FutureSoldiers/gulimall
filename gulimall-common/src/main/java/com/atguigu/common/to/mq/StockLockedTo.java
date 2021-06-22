package com.atguigu.common.to.mq;

import lombok.Data;

import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2021-03-24 19:37
 */
@Data
public class StockLockedTo {

    private Long id; //库存工作单的id
    private StockDetailTo detail; //工作单详情的所有Id


}
