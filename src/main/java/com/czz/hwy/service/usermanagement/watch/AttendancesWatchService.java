package com.czz.hwy.service.usermanagement.watch;

import java.util.List;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.watch.AttendanceForPlans;
import com.czz.hwy.bean.user.watch.Attendances;
/**
 *  员工考勤记录业务层
 * @author 佟士儒 2016/10/30
 * @version 1.0 换环卫云
 *
 */
@Service
public interface AttendancesWatchService{
    
    /**
     * 批量新增考勤轨迹记录  2016-08-30
     * @param attendancesList
     */
    public int batchAddAttendances(List<Attendances> attendancesList);
    
    /**
     * 将当天轨迹数据插入到用于地图查询的轨迹表中去,2016-09-20
     * @param attendancesList
     */
    public int batchAddAttendancesForMap(List<Attendances> attendancesList);

    /**
     * 根据考勤记录中的员工ID，上班时间，下班时间，日期获取按采集时间升序排列的在责任点的轨迹集合，2017-05-05 
     * @param att
     * @return
     */
	public List<Attendances> getAttendancesListByAttPlansBeanForWatch(AttendanceForPlans att);
}
