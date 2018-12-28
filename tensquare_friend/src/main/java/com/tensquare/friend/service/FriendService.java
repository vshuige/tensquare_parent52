package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/26
 */

@Service
@Transactional
public class FriendService {



    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    

    public int addFriend(String userId, String friendId) {
        // 先判断userid到frienfid是否有数据，有的话就是重复添加
        Friend friend = friendDao.findByUseridAndFriendid(userId, friendId);
        if (friend!=null){
            return 0;
        }

        // 直接添加好友，让好友表中userid到friendid的type为0（单向喜欢）
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendId);
        friend.setIslike("0");
        friendDao.save(friend);
        // 判断friendid到userid是否有数据，如果有把双方的状态改为1
        if(friendDao.findByUseridAndFriendid(friendId,userId)!=null){
            // 把双方的islike都改成1
            friendDao.updateIslike("1",userId,friendId);
            friendDao.updateIslike("1",friendId,userId);
        }
        return 1;
    }

    public int addNoFriend(String userId, String friendid) {
        // 先判断是否已经是非好友
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendid);
        if(noFriend!=null){
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;

    }

    public void deleteFriend(String userId, String friendid) {
        // 删除好友表中userid到friendid的数据
        friendDao.deleteFriend(userId,friendid);
        // 修改friendid到userid的islike数据为0
        friendDao.updateIslike("0",friendid,userId);
        // 非好友表中添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);

    }
}
