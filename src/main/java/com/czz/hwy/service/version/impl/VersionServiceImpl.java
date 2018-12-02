package com.czz.hwy.service.version.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.version.App;
import com.czz.hwy.dao.version.AppDao;
import com.czz.hwy.service.version.VersionService;
@Service
public class VersionServiceImpl implements VersionService {
     @Autowired
     private AppDao appDao;
     
     /**
      * 查询领导App最新版本
      */
     public App getLeaderAppVerCode() {
         App resultApp=appDao.getInfo("getLeaderAppByMaxCode");
         return resultApp;
     }
     
}
