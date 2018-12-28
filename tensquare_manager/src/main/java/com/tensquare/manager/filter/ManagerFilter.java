package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/28
 */
@Component
public class ManagerFilter extends ZuulFilter{

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 请求前执行pre请求后执行post
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器的执行顺序，数字越小，越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启，true表示开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作，return任何object的值都表示继续执行
     * setsendzuulresponse(false)时不执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台网关过滤器了");
        // 得到上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 得到域
        HttpServletRequest request = currentContext.getRequest();

        if(request.getMethod().equals("OPTIONS")){
            return null;
        }
        if(request.getRequestURI().indexOf("login")>0){
            return null;
        }


        // 得到请求头
        String header = request.getHeader("Authorization");
        if (header!=null && !"".equals(header)){
            if(header.startsWith("Bearer")){
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if("admin".equals(roles)){
                        //把信息头传下去，并且放行
                        currentContext.addZuulRequestHeader("Authorization",header);
                        return null;

                    }


                }catch (Exception e){
                    e.printStackTrace();
                    currentContext.setSendZuulResponse(false); //终止运行
                }
            }
        }

        currentContext.setSendZuulResponse(false); //终止运行
        currentContext.setResponseStatusCode(403);
        currentContext.setResponseBody("权限不足");
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
