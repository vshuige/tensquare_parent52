package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/14
 */

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }


    @GetMapping(value = "/{id}")
    public  Result findById(@PathVariable String id){
        return  new Result(true,StatusCode.OK,"查询成功",spitService.findById(id));
    }


    @PostMapping
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable String id,@RequestBody Spit spit){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"更新成功");
    }


    @DeleteMapping(value = "/{id}")
    public Result deleteById(@PathVariable String id){
        spitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @GetMapping(value = "/comment/{parentid}/{page}/{size}")
    public Result findByParentid(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> pageData = spitService.findByParentid(parentid, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(pageData.getTotalElements(),pageData.getContent()));
    }


    @PutMapping(value = "/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId){
        spitService.thumbup(spitId);
        return new Result(true,StatusCode.OK,"点赞成功");

    }

}
