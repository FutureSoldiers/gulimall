package com.atguigu.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2020-11-05 10:41
 */
//2级分类vo
@NoArgsConstructor   //生成 无参构造器
@AllArgsConstructor //生成 有参构造器
@Data
public class Catelog2Vo {
    private String catalog1Id; //1级父分类id
    private List<Catelog3Vo> catalog3List; //3级子分类
    private String id;
    private String name;

    //3级分类vo
    @NoArgsConstructor   //生成 无参构造器
    @AllArgsConstructor //生成 有参构造器
    @Data
    public static class Catelog3Vo{
        private String catalog1Id; //父分类 2级分类id
        private String id;
        private String name;


    }
}
