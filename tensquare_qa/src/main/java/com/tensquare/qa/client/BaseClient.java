package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.BaseClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/26
 */
//@Component   //这个注解只是为了不让注入BaseClient时报错，没实际用处，毕竟实现类不在该模块
@FeignClient(value = "tensquare-base",fallback = BaseClientImpl.class)
public interface BaseClient {

    /**
     * 根据id查找标签
     * @param labelId
     * @return
     */
    @GetMapping(value = "/label/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId);
}
