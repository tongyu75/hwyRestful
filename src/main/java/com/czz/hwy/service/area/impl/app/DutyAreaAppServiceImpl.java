package com.czz.hwy.service.area.impl.app;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.area.app.DutyAreaApp;
import com.czz.hwy.dao.area.app.DutyAreaAppDao;
import com.czz.hwy.service.area.app.DutyAreaAppService;

/**
 * 责任区业务层接口实现类，用于app接口
 * @author 张咏雪 2016-11-02
 */
@Service
public class DutyAreaAppServiceImpl implements DutyAreaAppService {

	@Autowired
	private DutyAreaAppDao dutyAreaAppDao;
	
	/**
	 * 查询检测员或考核员或督察员所管辖的责任区总数，2016-11-02
	 */
	public int selectAreaListCount(Map<String, Object> selectMap) {
		return dutyAreaAppDao.getInfoCountByMap("selectAreaListCount", selectMap);
	}

	/**
	 * 查询检测员或考核员或督察员所管辖的责任区列表，2016-11-02
	 */
	public List<DutyAreaApp> selectAreaList(Map<String, Object> selectMap) {
		return dutyAreaAppDao.getInfoListTByMap("selectAreaList", selectMap);
	}
    
    /**
     * 查询考核员或督察员的考勤查岗的责任区，2016-11-07
     * @param selectMap
     * @return
     */
    public List <Map<String, Object>> selectAttendanceInspectArea(Integer employeeId) {
        return dutyAreaAppDao.getInfoListMapByInt("selectAttendanceInspectAreaApp", employeeId);
    }	
}
