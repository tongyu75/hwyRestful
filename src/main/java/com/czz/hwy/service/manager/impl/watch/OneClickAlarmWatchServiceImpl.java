package com.czz.hwy.service.manager.impl.watch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.watch.OneClickAlarmWatch;
import com.czz.hwy.bean.user.watch.Attendances;
import com.czz.hwy.bean.user.watch.Users;
import com.czz.hwy.dao.manager.watch.OneClickAlarmWatchDao;
import com.czz.hwy.dao.user.watch.AttendancesWatchDao;
import com.czz.hwy.dao.user.watch.UsersWatchDao;
import com.czz.hwy.service.manager.watch.OneClickAlarmWatchService;

/**
 * 一键报警servie接口的实现类
 * @author 张咏雪 2016-5-17
 */
@Service
public class OneClickAlarmWatchServiceImpl implements OneClickAlarmWatchService {

    @Autowired
    private OneClickAlarmWatchDao oneClickAlarmWatchDao;

    @Autowired
    private UsersWatchDao usersWatchDao;
    
    @Autowired
    private AttendancesWatchDao attendancesWatchDao;
    
    
    /***
     * 根据员工Id，查询员工信息
     */
    public Users getEmployeeById(String employeeId) {
        return usersWatchDao.getInfoById("getUserInfoByEmployeeIdWatch", Integer.valueOf(employeeId));
    }

    /**
     * 根据员工ID，查询出与其在同一责任区内的其他员工的topics（主题）
     */
    public List<String> getTopicsListById(String employeeId) {
        OneClickAlarmWatch bean = new OneClickAlarmWatch();
        bean.setEmployeeId(employeeId);
        return oneClickAlarmWatchDao.getInfoListStringByBean("getTopicsListByIdWatch", bean);
    }

    /**
     * 插入一键报警信息
     */
    public int insertAlarm(OneClickAlarmWatch oneClickAlarm) {
        return oneClickAlarmWatchDao.insertInfo("insertAlarmWatch", oneClickAlarm);
    }

    /**
     * 根据员工ID，查询员工最后一次位置
     */
    public Attendances selectAttendancesById(String employeeId) {
        return attendancesWatchDao.getInfoById("selectAttendancesByIdWatch", employeeId);
    }
}
