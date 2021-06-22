package com.atguigu.gulimall.search.controller;

import com.atguigu.gulimall.search.service.MallSerachService;
import com.atguigu.gulimall.search.vo.SearchParam;
import com.atguigu.gulimall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.print.attribute.standard.NumberUp;
import javax.servlet.http.HttpServletRequest;

/**
 * @author FutureSoldiers
 * @create 2020-11-13 17:57
 */
@Controller
public class SearchController {

    @Autowired
    private MallSerachService mallSerachService;

    /**
     * 自动将页面提交过来的所有请求查询参数封装成指定的对象
     * @param param
     * @return
     */
    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request){
        if (param.getPageNum()==null){
            param.setPageNum(1);
        }

        param.set_queryString(request.getQueryString());
        //1、根据传递来的页面的查询参数,去es中检商品
      SearchResult result =  mallSerachService.search(param);
      model.addAttribute("result",result);
        System.out.println(result.toString());


        return "list";
    }

}
