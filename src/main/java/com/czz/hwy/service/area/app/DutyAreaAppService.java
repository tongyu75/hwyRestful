package com.czz.hwy.service.area.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.area.app.DutyAreaApp;


/**
 * 责任区业务层接口，用于app接口
 * @author 张咏雪 2016-11-02
 */
public interface DutyAreaAppService{

	/**
	 * 查询检测员或考核员或督察员所管辖的责任区总数，2016-11-02
	 * @param selectMap
	 * @return
	 */
	int selectAreaListCount(Map<String, Object> selectMap);

	/**
	 * 查询检测员或考核员或督察员所管辖的责任区列表，2016-11-02
	 * @param selectMap
	 * @return
	 */
	List<DutyAreaApp> selectAreaList(Map<String, Object> selectMap);
   
	
	/**
     * 查询考核员的考勤查岗的责任区，2016-11-07
     * @param selectMap
     * @return
     */
    List <Map<String, Object>>  selectAttendanceInspectArea(Integer employeeId);
}
