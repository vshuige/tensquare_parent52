package com.tensquare.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/18
 */
@SpringBootApplication
public class RabbitApplication {

    public static void main(String[] args) {

        SpringApplication.run(RabbitApplication.class,args);
    }
}
