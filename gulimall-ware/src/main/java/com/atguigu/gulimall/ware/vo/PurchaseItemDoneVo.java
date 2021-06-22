package com.atguigu.gulimall.ware.vo;

import lombok.Data;

/**
 * @author FutureSoldiers
 * @create 2020-10-29 14:58
 */
@Data
public class PurchaseItemDoneVo {

    private Long itemId;
    private Integer status;
    private String reason;

}
