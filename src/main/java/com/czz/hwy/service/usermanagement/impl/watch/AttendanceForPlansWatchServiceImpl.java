package com.czz.hwy.service.usermanagement.impl.watch;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.watch.DutyPlans;
import com.czz.hwy.bean.user.watch.AttendanceForPlans;
import com.czz.hwy.dao.user.watch.AttendanceForPlansWatchDao;
import com.czz.hwy.service.usermanagement.watch.AttendanceForPlansWatchService;

/**
 * 生成考勤结果的service接口实现类
 * 功能描述
 * @author 佟士儒
 * @company chnegzhongzhi
 */
@Service
public class AttendanceForPlansWatchServiceImpl implements AttendanceForPlansWatchService {

	@Autowired
	private AttendanceForPlansWatchDao attendanceForPlansWatchDao;
	
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
	
    /**
     * 根据员工号和当前日期查询环卫工所有的考勤结果
     */
    public List<AttendanceForPlans> getAttForPlans(int employeeId, Date currDate) {
        AttendanceForPlans attendanceForPlans = new AttendanceForPlans();
        attendanceForPlans.setEmployeeId(employeeId);
        attendanceForPlans.setRecordDate(currDate);
        return attendanceForPlansWatchDao.getInfoListByBean("getAttForPlansListWatch", attendanceForPlans);
    }
    
    /**
     * 根据员工号和当前日期查询检测员所有的考勤结果
     */
    public List<AttendanceForPlans> getAttForPlansToCCY(int employeeId, Date currDate) {
        AttendanceForPlans attendanceForPlans = new AttendanceForPlans();
        attendanceForPlans.setEmployeeId(employeeId);
        attendanceForPlans.setRecordDate(currDate);
        return attendanceForPlansWatchDao.getInfoListByBean("getAttForPlansListWatch", attendanceForPlans);
    }
    
    /**
     * 根据员工号和当前日期和计划上下班时间查询出环卫工（排了多个责任点的）所有的考勤结果
     */
    public List<AttendanceForPlans> getAttForPlansToHWG(int employeeId, Date currDate, DutyPlans dutyplans) {
        AttendanceForPlans attendanceForPlans = new AttendanceForPlans();
        attendanceForPlans.setEmployeeId(employeeId);
        attendanceForPlans.setRecordDate(currDate);
        attendanceForPlans.setDutyOnTime(dutyplans.getDutyOnTime());
        attendanceForPlans.setDutyOffTime(dutyplans.getDutyOffTime());
        return attendanceForPlansWatchDao.getInfoListByBean("getAttForPlansToHWGListWatch", attendanceForPlans);
    }
    
    /**
     * 根据员工号查询当前员工的考勤状态
     */
    public Map<String, Object> getCurrentAttendanceStatus(int employeeId) {
        return attendanceForPlansWatchDao.getInfoMapByBean("getCurrenAttendanceStatusWatch", employeeId);
    }
    
    /**
     * 获取所有的应该改变上下班状态考勤记录 
     */
    public List<AttendanceForPlans> selectAllAttendances(Map<String, Object> timeMap) {
        return attendanceForPlansWatchDao.getInfoListTByMap("getManualAttendancesWatch", timeMap);
    }
    
    /**
     * 考勤上班时间设置为【上班时间前60分钟，上班时间】时间段内，第一次在责任点的时间，2016-10-26
     */
    public List<AttendanceForPlans> selectAtPointAttenadanceList(Map<String, Object> timeMap) {
        return attendanceForPlansWatchDao.getInfoListTByMap("selectAtPointAttenadanceListWatch", timeMap);
    }
    
    /**
     * 批量更新考勤记录的上班状态，2016-10-26
     */
    public int batchUpdateOnStatusByList(List<AttendanceForPlans> beforList) {
        return attendanceForPlansWatchDao.updateInfoListByBean("batchUpdateOnStatusByListWatch", beforList);
    }
    
    /**
     * 批量更新上下班状态 2016-08-31
     */
    public int updateOnOrOffStatusByMap(Map<String, Object> timeMap) {
        return attendanceForPlansWatchDao.updateInfoByMap("updateOnOrOffStatusByMapWatch", timeMap);
    }
    
    /**
     * 获取时间在某一个区间内，且在责任点的考勤记录Id集合 2016-09-02
     */
    public List<Integer> selectAtPointAttenadance(Map<String, Object> timeMap) {
        return sqlSessionTemplate.selectList("selectAtPointAttenadanceWatch", timeMap);
    }
    
    /**
     * 根据员工ID查询昨日和今日考勤记录，用于手机端的考勤记录 2016-11-29
     * @return
     */
    public List<Map<String, Object>> getYesAndTomAttendanceForWatch(Map<String, Object> selectMap) {
        return attendanceForPlansWatchDao.getInfoListMapByMap("getYesAndTomAttendanceForWatch", selectMap);
    }

    /**
     * 根据Ids获取人、上下班时间、日期唯一的考勤记录列表，2017-05-05
     */
	public List<AttendanceForPlans> getAttendanceForPlansListByIdsForWatch(Map<String, Object> selectMap) {
		return attendanceForPlansWatchDao.getInfoListTByMap("getAttendanceForPlansListByIdsForWatch", selectMap);
	}

	/**
	 * 批量更新考勤记录中上下班时间内移动的距离，2017-05-05
	 */
	public int batchUpdateMobileDistanceByListForWatch(List<AttendanceForPlans> attPlansList) {
		return attendanceForPlansWatchDao.updateInfoListByBean("batchUpdateMobileDistanceByListForWatch", attPlansList);
	}
}
