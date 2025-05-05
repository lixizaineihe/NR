package com.itheima.interceptors;

import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * jwt 令牌由请求头带到服务器，请求头的名称为 Authorization
         * 所以这里是在获取请求头中携带的 jwt 令牌的值
         */
        String token=request.getHeader("Authorization");
        try {
            // 解析签名得到头和有效载荷，和令牌中的一致，才不会报异常
            Map<String,Object> claims= JwtUtil.parseToken(token);

            // 把令牌解析结果存储到线程上下文
            ThreadLocalUtil.set(claims);

            return true;
        } catch (Exception e) {
            // 解析有问题返回状态码 401
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空线程中的数据
        ThreadLocalUtil.remove();
    }
}
