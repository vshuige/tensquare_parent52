package com.tensquare.rabbit.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/19
 */

@Component
@RabbitListener(queues = "itcast")
public class Customer1 {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("itcast："+msg);
    }

}
