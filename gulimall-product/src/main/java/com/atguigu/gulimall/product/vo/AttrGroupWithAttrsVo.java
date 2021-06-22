package com.atguigu.gulimall.product.vo;

import com.atguigu.gulimall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2020-10-27 16:35
 */
@Data
public class AttrGroupWithAttrsVo {

    /**
     * ????id
     */
    @TableId
    private Long attrGroupId;
    /**
     * ????
     */
    private String attrGroupName;
    /**
     * ???
     */
    private Integer sort;
    /**
     * ????
     */
    private String descript;
    /**
     * ??ͼ?
     */
    private String icon;
    /**
     * ????????id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;

}
