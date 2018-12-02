package com.czz.hwy.service.manager.app;

import java.util.List;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.app.ToiletInformationApp;
@Service
public interface ToiletInformationAppService {

    public List<ToiletInformationApp> listToiletInformation();  
}
