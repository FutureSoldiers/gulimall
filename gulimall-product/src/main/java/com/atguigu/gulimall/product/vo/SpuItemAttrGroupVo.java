package com.atguigu.gulimall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2020-11-18 18:46
 */
@Data
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<Attr> attrs;

}
