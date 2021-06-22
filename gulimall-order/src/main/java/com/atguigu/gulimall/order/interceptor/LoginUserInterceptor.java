package com.atguigu.gulimall.order.interceptor;

        import com.atguigu.common.constant.AuthServerConstant;
        import com.atguigu.common.vo.MemberRespVo;
        import org.springframework.stereotype.Component;
        import org.springframework.util.AntPathMatcher;
        import org.springframework.web.servlet.HandlerInterceptor;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import javax.servlet.http.HttpSession;
        import java.io.IOException;

/**
 * @author FutureSoldiers
 * @create 2020-12-07 14:49
 */
@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberRespVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        boolean match = new AntPathMatcher().match("/order/order/status/**", uri);
        boolean match1 = new AntPathMatcher().match("/payed/notify", uri);

        if (match || match1){
            return  true;
        }

        MemberRespVo attribute = (MemberRespVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
        if (attribute!=null){
            loginUser.set(attribute);
            return true;
        }else {
            //没登录就去登录
            session.setAttribute("msg","请先进行登录");
            response.sendRedirect("http://auth.gulimall.com/login.html");
            return false;
        }

    }
}
