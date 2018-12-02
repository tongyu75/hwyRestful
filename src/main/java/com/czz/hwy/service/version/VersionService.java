package com.czz.hwy.service.version;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.version.App;
@Service
public interface VersionService {
    
    /**
     * 查询领导App最新版本
     */
	public App getLeaderAppVerCode();
}
