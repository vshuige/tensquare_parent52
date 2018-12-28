package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/27
 */

@Component
@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 跟新好友粉丝数和用户关心数
     * @param userid
     * @param friendid
     * @param x
     */
    @PutMapping(value = "/user/{userid}/{friendid}/{x}")
    public void updatefanscountandfollowcount(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") int x);
}
