package com.atguigu.gulimall.search.controller;

import com.atguigu.common.exception.BizCodeEnume;
import com.atguigu.common.to.es.SkuEsModel;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author FutureSoldiers
 * @create 2020-11-03 18:02
 */
@Slf4j
@RequestMapping("/search/save")
@RestController
public class ElasticSaveController{

    @Autowired
    private ProductSaveService productSaveService;

    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels){
        boolean b =false;
        try {
          b =  productSaveService.productStatusUp(skuEsModels);
        } catch (Exception e) {
            log.error("ElasticSaveController商品上架错误: {}",e);
            return R.error(BizCodeEnume.PRODUCY_UP_EXCEPTION.getCode(),BizCodeEnume.PRODUCY_UP_EXCEPTION.getMsg());
        }
        if (!b){return R.ok();}
        else { return R.error(BizCodeEnume.PRODUCY_UP_EXCEPTION.getCode(),BizCodeEnume.PRODUCY_UP_EXCEPTION.getMsg());}



    }

}
