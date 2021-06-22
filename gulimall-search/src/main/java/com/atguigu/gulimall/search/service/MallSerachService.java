package com.atguigu.gulimall.search.service;

import com.atguigu.gulimall.search.vo.SearchParam;
import com.atguigu.gulimall.search.vo.SearchResult;

/**
 * @author FutureSoldiers
 * @create 2020-11-13 18:47
 */
public interface MallSerachService {
    /**
     *
     * @param param 检索的所有参数
     * @return返回检索结果
     */
    SearchResult search(SearchParam param);
}
