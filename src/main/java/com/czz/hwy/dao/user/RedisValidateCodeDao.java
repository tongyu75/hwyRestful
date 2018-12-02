package com.czz.hwy.dao.user;


public interface RedisValidateCodeDao{
    boolean add(String token, String code);
 
    void delete(String key);
 
    String get(String key);
}