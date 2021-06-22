package com.atguigu.gulimall.seckill.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2021-04-01 17:09
 */
@Data
public class SeckillSessionWithSkus {

    private Long id;
    /**
     * ???????
     */
    private String name;
    /**
     * ÿ?տ?ʼʱ?
     */
    private Date startTime;
    /**
     * ÿ?ս???ʱ?
     */
    private Date endTime;
    /**
     * ????״̬
     */
    private Integer status;
    /**
     * ????ʱ?
     */
    private Date createTime;

    private List<SeckillSkuVo> relationSkus;
}
