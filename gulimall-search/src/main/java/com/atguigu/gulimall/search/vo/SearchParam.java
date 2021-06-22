package com.atguigu.gulimall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2020-11-13 18:43
 * 封装页面所有可能传递过来的查询条件
 *
 *
 */
@Data
public class
SearchParam {

    private String keyword;//页面传递过来的全文匹配关键字
    private Long catalog3Id; //三级分类id

    /**
     * sort=saleCount_desc/asc  销量
     * sort=skuPrice_desc/asc   价格
     * sort=hotScore_desc/asc
     */
    private String sort;  //排序条件

    /**
     * 好多的过滤条件
     * hasStock(是否有货)、skuPrice区间、brandId、catelog3Id、attrs
     * hasStock=0/1
     * skuPrice=1_500/_500/500_
     * brandId 品牌id
     * attrs=2_5寸:6寸
     */

    private Integer hasStock;  //是否有货 0(无库存)  1(有库存)
    private String skuPrice;   //价格区间
    private List<Long> brandId; //按照品牌进行查询,可以多选
    private List<String> attrs; //按照属性进行筛选
    private Integer pageNum; //页码

    private String _queryString; //原生的所有查询条件

}
