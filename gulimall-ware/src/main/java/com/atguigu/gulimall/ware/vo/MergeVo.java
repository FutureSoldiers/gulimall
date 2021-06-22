package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2020-10-29 12:47
 */
@Data
public class MergeVo {

    private Long purchaseId;  //整合单id
    private List<Long> items; //[1,2,3,4] 合并项集合
}
