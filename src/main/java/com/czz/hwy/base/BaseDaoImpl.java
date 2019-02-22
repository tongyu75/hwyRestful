package com.czz.hwy.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class BaseDaoImpl<T> implements BaseDao<T>{

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    
    public int insertInfo(String statement,T bean) {
        return  sqlSessionTemplate.insert(statement, bean);
    }

    public int insertInfo(String statement, List<T> bean) {
        return  sqlSessionTemplate.insert(statement, bean);
    }
    
    public int deleteInfoByBean(String statement,T bean) {
        return sqlSessionTemplate.delete(statement, bean);
    }

    public int deleteInfoByPk(String statement, String id) {
        return sqlSessionTemplate.delete(statement, id);
    }

    public int updateInfoByBean(String statement,T bean) {
        return sqlSessionTemplate.update(statement, bean);
    }

    public T getInfoByBean(String statement,T bean) {
        return sqlSessionTemplate.selectOne(statement, bean);
    }

    public int getInfoCount(String statement) {
        return sqlSessionTemplate.selectOne(statement);
    }

    public List<T> getInfoList(String statement) {
        return sqlSessionTemplate.selectList(statement);
    }
    
    public List<T> getInfoListByBean(String statement, T bean) {
        return sqlSessionTemplate.selectList(statement, bean);
    }

    public List<T> getInfoListByBean(String statement, String id) {
        return sqlSessionTemplate.selectList(statement, id);
    }
    
    public T getInfoById(String statement, String id) {
        return sqlSessionTemplate.selectOne(statement, id);
    }

    public T getInfoById(String statement, Integer id) {
        return sqlSessionTemplate.selectOne(statement, id);
    }
    
    public List<T> getAllInfo(String statement) {
        return sqlSessionTemplate.selectList(statement);
    }

    public List<T> getInfoListById(String statement, String id) {
        return sqlSessionTemplate.selectList(statement, id);
    }
    
    public List<T> getInfoListByIntegerId(String statement, Integer id) {
        return sqlSessionTemplate.selectList(statement, id);
    }
    
    public T getInfoByMap(String statement, Map<String, Object> param){
        return sqlSessionTemplate.selectOne(statement, param);
    }
    
    public int getInfoCount(String statement, T bean) {
        return sqlSessionTemplate.selectOne(statement, bean);
    }

    public int getInfoCountById(String statement, String id) {
        return sqlSessionTemplate.selectOne(statement, id);
    }

    public List<Map<String, Object>> getInfoListMapByMap(String statement, 
            Map<String, Object> param) {
        return sqlSessionTemplate.selectList(statement, param);
    }

    public List<Map<String, Object>> getInfoListMap(String statement) {
        return sqlSessionTemplate.selectList(statement);
    }
    
    public List<Map<String, Object>> getInfoListMapByInt(String statement, Integer param) {
        return sqlSessionTemplate.selectList(statement, param);
    }
    
    public List<T> getInfoListTByMap(String statement, Map<String, Object> param) {
        return sqlSessionTemplate.selectList(statement, param);
    }

    public List<String> getInfoListStringByMap(String statement,
            Map<String, Object> param) {
        return sqlSessionTemplate.selectList(statement, param);
    }

    public int updateInfoByString(String statement, String param) {
        return sqlSessionTemplate.update(statement, param);
    }
    
    public int updateInfoListByBean(String statement, List<T> bean) {
        return sqlSessionTemplate.update(statement, bean);
    }
    
    public List<Map<String, Object>> getInfoListMapByString(String statement, String param) {
        return sqlSessionTemplate.selectList(statement, param);
    }

    public List<String> getInfoListStringByBean(String statement, T bean) {
        return sqlSessionTemplate.selectList(statement, bean);
    }
    
    public int getInfoCountByMap(String statement, Map<String, Object> param) {
        return sqlSessionTemplate.selectOne(statement, param);
    }
    
    public T getInfo(String statement) {
        return sqlSessionTemplate.selectOne(statement);
    }
    
    public List<Map<String, Object>> getInfoListMapByBean(String statement, T bean) {
        return sqlSessionTemplate.selectList(statement, bean);
    }

	public int deleteInfoByMap(String statement, Map<String, Object> param) {
		return sqlSessionTemplate.delete(statement, param);
	}

	
	public int deleteAllInfo(String statement) {
		return sqlSessionTemplate.delete(statement);
	}

	public int updateInfoByMap(String statement, Map<String, Object> param) {
		return sqlSessionTemplate.update(statement, param);
	}

	public int insertInfo(String statement) {
		return sqlSessionTemplate.insert(statement);
	}

	public int updateInfo(String statement) {
		return sqlSessionTemplate.update(statement);
	}
	public int deleteInfoByPk(String statement, Integer id) {
		return sqlSessionTemplate.delete(statement, id);
	}
    public Map<String, Object> getInfoMapByBean(String statement, Integer id) {
        return sqlSessionTemplate.selectOne(statement, id);
    }

	public List<Integer> getInfoListIntegerByBean(String statement,Integer param) {
		return sqlSessionTemplate.selectList(statement, param);
	}
}