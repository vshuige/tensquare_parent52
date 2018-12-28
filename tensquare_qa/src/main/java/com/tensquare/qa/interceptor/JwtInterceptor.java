package com.tensquare.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/24
 */

@Component
public class JwtInterceptor implements HandlerInterceptor{

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("经过了拦截器");
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            final String token = authHeader.substring(7); // The partafter "Bearer "

            try {
                Claims claims = jwtUtil.parseJWT(token);
                if (claims != null) {
                    if("admin".equals(claims.get("roles"))){//如果是管理员
                        request.setAttribute("admin_token", token);
                    }
                    if("user".equals(claims.get("roles"))){//如果是用户
                        request.setAttribute("user_token", token);
                    }
                }
            }catch (Exception e){
                throw new RuntimeException("令牌不正确");
            }

        }
        return true;
    }

}
