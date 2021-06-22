package com.atguigu.gulimall.seckill.controller;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.seckill.service.SeckillService;
import com.atguigu.gulimall.seckill.to.SeckillSkuRedisTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2021-04-02 14:55
 */
@Controller
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 返回当前时间可以参与秒杀的商品信息
     */
    @ResponseBody
    @GetMapping("/currentSeckillSkus")
    public R getCurrentSeckillSkus(){

     List<SeckillSkuRedisTo> vo =  seckillService.getCurrentSeckillSkus();

        return R.ok().setData(vo);
    }

    @ResponseBody
    @GetMapping("/sku/seckill/{skuId}")
    public R getSkuSeckillInfo(@PathVariable("skuId") Long skuId){
       SeckillSkuRedisTo skuRedisTo =  seckillService.getSkuSeckillInfo(skuId);

        return R.ok().setData(skuRedisTo);

    }

    @GetMapping("/kill")
    public String  secKill(@RequestParam("killId") String killId
                     ,@RequestParam("key") String key
                     ,@RequestParam("num") Integer num
                     ,Model model){

     String orderSn = seckillService.kill(killId,key,num);

     model.addAttribute("orderSn",orderSn);
        //1、判断是否登录

        return "success";
    }
}
