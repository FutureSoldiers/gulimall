package com.atguigu.gulimall.ware.vo;

import javafx.fxml.Initializable;
import lombok.Data;

/**
 * @author FutureSoldiers
 * @create 2021-03-16 15:26
 */
@Data
public class LockStockResult {

    private Long skuId;
    private Integer num;
    private Boolean locked;
}
