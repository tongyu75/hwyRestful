package com.czz.hwy.service.version.watch;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.version.App;
@Service
public interface VersionWatchService {
    
    /**
     * 查询App最新版本
     */
	public App getAppVersionCode();
}
