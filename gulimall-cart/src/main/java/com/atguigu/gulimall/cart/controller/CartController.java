package com.atguigu.gulimall.cart.controller;

import com.atguigu.common.constant.AuthServerConstant;
import com.atguigu.gulimall.cart.interceptor.CartInterceptor;
import com.atguigu.gulimall.cart.service.CartService;
import com.atguigu.gulimall.cart.vo.Cart;
import com.atguigu.gulimall.cart.vo.CartItem;
import com.atguigu.gulimall.cart.vo.UserInfoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author FutureSoldiers
 * @create 2020-11-29 10:32
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @ResponseBody
    @GetMapping("/currentUserCartItems")
    public List<CartItem> getCurrentUserCartItems(){
        return cartService.getUserCartItems();
    }


    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId){
        cartService.deleteItem(skuId);
        return "redirect:http://cart.gulimall.com/cart.html";

    }

    @GetMapping("/countItem")
    public String countItem(@RequestParam("skuId") Long skuId,@RequestParam("num") Integer num){

        cartService.changeItemCount(skuId,num);
        return "redirect:http://cart.gulimall.com/cart.html";

    }

    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId,@RequestParam("check") Integer check){
        cartService.checkItem(skuId,check);

        return "redirect:http://cart.gulimall.com/cart.html";

    }

    /**
     * 浏览器有一个cookie; user-key; 标识用户身份,一个月后过期;
     * 如果第一次使用jd的购物车功能,都会给一个临时的用户身份;
     * 浏览器以后保存,每次访问都会带上这个cookie;
     *
     * 登录: session有
     * 没登录: 按照cookie里面带来的user-key来做
     * 第一次: 如果没有临时用户,还要帮忙创建一个临时用户.
     *
     * @return
     */
    @GetMapping("/cart.html")
    public String cartListPage(Model model) throws ExecutionException, InterruptedException {

        //快速得到用户信息, id, user-key
       // UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        Cart cart = cartService.getCart();
        model.addAttribute("cart",cart);

        System.out.println(cart.toString());
        System.out.println(cart.getItems().toString());

        return "cartList";
    }

    /**
     * 添加商品到购物车
     *
     * RedirectAttributes ra
     *      ra.addFlashAttribute("skuId",skuId); 将数据放在session里面可以在页面取出,但是只能取一次
     *      ra.addAttribute("skuId",skuId); 将数据放在url后面
     * @return
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,@RequestParam("num") Integer num,RedirectAttributes ra) throws ExecutionException, InterruptedException {

     cartService.addToCart(skuId,num);
      ra.addAttribute("skuId",skuId);
        return "redirect:http://cart.gulimall.com/addToCartSuccess.html";
    }

    /**
     * 跳转到成功页
     * @param skuId
     * @param model
     * @return
     */
    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId,Model model){
        //重定向到成功页面. 再次查询购物车数据即可
        CartItem cartItem =  cartService.getCartItem(skuId);
        model.addAttribute("cartItem",cartItem);

        return "success";
    }
}
