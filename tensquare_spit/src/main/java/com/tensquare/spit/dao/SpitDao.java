package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/14
 */
public interface SpitDao extends MongoRepository<Spit,String>{

    /**
     * 通过父标签查找
     * @param parentId
     * @param pageable
     * @return
     */
    public Page<Spit> findByParentid(String parentId,Pageable pageable);

}
