package com.czz.hwy.dao.user.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.czz.hwy.base.RedisGeneratorDao;
import com.czz.hwy.bean.user.UsersForRedis;
import com.czz.hwy.dao.user.RedisUsersDao;

@Repository
public class RedisUsersDaoImpl extends RedisGeneratorDao<String, UsersForRedis>  implements RedisUsersDao {
    
    /**
     * 添加对象
     */
    public boolean add(final String key, final UsersForRedis usersForRedis) {  
      boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
        public Boolean doInRedis(RedisConnection connection)  throws DataAccessException {
            String serializeKey = key;
            RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getValueSerializer();
            @SuppressWarnings("unchecked")
            RedisSerializer<UsersForRedis> valueSerializer = (RedisSerializer<UsersForRedis>) getValueSerializer();
            byte[] key  = keySerializer.serialize(serializeKey);
            byte[] name = valueSerializer.serialize(usersForRedis);
            return connection.setNX(key, name);         
        }  
      });  
      return result;  
    }  

    /**
     * 添加集合
     */
    public boolean add(final List<UsersForRedis> list) {
      Assert.notEmpty(list);  
      boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
        public Boolean doInRedis(RedisConnection connection)  
            throws DataAccessException {  
          RedisSerializer<String> serializer = getRedisSerializer();  
          for (UsersForRedis UsersForRedis : list) {  
              byte[] key  = serializer.serialize(String.valueOf(UsersForRedis.getToken()));  
              byte[] name = serializer.serialize(JSONObject.fromObject(UsersForRedis).toString());  
            connection.setNX(key, name);  
          }  
          return true;  
        }  
      }, false, true);  
      return result; 
    }  
    
    /**
     * 删除对象 ,依赖key
     */
    public void delete(String key) {  
      List<String> list = new ArrayList<String>();  
      list.add(key);  
      delete(list);  
    }  
    
    /**
     * 删除集合 ,依赖key集合
     */
    public void delete(List<String> keys) {  
      redisTemplate.delete(keys);  
    }  
    
    /**
     * 修改对象 
     */
    public boolean update(final UsersForRedis UsersForRedis) {  
      String key = String.valueOf(UsersForRedis.getToken());  
      if (get(key) == null) {  
        throw new NullPointerException("数据行不存在, key = " + key);  
      }  
      boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
        public Boolean doInRedis(RedisConnection connection)  
            throws DataAccessException {  
          RedisSerializer<String> serializer = getRedisSerializer();  
          byte[] key  = serializer.serialize(String.valueOf(UsersForRedis.getToken()));  
          byte[] name = serializer.serialize(JSONObject.fromObject(UsersForRedis).toString());  
          connection.set(key, name);  
          return true;  
        }  
      });  
      return result;  
    }  
    
    /**
     * 根据key获取对象
     */
    public UsersForRedis get(final String keyId) {  
      UsersForRedis result = redisTemplate.execute(new RedisCallback<UsersForRedis>() {  
        public UsersForRedis doInRedis(RedisConnection connection) throws DataAccessException {  
            RedisSerializer<String> serializer = getRedisSerializer();  
            byte[] key = serializer.serialize(keyId);  
            byte[] value = connection.get(key);  
            if (value == null) {  
              return null;  
            }  
            @SuppressWarnings("unchecked")
            RedisSerializer<UsersForRedis> valueSerializer = (RedisSerializer<UsersForRedis>) getValueSerializer();
            UsersForRedis users = valueSerializer.deserialize(value);
            return users;          
        }  
      });  
      return result;  
    }  
  }