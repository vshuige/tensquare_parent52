package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    public User findByMobile(String mobile);

    /**
     * 修改粉丝数
     * @param x
     * @param friendid
     */
    @Modifying
    @Query(value = "update tb_user set fanscount=fanscount+? Where id=?",nativeQuery = true)
    void updatefanscount(int x, String friendid);

    @Modifying
    @Query(value = "update tb_user set followcount=followcount+? Where id=?",nativeQuery = true)
    void updatefollowcount(int x, String userid);
}
