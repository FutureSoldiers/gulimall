package com.atguigu.common.to;

import com.atguigu.common.utils.R;
import lombok.Data;

/**
 * @author FutureSoldiers
 * @create 2020-11-03 15:39
 */
@Data
public class SkuHasStockVo {

    private Long skuId;
    private Boolean hasStock;
}
