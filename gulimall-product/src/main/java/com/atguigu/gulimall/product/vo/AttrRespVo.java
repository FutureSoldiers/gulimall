package com.atguigu.gulimall.product.vo;

import lombok.Data;

/**
 * @author FutureSoldiers
 * @create 2020-10-26 16:56
 */
@Data
public class AttrRespVo extends AttrVo{

    private String catelogName;
    private String groupName;
    private Long[] catelogPath;

}
