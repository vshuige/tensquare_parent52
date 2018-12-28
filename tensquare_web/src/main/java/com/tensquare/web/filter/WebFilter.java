package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/28
 */
@Component
public class WebFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        // 得到request上下文
        RequestContext currentContext = RequestContext.getCurrentContext();

        //得到request域
        HttpServletRequest request = currentContext.getRequest();

        //得到头信息
        final String authHeader = request.getHeader("Authorization");

        //判断是否有头信息
        if(authHeader!=null&&!"".equals(authHeader)){
            //把头信息往下传
            currentContext.addZuulRequestHeader("Authorization",authHeader);
        }

        return null;
    }
}
