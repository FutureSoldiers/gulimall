package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2020-10-29 14:57
 */
@Data
public class PurchaseDoneVo {

    @NotNull
    private Long id; //采购单

    private List<PurchaseItemDoneVo> items;
}
