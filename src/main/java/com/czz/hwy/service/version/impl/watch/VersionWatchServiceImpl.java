package com.czz.hwy.service.version.impl.watch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.version.App;
import com.czz.hwy.dao.version.watch.AppWatchDao;
import com.czz.hwy.service.version.watch.VersionWatchService;
@Service
public class VersionWatchServiceImpl implements VersionWatchService {
     @Autowired
     private AppWatchDao appWatchDao;
     
     /**
      * 查询App最新版本
      */
     public App getAppVersionCode() {
         return appWatchDao.getInfo("getAppByMaxCodeWatch");
     }
     
}
