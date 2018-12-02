/**
 * 
 */
package com.czz.hwy.service.area.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czz.hwy.bean.area.BoderPointFile;
import com.czz.hwy.dao.area.BoderPointFileDao;
import com.czz.hwy.service.area.BoderPointFileService;

/**
 * 责任点地图采集信息实现
 * @author 佟士儒
 *
 */
@Service
@Transactional
public class BoderPointFileServiceImpl implements BoderPointFileService {
    
    @Autowired
    private BoderPointFileDao boderPointFileDao;
    
    /**
     * 根据查询条件获取责任点地图采集列表信息（带有分页信息）
     */
    public List<BoderPointFile> getBoderPointFiles(BoderPointFile bean) {
        return boderPointFileDao.getInfoListByBean("getBoderPointFiles", bean);
    }
    
    /**
     * 根据查询条件获取责任点地图采集信息的数量
     */
    public int getBoderPointFilesCount(BoderPointFile bean) {
        return boderPointFileDao.getInfoCount("getBoderPointFilesCount", bean);
    }
    
    /**
     * 插入上传责任点地图采集信息
     */
    public int insertBoderPointFile(BoderPointFile bean) {
        return boderPointFileDao.insertInfo("insertBorderPointFileInfo", bean);
    }
    
    /**
     * 根据查询条件获取责任点地图采集列表信息（没有分页信息）
     */
    public List<BoderPointFile> getBoderPointFilesListByBean(BoderPointFile bean) {
        return boderPointFileDao.getInfoListByBean("getBoderPointFilesByBean", bean);
    }
    
    /**
     * 根据查询条件获取责任点地图采集信息
     */
    public BoderPointFile getBoderPointFilesByBean(BoderPointFile bean) {
        return boderPointFileDao.getInfoByBean("getBoderPointFilesByBean", bean);
    }
    
    /**
     * 更新责任点地图采集信息
     */     
    public int updateBoderPointFiles(BoderPointFile bean) {
        return boderPointFileDao.updateInfoByBean("updateBoderPointFile", bean);
    }
    
    /**
     * 更新责任点地图采集选中状态
     */     
    public int updateBoderPointFilesApprStatus(BoderPointFile bean) {
        return boderPointFileDao.updateInfoByBean("updateBoderPointFileForApprStatus", bean);
    }
}
