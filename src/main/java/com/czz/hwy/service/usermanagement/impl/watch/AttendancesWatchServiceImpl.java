package com.czz.hwy.service.usermanagement.impl.watch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.watch.AttendanceForPlans;
import com.czz.hwy.bean.user.watch.Attendances;
import com.czz.hwy.dao.user.watch.AttendancesWatchDao;
import com.czz.hwy.service.usermanagement.watch.AttendancesWatchService;
@Service
public class AttendancesWatchServiceImpl implements AttendancesWatchService {

    @Autowired
    private AttendancesWatchDao attendancesWatchDao;
    /**
     * 批量新增考勤轨迹记录  2016-08-30
     */
    public int batchAddAttendances(List<Attendances> attendancesList) {
        return attendancesWatchDao.insertInfo("batchAddAttendancesWatch", attendancesList);
    }
    
    /**
     * 将当天轨迹数据插入到用于地图查询的轨迹表中去,2016-09-20
     */
    public int batchAddAttendancesForMap(List<Attendances> attendancesList) {
        return attendancesWatchDao.insertInfo("batchAddAttendancesForMapWatch", attendancesList);
    }

    /**
     * 根据考勤记录中的员工ID，上班时间，下班时间，日期获取按采集时间升序排列的在责任点的轨迹集合，2017-05-05 
     */
	public List<Attendances> getAttendancesListByAttPlansBeanForWatch(AttendanceForPlans att) {
		return attendancesWatchDao.getAttendancesListByAttPlansBeanForWatch(att);
	}
}
