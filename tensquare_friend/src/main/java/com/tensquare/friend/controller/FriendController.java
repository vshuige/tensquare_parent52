package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import com.zaxxer.hikari.util.FastList;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/26
 */
@RestController
@RequestMapping("/friend")
public class FriendController {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;
    
    /**
     * 添加好友或添加非好友
     * @return
     */
    @PutMapping(value = "/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid,@PathVariable String type ){
        // 验证是否登陆，并拿到当前用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if(claims==null){
            // 说明当前用户没有user角色
            return new Result(false, StatusCode.ERROR,"权限不足");
        }
        // 得到当前登陆用户id
        String userId = claims.getId();
        // 判断是添加好友还是添加非好友
        if(type!=null){
            if("1".equals(type)){
                // 添加好友
                int flag = friendService.addFriend(userId,friendid);
                if(flag == 0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加好友");
                }
                if(flag == 1){
                    userClient.updatefanscountandfollowcount(userId,friendid,1);
                    return new Result(true,StatusCode.OK,"添加成功");
                }
            }else if("2".equals(type)){
                // 添加非好友
                int flag = friendService.addNoFriend(userId,friendid);
                if(flag == 0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加非好友");
                }
                if(flag == 1){
                    return new Result(true,StatusCode.OK,"添加成功");
                }
            }
            return new Result(false,StatusCode.ERROR,"参数异常");
        }else {
            return new Result(false,StatusCode.ERROR,"参数异常");
        }
    }



    @DeleteMapping(value = "/{friendid}")
    public Result deleteFriend(@PathVariable String friendid){
        // 验证是否登陆，并拿到当前用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if(claims==null){
            // 说明当前用户没有user角色
            return new Result(false, StatusCode.ERROR,"权限不足");
        }
        // 得到当前登陆用户id
        String userId = claims.getId();
        friendService.deleteFriend(userId,friendid);
        userClient.updatefanscountandfollowcount(userId,friendid,-1);
        return new Result(true,StatusCode.OK,"删除成功");


    }
}
