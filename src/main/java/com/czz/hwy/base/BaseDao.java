package com.czz.hwy.base;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
    
    public int getInfoCount(String statement);
    
    public int getInfoCount(String statement, T bean);
    
    public int getInfoCountById(String statement, String id);

    public int getInfoCountByMap(String statement, Map<String, Object> param);
    
    public T getInfo(String statement);
    
    public T getInfoByBean(String statement, T bean);
    
    public T getInfoById(String statement, String id);
    
    public T getInfoById(String statement, Integer id);
    
    public Map<String, Object> getInfoMapByBean(String statement, Integer id);
    
    public List<T> getAllInfo(String statement);

    public List<T> getInfoListByBean(String statement, T bean);
    
    public List<T> getInfoListById(String statement, String id);

    public List<T> getInfoListByIntegerId(String statement, Integer id);
    
    public List<T> getInfoList(String statement);
    
    public T getInfoByMap(String statement, Map<String, Object> param);
    
    public List<Map<String, Object>> getInfoListMap(String statement);
    
    public List<Map<String, Object>> getInfoListMapByMap(String statement, Map<String, Object> param);
    
    public List<Map<String, Object>> getInfoListMapByString(String statement, String param);

    public List<Map<String, Object>> getInfoListMapByInt(String statement, Integer param);
    
    public List<Map<String, Object>> getInfoListMapByBean(String statement, T bean);
    
    public List<T> getInfoListTByMap(String statement, Map<String,Object> param);
    
    public List<String> getInfoListStringByBean(String statement, T bean);
    
    public List<String> getInfoListStringByMap(String statement, Map<String, Object> param);
    
    public List<Integer> getInfoListIntegerByBean(String statement, Integer param);

    public int insertInfo(String statement);
    
    public int insertInfo(String statement, T bean);
    
    public int insertInfo(String statement, List<T> bean);
    
    public int deleteInfoByPk(String statement, String id);
    
    public int deleteInfoByPk(String statement, Integer id);
    
    public int deleteInfoByBean(String statement, T bean);
    
    public int deleteInfoByMap(String statement, Map<String,Object> param);
    
    public int deleteAllInfo(String statement);

    public int updateInfo(String statement);
    
    public int updateInfoByBean(String statement, T bean);

    public int updateInfoListByBean(String statement, List<T> bean);
    
    public int updateInfoByString(String statement, String param);
    
    public int updateInfoByMap(String statement, Map<String,Object> param);
}