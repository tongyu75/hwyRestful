package com.czz.hwy.dao.user.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.RedisGeneratorDao;
import com.czz.hwy.dao.user.RedisValidateCodeDao;

@Repository
public class RedisValidateCodeDaoImpl extends RedisGeneratorDao<String, String>  implements RedisValidateCodeDao {

    /**
     * 添加验证码 
     */
    public boolean add(final String token, final String code) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)  throws DataAccessException {
                RedisSerializer<String> keySerializer = getRedisSerializer();
                byte[] key  = keySerializer.serialize(token);
                byte[] name = keySerializer.serialize(code);
                return connection.setNX(key, name);         
            }  
            });  
          return result;  
    }

    /**
     * 根据key删除验证码 
     */
    public void delete(String key) {
        redisTemplate.delete(key);
        
    }

    /**
     * 根据key获取验证码 
     */
    public String get(final String code) {
        String result = redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key = serializer.serialize(code);
                byte[] value = connection.get(key);  
                if (value == null) {  
                  return null;  
                }  
                String code = serializer.deserialize(value);
                return code;          
            }  
          });  
          return result;  
    }
}