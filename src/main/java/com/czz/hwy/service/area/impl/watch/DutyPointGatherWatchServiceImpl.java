/**
 * 
 */
package com.czz.hwy.service.area.impl.watch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czz.hwy.bean.area.watch.DutyPointGather;
import com.czz.hwy.dao.area.watch.DutyPointGatherWatchDao;
import com.czz.hwy.service.area.watch.DutyPointGatherWatchService;

/**
 *  责任点考核点的采集信息Service接口
 */
@Service
@Transactional
public class DutyPointGatherWatchServiceImpl implements DutyPointGatherWatchService {

    @Autowired
    private DutyPointGatherWatchDao dutyPointGatherWatchDao;
    /**
     * 根据责任点ID查询边界点
     * @param dutyPointId
     * @param dutyAreaId
     * @return
     */
    public List<DutyPointGather> getDutyPointInfoById(int dutyPointId, int dutyAreaId){
        DutyPointGather dutyPointGather =new  DutyPointGather();
        // 责任区ID
        dutyPointGather.setAreaId(dutyAreaId);
        // 责任点ID
        dutyPointGather.setPointId(dutyPointId);
        return dutyPointGatherWatchDao.getInfoListByBean("getInfoByBeanWatch", dutyPointGather);
        
    }

}
