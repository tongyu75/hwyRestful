/**
 * 
 */
package com.czz.hwy.service.area;

import java.util.List;

import com.czz.hwy.bean.area.BoderPointFile;

/**
 * 责任点边界点采集文件管理接口
 * @author 佟士儒
 *
 */
public interface BoderPointFileService {

    /**
     * 根据查询条件获取责任点地图采集列表信息（带有分页信息）
     */
    public List<BoderPointFile> getBoderPointFiles(BoderPointFile bean);
    
    /**
     * 根据查询条件获取责任点地图采集信息的数量
     */
    public int getBoderPointFilesCount(BoderPointFile bean);
    
    /**
     * 插入上传责任点地图采集信息
     */
    public int insertBoderPointFile(BoderPointFile bean);
    
    /**
     * 根据查询条件获取责任点地图采集列表信息（没有分页信息）
     */
    public List<BoderPointFile> getBoderPointFilesListByBean(BoderPointFile bean);
    
    /**
     * 根据查询条件获取责任点地图采集信息
     */
    public BoderPointFile getBoderPointFilesByBean(BoderPointFile bean);
    
    /**
     * 更新责任点地图采集信息
     */     
    public int updateBoderPointFiles(BoderPointFile bean);
    
    /**
     * 更新责任点地图采集选中状态
     */     
    public int updateBoderPointFilesApprStatus(BoderPointFile bean);
}
