package com.czz.hwy.dao.user;

import java.util.List;

import com.czz.hwy.bean.user.UsersForRedis;

public interface RedisUsersDao{
    boolean add(final String key, UsersForRedis user);
 
    boolean add(List<UsersForRedis> list);
 
    void delete(String key);
 
    UsersForRedis get(String keyId);
     
}