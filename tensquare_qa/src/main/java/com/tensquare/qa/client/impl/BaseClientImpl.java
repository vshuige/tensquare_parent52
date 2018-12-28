package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.BaseClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/27
 */
@Component
public class BaseClientImpl implements BaseClient {

    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR,"熔断器触发了");
    }
}
